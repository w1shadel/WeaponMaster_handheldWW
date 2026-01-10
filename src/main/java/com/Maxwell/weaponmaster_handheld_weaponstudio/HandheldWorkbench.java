package com.Maxwell.weaponmaster_handheld_weaponstudio;

import com.Maxwell.weaponmaster_handheld_weaponstudio.command.WMLevelCommand;
import com.Maxwell.weaponmaster_handheld_weaponstudio.item.ModItems;
import net.minecraft.world.item.CreativeModeTabs;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.BuildCreativeModeTabContentsEvent;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(HandheldWorkbench.MODID)
public class HandheldWorkbench {
    public static final String MODID = "weaponmaster_handheld_weaponstudio";

    public HandheldWorkbench(FMLJavaModLoadingContext context) {
        IEventBus modEventBus = context.getModEventBus();
        ModItems.register(modEventBus);
        modEventBus.addListener(this::addCreative);
        MinecraftForge.EVENT_BUS.register(this);
    }

    private void addCreative(BuildCreativeModeTabContentsEvent event) {
        if (event.getTabKey() == CreativeModeTabs.COMBAT) {
            event.accept(ModItems.PORTABLE_WORKBENCH);
            event.accept(ModItems.WM_EXP_SHARD);
            event.accept(ModItems.WM_EXP_SCROLL);
            event.accept(ModItems.WM_EXP_CORE);
            event.accept(ModItems.WM_EXP_INFINITY);
        }
    }

    @SubscribeEvent
    public void onRegisterCommands(RegisterCommandsEvent event) {
        WMLevelCommand.register(event.getDispatcher());
    }
}