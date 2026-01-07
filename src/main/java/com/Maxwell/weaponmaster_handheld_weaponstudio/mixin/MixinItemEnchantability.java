package com.Maxwell.weaponmaster_handheld_weaponstudio.mixin;

import com.sky.weaponmaster.SweeperWeapon;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentCategory;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class MixinItemEnchantability {

    
    @Inject(method = "getEnchantmentValue()I", at = @At("HEAD"), cancellable = true)
    private void onGetEnchantmentValue(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof SweeperWeapon) {
            cir.setReturnValue(15);
        }
    }
}