package com.pp.augmenta.menu;

import com.pp.augmenta.augment.AugmentInventory;
import com.pp.augmenta.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.SlotItemHandler;

public class AugmenterMenu extends AbstractContainerMenu {
    private static final int AUGMENT_ROWS = 3;
    private static final int AUGMENT_COLUMNS = 5;
    private static final int AUGMENT_SLOT_COUNT = AUGMENT_ROWS * AUGMENT_COLUMNS;

    private final AugmentInventory augmentInventory;
    private final ContainerLevelAccess access;

    public AugmenterMenu(int id, Inventory playerInventory, RegistryFriendlyByteBuf extraData) {
        this(id, playerInventory, new AugmentInventory(), extraData == null ? ContainerLevelAccess.NULL
                : ContainerLevelAccess.create(playerInventory.player.level(), extraData.readBlockPos()));
    }

    public AugmenterMenu(int id, Inventory playerInventory, AugmentInventory inventory, BlockPos pos) {
        this(id, playerInventory, inventory, ContainerLevelAccess.create(playerInventory.player.level(), pos));
    }

    private AugmenterMenu(int id, Inventory playerInventory, AugmentInventory inventory, ContainerLevelAccess access) {
        super(ModMenuTypes.AUGMENTER.get(), id);
        this.augmentInventory = inventory;
        this.access = access;

        addAugmentSlots();
        addPlayerInventory(playerInventory);
    }

    private void addAugmentSlots() {
        int startX = 44;
        int startY = 17;

        for (int row = 0; row < AUGMENT_ROWS; row++) {
            for (int column = 0; column < AUGMENT_COLUMNS; column++) {
                int index = column + row * AUGMENT_COLUMNS;
                addSlot(new SlotItemHandler(augmentInventory, index, startX + column * 18, startY + row * 18));
            }
        }
    }

    private void addPlayerInventory(Inventory playerInventory) {
        int slotSize = 18;
        int playerStartY = 84;

        // Main inventory
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                int slotIndex = column + row * 9 + 9;
                addSlot(new Slot(playerInventory, slotIndex, 8 + column * slotSize, playerStartY + row * slotSize));
            }
        }

        // Hotbar
        for (int column = 0; column < 9; column++) {
            addSlot(new Slot(playerInventory, column, 8 + column * slotSize, playerStartY + 58));
        }
    }

    public AugmentInventory getAugmentInventory() {
        return augmentInventory;
    }

    @Override
    public boolean stillValid(Player player) {
        return stillValid(access, player, ModBlocks.AUGMENTER.get());
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(index);
        if (slot != null && slot.hasItem()) {
            ItemStack slotStack = slot.getItem();
            itemstack = slotStack.copy();

            if (index < AUGMENT_SLOT_COUNT) {
                if (!moveItemStackTo(slotStack, AUGMENT_SLOT_COUNT, this.slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(slotStack, 0, AUGMENT_SLOT_COUNT, false)) {
                return ItemStack.EMPTY;
            }

            if (slotStack.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemstack;
    }

}
