package com.Maxwell.weaponmaster_handheld_weaponstudio.event;

import com.Maxwell.weaponmaster_handheld_weaponstudio.item.ModItems;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
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
        if (!(event.getSource().getEntity() instanceof net.minecraft.world.entity.player.Player)) return;
        float maxHP = entity.getMaxHealth();
        if (maxHP < 100) return;
        long totalExpPool = (long) (maxHP * 10);
        long infiniteCount = 0;
        long coreCount = 0;
        long scrollCount = 0;
        long shardCount = 0;
        if (totalExpPool >= 2147483647L) {
            infiniteCount = totalExpPool / 2147483647L;
            totalExpPool %= 2147483647L;
        }
        if (totalExpPool >= 10000000) {
            coreCount = totalExpPool / 10000000;
            totalExpPool %= 10000000;
        }
        if (totalExpPool >= 100000) {
            scrollCount = totalExpPool / 100000;
            totalExpPool %= 100000;
        }
        if (totalExpPool >= 1000) {
            shardCount = totalExpPool / 1000;
        }
        addDrop(event, entity, ModItems.WM_EXP_INFINITY.get(), infiniteCount);
        addDrop(event, entity, ModItems.WM_EXP_CORE.get(), coreCount);
        addDrop(event, entity, ModItems.WM_EXP_SCROLL.get(), scrollCount);
        addDrop(event, entity, ModItems.WM_EXP_SHARD.get(), shardCount);
    }

    private static void addDrop(LivingDropsEvent event, LivingEntity entity, Item item, long count) {
        if (count <= 0) return;
        long remaining = count;
        while (remaining > 0) {
            int stackSize = (int) Math.min(remaining, 64);
            ItemStack stack = new ItemStack(item, stackSize);
            ItemEntity itemEntity = new ItemEntity(entity.level(), entity.getX(), entity.getY(), entity.getZ(), stack);
            itemEntity.setDefaultPickUpDelay();
            event.getDrops().add(itemEntity);
            remaining -= stackSize;
        }
    }
}