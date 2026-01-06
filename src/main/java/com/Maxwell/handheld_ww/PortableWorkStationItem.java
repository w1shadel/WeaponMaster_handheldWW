package com.Maxwell.handheld_ww;

import com.sky.weaponmaster.WorkStationMenu;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraftforge.network.NetworkHooks;

public class PortableWorkStationItem extends Item {

    public PortableWorkStationItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);

        // サーバー側でのみ実行
        if (!world.isClientSide()) {
            if (player instanceof ServerPlayer serverPlayer) {
                MenuProvider containerProvider = new MenuProvider() {
                    @Override
                    public Component getDisplayName() {
                        return Component.empty();
                    }

                    @Override
                    public AbstractContainerMenu createMenu(int windowId, Inventory playerInventory, Player playerEntity) {
                        return new WorkStationMenu(windowId, playerInventory);
                    }
                };

                // メニューを開く
                NetworkHooks.openScreen(serverPlayer, containerProvider);
            }
        }

        return InteractionResultHolder.sidedSuccess(itemstack, world.isClientSide());
    }
}