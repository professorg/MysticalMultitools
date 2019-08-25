package us.kosdt.mysticalmultitools.blocks;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import us.kosdt.mysticalmultitools.config.AdaptiteStatConfig;

public class TileBlockAdaptite extends TileEntity {

    public int durabilityStat;
    public int speedStat;

    public TileBlockAdaptite() {
        durabilityStat = AdaptiteStatConfig.ADAPTITE_INITIAL_DURABILITY;
        speedStat = AdaptiteStatConfig.ADAPTITE_INITIAL_SPEED;
        markDirty();
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        NBTTags.setTag(nbt, NBTTags.DURABILITY, durabilityStat);
        NBTTags.setTag(nbt, NBTTags.SPEED, speedStat);
        return nbt;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);
        if (nbt.hasKey(NBTTags.DURABILITY.tagName)) {
            durabilityStat = (int) NBTTags.getTag(nbt, NBTTags.DURABILITY);
        }
        if (nbt.hasKey(NBTTags.SPEED.tagName)) {
            speedStat = (int) NBTTags.getTag(nbt, NBTTags.SPEED);
        }
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        NBTTagCompound nbtTag = new NBTTagCompound();
        nbtTag = writeToNBT(nbtTag);
        return nbtTag;
    }

    @Override
    public void handleUpdateTag(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

}
