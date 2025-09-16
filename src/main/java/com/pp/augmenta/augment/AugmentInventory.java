package com.pp.augmenta.augment;

import net.minecraft.world.item.ItemStack;
import net.neoforged.neoforge.items.ItemStackHandler;

/**
 * Stores the augment items installed in a player's cybernetics.
 */
public class AugmentInventory extends ItemStackHandler {
    public static final int SLOT_COUNT = 15;

    public AugmentInventory() {
        super(SLOT_COUNT);
    }

    @Override
    protected void onContentsChanged(int slot) {
        super.onContentsChanged(slot);
    }

    /**
     * Sets the contents of this inventory to match the other handler. Existing stacks are copied.
     */
    public void copyFrom(AugmentInventory other) {
        for (int i = 0; i < getSlots(); i++) {
            ItemStack stack = other.getStackInSlot(i);
            setStackInSlot(i, stack.copy());
        }
    }
}
