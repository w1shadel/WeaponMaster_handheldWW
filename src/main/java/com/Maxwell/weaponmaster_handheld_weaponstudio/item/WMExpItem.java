package com.Maxwell.weaponmaster_handheld_weaponstudio.item;

import com.sky.weaponmaster.SweeperWeapon;
import com.sky.weaponmaster.abilities.AbilityAsset.LevelPath;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WMExpItem extends Item {
    private final long expValue;

    public WMExpItem(Properties props, long expValue) {
        super(props);
        this.expValue = expValue;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack held = player.getMainHandItem();
        ItemStack item = player.getItemInHand(hand);
        if (held.getItem() instanceof SweeperWeapon) {
            if (!world.isClientSide) {
                for (LevelPath path : LevelPath.values()) {
                    SweeperWeapon.addExp(held, player, (int) expValue, path, true);
                }
                if (!player.getAbilities().instabuild) item.shrink(1);
            }
            return InteractionResultHolder.consume(item);
        }
        return InteractionResultHolder.pass(item);
    }
}