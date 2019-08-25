package us.kosdt.mysticalmultitools.gui;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class AdaptiteBagOutputSlot extends Slot {

    public AdaptiteBagOutputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return false;
    }
}
