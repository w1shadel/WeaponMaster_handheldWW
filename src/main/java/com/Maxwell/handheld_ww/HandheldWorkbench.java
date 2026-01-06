package com.Maxwell.handheld_ww;

import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(HandheldWorkbench.MODID)
public class HandheldWorkbench
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "handheld_ww";
    public HandheldWorkbench(FMLJavaModLoadingContext context)
    {
        IEventBus modEventBus = context.getModEventBus();
        ITEMS.register(modEventBus);
    }
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(Registries.ITEM, MODID);
    public static final RegistryObject<Item> PORTABLE_WORKBENCH = ITEMS.register("portable_workbench",
            () -> new PortableWorkStationItem(new Item.Properties()));
}
