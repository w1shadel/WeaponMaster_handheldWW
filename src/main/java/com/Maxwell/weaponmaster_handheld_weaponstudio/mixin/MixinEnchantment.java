package com.Maxwell.weaponmaster_handheld_weaponstudio.mixin;

import com.sky.weaponmaster.SweeperWeapon;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Enchantment.class)
public abstract class MixinEnchantment {

    @Shadow public EnchantmentCategory category;

    
    @Inject(method = "canEnchant", at = @At("HEAD"), cancellable = true)
    private void onCanEnchant(ItemStack pStack, CallbackInfoReturnable<Boolean> cir) {
        if (pStack.getItem() instanceof SweeperWeapon) {
            if (this.category == EnchantmentCategory.WEAPON || this.category == EnchantmentCategory.BREAKABLE) {
                cir.setReturnValue(true);
            }
        }
    }

    
    @Inject(method = "canApplyAtEnchantingTable", at = @At("HEAD"), cancellable = true, remap = false)
    private void onCanApplyAtEnchantingTable(ItemStack stack, CallbackInfoReturnable<Boolean> cir) {
        if (stack.getItem() instanceof SweeperWeapon) {
            if (this.category == EnchantmentCategory.WEAPON || this.category == EnchantmentCategory.BREAKABLE) {
                cir.setReturnValue(true);
            }
        }
    }
}