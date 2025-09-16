package com.pp.augmenta.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.pp.augmenta.attachment.ModAttachments;
import com.pp.augmenta.augment.AugmentInventory;
import com.pp.augmenta.menu.AugmenterMenu;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.EntityArgument;
import net.minecraft.commands.arguments.item.ItemArgument;
import net.minecraft.commands.arguments.item.ItemInput;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

public final class AugmentCommands {
    private AugmentCommands() {}

    public static void register(RegisterCommandsEvent event) {
        CommandDispatcher<CommandSourceStack> dispatcher = event.getDispatcher();
        CommandBuildContext context = event.getBuildContext();

        var root = Commands.literal("augmenta")
                .requires(source -> source.hasPermission(2));

        var setItemArgument = Commands.argument("item", ItemArgument.item(context))
                .executes(ctx -> setAugment(ctx,
                        EntityArgument.getPlayer(ctx, "player"),
                        IntegerArgumentType.getInteger(ctx, "slot"),
                        ItemArgument.getItem(ctx, "item")));
        var setSlotArgument = Commands.argument("slot", IntegerArgumentType.integer(1, AugmentInventory.SLOT_COUNT))
                .then(setItemArgument);
        var setPlayerArgument = Commands.argument("player", EntityArgument.player())
                .then(setSlotArgument);
        root.then(Commands.literal("set").then(setPlayerArgument));

        var clearSlotArgument = Commands.argument("slot", IntegerArgumentType.integer(1, AugmentInventory.SLOT_COUNT))
                .executes(ctx -> clearAugment(ctx,
                        EntityArgument.getPlayer(ctx, "player"),
                        IntegerArgumentType.getInteger(ctx, "slot")));
        var clearPlayerArgument = Commands.argument("player", EntityArgument.player())
                .then(clearSlotArgument);
        root.then(Commands.literal("clear").then(clearPlayerArgument));

        dispatcher.register(root);
    }

    private static int setAugment(CommandContext<CommandSourceStack> context, ServerPlayer target, int slot, ItemInput input)
            throws CommandSyntaxException {
        int index = slot - 1;
        AugmentInventory inventory = target.getData(ModAttachments.AUGMENTS.get());
        ItemStack stack = input.createItemStack(1, false);
        inventory.setStackInSlot(index, stack);
        updateOpenMenu(target, inventory);

        context.getSource().sendSuccess(
                () -> Component.translatable("commands.augmenta.set", slot, target.getDisplayName(), stack.getHoverName()),
                true);
        return 1;
    }

    private static int clearAugment(CommandContext<CommandSourceStack> context, ServerPlayer target, int slot) {
        int index = slot - 1;
        AugmentInventory inventory = target.getData(ModAttachments.AUGMENTS.get());
        inventory.setStackInSlot(index, ItemStack.EMPTY);
        updateOpenMenu(target, inventory);

        context.getSource().sendSuccess(
                () -> Component.translatable("commands.augmenta.clear", slot, target.getDisplayName()),
                true);
        return 1;
    }

    private static void updateOpenMenu(ServerPlayer player, AugmentInventory inventory) {
        if (player.containerMenu instanceof AugmenterMenu menu && menu.getAugmentInventory() == inventory) {
            menu.broadcastChanges();
        }
    }
}
