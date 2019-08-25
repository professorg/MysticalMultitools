package us.kosdt.mysticalmultitools.gui;

import net.minecraft.block.Block;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import us.kosdt.mysticalmultitools.blocks.ModBlocks;

public class AdaptiteBagInputSlot extends Slot {

    public AdaptiteBagInputSlot(IInventory inventoryIn, int index, int xPosition, int yPosition) {
        super(inventoryIn, index, xPosition, yPosition);
    }

    @Override
    public boolean isItemValid(ItemStack itemStack) {
        return Block.getBlockFromItem(itemStack.getItem()) == ModBlocks.blockAdaptite;
    }
}
