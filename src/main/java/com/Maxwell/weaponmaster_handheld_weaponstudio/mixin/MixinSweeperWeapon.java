package com.Maxwell.weaponmaster_handheld_weaponstudio.mixin;

import com.sky.weaponmaster.SweeperWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SweeperWeapon.class)
public abstract class MixinSweeperWeapon {

    @Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true, remap = false)
    private void onIsEnchantable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }

    @Inject(method = "removeNonCurses", at = @At("HEAD"), cancellable = true, remap = false)
    private static void onRemoveNonCurses(ItemStack stack, CallbackInfo ci) {
        ci.cancel();
    }
}