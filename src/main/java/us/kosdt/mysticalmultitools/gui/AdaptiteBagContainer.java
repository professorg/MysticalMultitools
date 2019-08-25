package us.kosdt.mysticalmultitools.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Level;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.items.ItemBlockAdaptite;

public class AdaptiteBagContainer extends Container {

    public AdaptiteBagContainer(EntityPlayer player, InventoryPlayer inventoryPlayer, AdaptiteBagInventory inventoryCustom) {
        this.addSlotToContainer(new AdaptiteBagInputSlot(inventoryCustom, AdaptiteBagConstants.INPUT_SLOT, 16,16));
        this.addSlotToContainer(new AdaptiteBagOutputSlot(inventoryCustom, AdaptiteBagConstants.OUTPUT_SLOT, 34, 16));

        int i;
        // Add vanilla PLAYER INVENTORY
        for (i = 0; i < AdaptiteBagConstants.PLAYER_INVENTORY_ROWS; ++i) {
            for (int j = 0; j < AdaptiteBagConstants.PLAYER_INVENTORY_COLS; ++j) {
                this.addSlotToContainer(new Slot(inventoryPlayer, j + i * 9 + 9, 8 + j * 18, 84 + i * 18));
            }
        }

        // Add ACTION BAR / HOTBAR
        for (i = 0; i < AdaptiteBagConstants.HOTBAR_LENGTH; ++i) {
            this.addSlotToContainer(new Slot(inventoryPlayer, i, 8 + i * 18, 142));
        }
    }

    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(index);
        //MysticalMultitools.logger.log(Level.INFO, "Tried to transfer stack");

        if (slot != null && slot.getHasStack()) {
            ItemStack slotItemStack = slot.getStack();
            itemStack = slotItemStack.copy();
            if (index >= AdaptiteBagConstants.CUSTOM_INV_START && index < AdaptiteBagConstants.CUSTOM_INV_END) { // In custom inventory
                MysticalMultitools.logger.log(Level.INFO, "It's coming from the custom inventory");
                if (index == AdaptiteBagConstants.INPUT_SLOT)
                    return ItemStack.EMPTY;
                if (index == AdaptiteBagConstants.OUTPUT_SLOT) { // Move out of bag
                    //MysticalMultitools.logger.log(Level.INFO, "From the output slot");
                    if (!this.mergeItemStack(slotItemStack, AdaptiteBagConstants.PLAYER_INVENTORY_START, AdaptiteBagConstants.HOTBAR_END, true)) {
                        //MysticalMultitools.logger.log(Level.INFO, "Failure");
                        return ItemStack.EMPTY;
                    }
                    int newCount = slotItemStack.getCount();
                    //slot.decrStackSize(slot.getStack().getCount() - newCount);
                    //MysticalMultitools.logger.log(Level.INFO, "Success!");
                }
                slot.onSlotChange(slotItemStack, itemStack);
            } else if (index >= AdaptiteBagConstants.PLAYER_INVENTORY_START && index < AdaptiteBagConstants.HOTBAR_END) { // In player inv or hotbar
                //MysticalMultitools.logger.log(Level.INFO, "It's coming from the player inventory");
                if (slotItemStack.getItem() instanceof ItemBlockAdaptite) { // Move into bag
                    //MysticalMultitools.logger.log(Level.INFO, "It's an adaptite block");
                    if (!this.mergeItemStack(slotItemStack, AdaptiteBagConstants.INPUT_SLOT, AdaptiteBagConstants.INPUT_SLOT + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= AdaptiteBagConstants.PLAYER_INVENTORY_START && index < AdaptiteBagConstants.PLAYER_INVENTORY_END) { // Move from inv to hotbar
                    if (!this.mergeItemStack(slotItemStack, AdaptiteBagConstants.HOTBAR_START, AdaptiteBagConstants.HOTBAR_END, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= AdaptiteBagConstants.HOTBAR_START && index < AdaptiteBagConstants.HOTBAR_END) { // Move from hotbar to inv
                    if (!this.mergeItemStack(slotItemStack, AdaptiteBagConstants.PLAYER_INVENTORY_START, AdaptiteBagConstants.PLAYER_INVENTORY_END, false)) {
                        return ItemStack.EMPTY;
                    }
                }
                slot.onSlotChange(slotItemStack, itemStack);
            }
            // Item must have been merged, or 0 items transferred
            if (slotItemStack.getCount() == 0) { // All items transferred
                slot.putStack(ItemStack.EMPTY); // Null out stack
            } else { // Update stack otherwise
                slot.onSlotChanged();
            }

            if (slotItemStack.getCount() == itemStack.getCount()) { // Number of items didn't change - no transfer
                return ItemStack.EMPTY;
            }

            slot.onTake(player, slotItemStack);
            return slotItemStack;
        }

        return itemStack;
    }

    @Override
    public ItemStack slotClick(int slot, int dragType, ClickType clickType, EntityPlayer player) {
        if (slot >= 0 && getSlot(slot) != null && getSlot(slot).getStack() == player.getHeldItemMainhand()) {
            return ItemStack.EMPTY;
        }
        return super.slotClick(slot, dragType, clickType, player);
    }


    @Override
    public boolean canInteractWith(EntityPlayer playerIn) {
        return true;
    }

    @Override
    public void detectAndSendChanges() {
        super.detectAndSendChanges();
    }

    public void setSelectedSlot(int selectedSlot) {
        ((AdaptiteBagInventory) getSlot(AdaptiteBagConstants.OUTPUT_SLOT).inventory).setSelected(selectedSlot);
    }


}
