package com.Maxwell.weaponmaster_handheld_weaponstudio.mixin;

import com.sky.weaponmaster.RuniaConf;
import com.sky.weaponmaster.SweeperWeapon;
import com.sky.weaponmaster.abilities.AbilityAsset;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(SweeperWeapon.class)
public abstract class MixinSweeperWeaponEnchantable {
    private double calculateBalancedPower(double currentLvl) {
        if (currentLvl <= 100.0) {
            return currentLvl / 100.0;
        }
        double delta = currentLvl - 100.0;
        return 1.0 + (delta * 0.0065);
    }

    @Redirect(
            method = "getAttributeModifiers(Lnet/minecraft/world/entity/EquipmentSlot;Lnet/minecraft/world/item/ItemStack;)Lcom/google/common/collect/Multimap;",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(DD)D"),
            remap = false
    )
    private double liftAttributeCap(double a, double b) {
        double maxLvl = ((Integer) RuniaConf.globalConfig.maxLevel.get()).doubleValue();
        return calculateBalancedPower(a * maxLvl);
    }

    @Redirect(
            method = "getMaxDamage(Lnet/minecraft/world/item/ItemStack;)I",
            at = @At(value = "INVOKE", target = "Ljava/lang/Math;min(DD)D"),
            remap = false
    )
    private double liftDurabilityCap(double a, double b) {
        double maxLvl = ((Integer) RuniaConf.globalConfig.maxLevel.get()).doubleValue();
        return calculateBalancedPower(a * maxLvl);
    }

    @Overwrite(remap = false)
    public static double expToLevel(int exp) {
        if (exp <= 0) return 0.0;
        long unsignedExp = exp < 0 ? 2147483647L : (long) exp;
        return (double) unsignedExp / 1000.0;
    }

    @Overwrite(remap = false)
    public static void addExp(ItemStack weapon, Player player, int ammount, AbilityAsset.LevelPath path, boolean shouldSpread) {
        net.minecraft.nbt.CompoundTag mainTag = weapon.getOrCreateTag();
        net.minecraft.nbt.CompoundTag levelsTag = mainTag.getCompound("CoreWeaponlevels");
        String uuid = player.getUUID().toString();
        if (!levelsTag.contains(uuid)) levelsTag.put(uuid, new net.minecraft.nbt.CompoundTag());
        net.minecraft.nbt.CompoundTag playerLevelTag = levelsTag.getCompound(uuid);
        long currentExp = (long) playerLevelTag.getInt(path.id);
        double multiplier = ((Double) RuniaConf.globalConfig.expMultiplier.get()).doubleValue();
        double finalExp = Math.pow(Math.max(1, ammount), 0.75) * multiplier;
        long nextExp = Math.min(2147483647L, currentExp + (long) finalExp);
        int oldLevel = (int) Math.floor(expToLevel((int) currentExp));
        int newLevel = (int) Math.floor(expToLevel((int) nextExp));
        playerLevelTag.putInt(path.id, (int) nextExp);
        if (newLevel > oldLevel) {
            int diff = newLevel - oldLevel;
            mainTag.putInt("BonusDucks", mainTag.getInt("BonusDucks") + diff);
            mainTag.putInt("Rerolls", mainTag.getInt("Rerolls") + diff);
            player.level().playSound(null, player.getX(), player.getY(), player.getZ(),
                    (net.minecraft.sounds.SoundEvent) com.sky.weaponmaster.WeaponMaster.WEAPON_LEVEL.get(),
                    net.minecraft.sounds.SoundSource.PLAYERS, 1.0F, 1.5F);
        }
    }

    @Inject(
            method = "processHits(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/server/level/ServerLevel;Ljava/util/Set;Ljava/util/Set;FFZF)V",
            at = @At("HEAD"),
            remap = false
    )
    private void clearInvulnerability(ItemStack stack, Player player, ServerLevel world, Set<Entity> hitListCritable, Set<LivingEntity> hitList, float damage, float knockback, boolean isCritGlobal, float size, CallbackInfo ci) {
        if (hitListCritable != null) {
            for (Entity e : hitListCritable) if (e != null) e.invulnerableTime = 0;
        }
        if (hitList != null) {
            for (LivingEntity le : hitList) if (le != null) le.invulnerableTime = 0;
        }
    }

    @Overwrite(remap = true)
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }

    public int getEnchantmentValue() {
        return 15;
    }

    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return enchantment.category == EnchantmentCategory.WEAPON || enchantment.category == EnchantmentCategory.BREAKABLE || enchantment.category == EnchantmentCategory.VANISHABLE;
    }

    @Overwrite(remap = false)
    public static void removeNonCurses(ItemStack itemstack) {
    }

    @Redirect(
            method = "initCapabilities(Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/nbt/CompoundTag;)Lnet/minecraftforge/common/capabilities/ICapabilityProvider;",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/world/item/ItemStack;setTag(Lnet/minecraft/nbt/CompoundTag;)V"),
            remap = false
    )
    private void mergeTagsOnSetup(ItemStack stack, net.minecraft.nbt.CompoundTag newTag) {
        net.minecraft.nbt.CompoundTag currentTag = stack.getTag();
        if (currentTag != null) currentTag.merge(newTag);
        else stack.setTag(newTag);
    }

    @Inject(
            method = "print",
            at = @At("HEAD"),
            cancellable = true,
            remap = false
    )
    private static void silenceDebugLog(Object[] objects, CallbackInfo ci) {
        ci.cancel();
    }
}