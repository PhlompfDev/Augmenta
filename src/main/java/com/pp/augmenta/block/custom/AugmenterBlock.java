package com.pp.augmenta.block.custom;

import com.mojang.serialization.MapCodec;
import com.pp.augmenta.attachment.ModAttachments;
import com.pp.augmenta.menu.AugmenterMenu;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.SimpleMenuProvider;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.HorizontalDirectionalBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.Nullable;

public class AugmenterBlock extends HorizontalDirectionalBlock {
    public static final MapCodec<AugmenterBlock> CODEC = simpleCodec(AugmenterBlock::new);

    public AugmenterBlock(Properties properties) {
        super(properties);
        registerDefaultState(stateDefinition.any().setValue(FACING, Direction.NORTH));
    }

    @Override
    protected MapCodec<? extends HorizontalDirectionalBlock> codec() {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockState getStateForPlacement(BlockPlaceContext context) {
        return super.defaultBlockState().setValue(FACING, context.getHorizontalDirection().getOpposite());
    }

    @Override
    protected InteractionResult useWithoutItem(BlockState state, Level level, BlockPos pos, Player player, BlockHitResult hit) {
        if (!(player instanceof ServerPlayer serverPlayer)) {
            return InteractionResult.SUCCESS;
        }

        var augmentInventory = serverPlayer.getData(ModAttachments.AUGMENTS.get());
        var menuProvider = new SimpleMenuProvider(
                (containerId, playerInventory, ignored) ->
                        new AugmenterMenu(containerId, playerInventory, augmentInventory, pos),
                Component.translatable("menu.augmenta.augmenter"));

        serverPlayer.openMenu(menuProvider, buf -> buf.writeBlockPos(pos));
        return InteractionResult.CONSUME;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(FACING);
    }
}
