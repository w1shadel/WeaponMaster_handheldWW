package com.Maxwell.weaponmaster_handheld_weaponstudio.event;

import com.Maxwell.weaponmaster_handheld_weaponstudio.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class WMDynamicDropHandler {
    @SubscribeEvent
    public static void onLivingDrop(LivingDropsEvent event) {
        LivingEntity entity = event.getEntity();
        if (entity.level().isClientSide) return;
        float maxHP = entity.getMaxHealth();
        if (maxHP < 100) return;
        long totalExpPool = (long) (maxHP * 10);
        long mythicCount = totalExpPool / 1000000;
        totalExpPool %= 1000000;
        spawnItems(entity, mythicCount, ModItems.WM_EXP_CORE.get());
        long rareCount = totalExpPool / 10000;
        totalExpPool %= 10000;
        spawnItems(entity, rareCount, ModItems.WM_EXP_SCROLL.get());
        long commonCount = totalExpPool / 1000;
        spawnItems(entity, commonCount, ModItems.WM_EXP_SHARD.get());
    }

    private static void spawnItems(LivingEntity entity, long count, Item item) {
        if (count <= 0) return;
        long remaining = count;
        while (remaining > 0) {
            int stackSize = (int) Math.min(remaining, 64);
            entity.spawnAtLocation(new ItemStack(item, stackSize));
            remaining -= stackSize;
        }
    }
}