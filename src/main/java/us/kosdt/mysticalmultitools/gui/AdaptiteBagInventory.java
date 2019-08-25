package us.kosdt.mysticalmultitools.gui;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.common.util.Constants;
import org.apache.logging.log4j.Level;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.blocks.BlockAdaptite;
import us.kosdt.mysticalmultitools.blocks.ModBlocks;
import us.kosdt.mysticalmultitools.blocks.NBTTags;
import us.kosdt.mysticalmultitools.items.ItemAdaptiteBag;
import us.kosdt.mysticalmultitools.items.ItemBlockAdaptite;
import us.kosdt.mysticalmultitools.network.BagSelectedSlotMessage;
import us.kosdt.mysticalmultitools.network.MysticalMultitoolsPacketHandler;

import java.util.Comparator;
import java.util.Map;
import java.util.TreeMap;

public class AdaptiteBagInventory implements IInventory {

    public static final int INV_SIZE = 2;

    private Map<AdaptiteBagStats, Integer> storage;
    public int selected;
    private ItemStack selectedItem;

    private ItemStack invItem;

    public AdaptiteBagInventory(ItemStack item) {
        storage = new TreeMap<>(AdaptiteBagStats.byDurability);
        invItem = item;
        if (!item.hasTagCompound()) {
            item.setTagCompound(new NBTTagCompound());
        }
        readFromNBT(item.getTagCompound());
    }

    private void sortBy(Comparator<AdaptiteBagStats> comparator) {
        Map<AdaptiteBagStats, Integer> newStorage = new TreeMap<>(comparator);
        newStorage.putAll(storage);
        storage = newStorage;
        this.markDirty();
    }

    private Map.Entry<AdaptiteBagStats, Integer> getEntry(int index) {
        if (storage.size() <= 0)
            return null;
        return (Map.Entry) storage.entrySet().toArray()[index];
    }

    @Override
    public int getSizeInventory() {
        return storage.size();
    }

    @Override
    public boolean isEmpty() {
        return storage.isEmpty();
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        if (slot == AdaptiteBagConstants.INPUT_SLOT)
            return ItemStack.EMPTY;
        if (slot == AdaptiteBagConstants.OUTPUT_SLOT) {
            selectedItem = updateSelectedItem();
            MysticalMultitoolsPacketHandler.INSTANCE.sendToServer(new BagSelectedSlotMessage(selected));
            MysticalMultitools.logger.log(Level.INFO, selected);
            return selectedItem;
            /*if (storage.size() == 0)
                return ItemStack.EMPTY;
            Map.Entry<AdaptiteBagStats, Integer> entry = getEntry(selected);
            //ItemStack stack = new ItemStack(ModBlocks.blockAdaptite, entry.getValue());
            ItemStack stack = new ItemStack(ModBlocks.blockAdaptite, Math.min(entry.getValue(), 64));
            NBTTagCompound nbt = stack.getTagCompound();
            if (nbt == null)
                nbt = new NBTTagCompound();
            NBTTags.setTag(nbt, NBTTags.DURABILITY, entry.getKey().durability);
            NBTTags.setTag(nbt, NBTTags.SPEED, entry.getKey().speed);
            stack.setTagCompound(nbt);
            if (stack != null)
                return stack;*/
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        ItemStack stack = getStackInSlot(slot);
        if (stack != null) {
            if (stack.getCount() > amount) {
                Map.Entry<AdaptiteBagStats, Integer> entry = getEntry(selected);
                storage.put(entry.getKey(), storage.get(entry.getKey()) - amount);
                stack = stack.splitStack(amount);
                this.markDirty();
            } else {
                setInventorySlotContents(slot, ItemStack.EMPTY);
            }
            return stack;
        }
        return ItemStack.EMPTY;
    }

    @Override
    public ItemStack removeStackFromSlot(int slot) {
        ItemStack stack = getStackInSlot(slot);
        decrStackSize(slot, Math.min(stack.getCount(), 64));
        return stack;
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        if (slot == AdaptiteBagConstants.INPUT_SLOT) {
            if (stack.getItem() instanceof ItemBlockAdaptite) {
                ItemBlockAdaptite itemBlockAdaptite = (ItemBlockAdaptite) stack.getItem();
                NBTTagCompound nbt = stack.getTagCompound();
                AdaptiteBagStats stats = new AdaptiteBagStats(nbt);
                int count = stack.getCount();
                if (storage.get(stats) != null) {
                    storage.put(stats, storage.get(stats) + count);
                } else {
                    storage.put(stats, count);
                }
            }
        }
        if (slot == AdaptiteBagConstants.OUTPUT_SLOT) {
            if (stack == null || stack.isEmpty()) {
                selectedItem = ItemStack.EMPTY;
                Map.Entry<AdaptiteBagStats,Integer> entry = getEntry(selected);
                if (entry != null) {
                    if (entry.getValue() <= 64) {
                        storage.remove(entry.getKey());
                    } else {
                        storage.put(entry.getKey(), entry.getValue() - 64);
                    }
                }
            }
        }
        this.markDirty();
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public void markDirty() {
        if (storage.size() <= 0)
            selected = 0;
        else
            selected = Math.min(Math.max(selected, 0), storage.size() - 1);
        writeToNBT(invItem.getTagCompound());
    }

    private ItemStack updateSelectedItem() {
        if (storage.size() <= 0) {
            return ItemStack.EMPTY;
        }
        Map.Entry<AdaptiteBagStats, Integer> entry = getEntry(selected);
        //ItemStack stack = new ItemStack(ModBlocks.blockAdaptite, entry.getValue());
        ItemStack stack = new ItemStack(ModBlocks.blockAdaptite, Math.min(entry.getValue(), 64));
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            nbt = new NBTTagCompound();
        NBTTags.setTag(nbt, NBTTags.DURABILITY, entry.getKey().durability);
        NBTTags.setTag(nbt, NBTTags.SPEED, entry.getKey().speed);
        stack.setTagCompound(nbt);
        this.markDirty();
        return stack;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) { }

    @Override
    public void closeInventory(EntityPlayer player) { }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if (slot == AdaptiteBagConstants.INPUT_SLOT)
            return stack.getItem() instanceof ItemBlockAdaptite;
        if (slot == AdaptiteBagConstants.OUTPUT_SLOT)
            return false;
        return false;
    }

    @Override
    public int getField(int id) {
        return 0;
    }

    @Override
    public void setField(int id, int value) { }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        storage.clear();
        this.markDirty();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }

    public void readFromNBT(NBTTagCompound compound) {
        NBTTagList items = compound.getTagList(AdaptiteBagConstants.INVENTORY_NBT_NAME, Constants.NBT.TAG_COMPOUND);

        for (int i = 0; i < items.tagCount(); i++) {
            NBTTagCompound item = (NBTTagCompound) items.getCompoundTagAt(i);
            int count = (int) NBTTags.getTag(item, NBTTags.COUNT);
            AdaptiteBagStats stats = new AdaptiteBagStats(item);
            storage.put(stats, count);
        }
        this.markDirty();
    }

    public void writeToNBT(NBTTagCompound compound) {
        NBTTagList items = new NBTTagList();

        int i = 0;
        for (Map.Entry<AdaptiteBagStats, Integer> entry : storage.entrySet()) {
            NBTTagCompound item = new NBTTagCompound();
            NBTTags.setTag(item, NBTTags.COUNT, entry.getValue());
            item = entry.getKey().writeToNBT(item);
            items.appendTag(item);
        }

        compound.setTag(AdaptiteBagConstants.INVENTORY_NBT_NAME, items);
    }

    public void first() {
        selected = 0;
        updateSelectedItem();
    }

    public void prev() {
        selected--;
        if (selected < 0) {
            selected = 0;
        }
        updateSelectedItem();
    }

    public void next() {
        selected++;
        if (selected >= storage.size()) {
            selected = storage.size() - 1;
        }
        updateSelectedItem();
    }

    public void last() {
        if (storage.size() == 0)
            selected = 0;
        else
            selected = storage.size() - 1;
        updateSelectedItem();
    }

    public Map.Entry<AdaptiteBagStats, Integer> getSelectedEntry() {
        return getEntry(selected);
    }

    public void setSelected(int selected) {
        this.selected = selected;
    }

}
