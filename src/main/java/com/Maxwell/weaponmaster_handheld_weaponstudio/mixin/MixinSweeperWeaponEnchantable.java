package com.Maxwell.weaponmaster_handheld_weaponstudio.mixin;

import com.sky.weaponmaster.SweeperWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

@Mixin(SweeperWeapon.class)
public abstract class MixinSweeperWeaponEnchantable {

    @Overwrite(remap = true)
    public boolean isEnchantable(ItemStack stack) {
        return true;
    }
    public int getEnchantmentValue() {
        return 15;
    }
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        // WEAPON カテゴリは剣用のエンチャント（鋭さ、ノックバック、火属性、掠りの刃など）
        // BREAKABLE カテゴリは「耐久力」
        // VANISHABLE カテゴリは「修繕」や「消滅の呪い」
        return enchantment.category == EnchantmentCategory.WEAPON
                || enchantment.category == EnchantmentCategory.BREAKABLE
                || enchantment.category == EnchantmentCategory.VANISHABLE;
    }
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Overwrite(remap = false)
    public static void removeNonCurses(ItemStack itemstack) {
        // 削除処理を阻止
    }
}