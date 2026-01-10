package com.Maxwell.weaponmaster_handheld_weaponstudio.item;

import com.Maxwell.weaponmaster_handheld_weaponstudio.HandheldWorkbench;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Rarity;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, HandheldWorkbench.MODID);
    public static final RegistryObject<Item> PORTABLE_WORKBENCH = ITEMS.register("portable_workbench",
            () -> new com.Maxwell.weaponmaster_handheld_weaponstudio.PortableWorkStationItem(new Item.Properties()));
    public static final RegistryObject<Item> WM_EXP_SHARD = ITEMS.register("wm_exp_shard",
            () -> new WMExpItem(new Item.Properties().rarity(Rarity.COMMON), 1000));
    public static final RegistryObject<Item> WM_EXP_SCROLL = ITEMS.register("wm_exp_scroll",
            () -> new WMExpItem(new Item.Properties().rarity(Rarity.RARE).fireResistant(), 100000));
    public static final RegistryObject<Item> WM_EXP_CORE = ITEMS.register("wm_exp_core",
            () -> new WMExpItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), 10000000));
    public static final RegistryObject<Item> WM_EXP_INFINITY = ITEMS.register("wm_exp_infinity",
            () -> new WMExpItem(new Item.Properties().rarity(Rarity.EPIC).fireResistant(), 2147483647));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}