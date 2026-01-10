package com.Maxwell.weaponmaster_handheld_weaponstudio.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.sky.weaponmaster.SweeperWeapon;
import com.sky.weaponmaster.abilities.AbilityAsset.LevelPath;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public class WMLevelCommand {
    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("wm-addexp")
                .requires(source -> source.hasPermission(2)) 
                .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                        .executes(context -> {
                            return addExpToHeldItem(context.getSource(), IntegerArgumentType.getInteger(context, "amount"));
                        })
                )
        );
    }

    private static int addExpToHeldItem(CommandSourceStack source, int amount) throws com.mojang.brigadier.exceptions.CommandSyntaxException {
        ServerPlayer player = source.getPlayerOrException();
        ItemStack stack = player.getMainHandItem();

        if (stack.getItem() instanceof SweeperWeapon) {

            for (LevelPath path : LevelPath.values()) {
                SweeperWeapon.addExp(stack, player, amount, path);
            }

            source.sendSuccess(() -> Component.literal("武器に " + amount + " 経験値を付与しました（全ステータス）"), true);
            return 1;
        } else {
            source.sendFailure(Component.literal("WeaponMasterの武器をメインハンドに持ってください。"));
            return 0;
        }
    }
}