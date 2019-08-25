package us.kosdt.mysticalmultitools.gui;

import net.minecraft.nbt.NBTTagCompound;
import us.kosdt.mysticalmultitools.blocks.NBTTags;
import us.kosdt.mysticalmultitools.config.AdaptiteStatConfig;

import java.util.Comparator;

public class AdaptiteBagStats {

    public int durability;
    public int speed;

    public static Comparator<AdaptiteBagStats> byDurability = ((stats, other) -> {
        int diff = stats.durability - other.durability;
        if (diff == 0)
            return stats.speed - other.speed;
        else
            return diff;
    });
    public static Comparator<AdaptiteBagStats> bySpeed = ((stats, other) -> {
        int diff = stats.speed - other.speed;
        if (diff == 0)
            return stats.durability - other.durability;
        else
            return diff;
    });

    public AdaptiteBagStats(NBTTagCompound nbt) {
        readFromNBT(nbt);
    }

    public AdaptiteBagStats(int durability, int speed) {
        this.durability = durability;
        this.speed = speed;
    }

    @Override
    public boolean equals(Object other) {
        if (other == null)
            return false;
        if (!(other instanceof AdaptiteBagStats))
            return false;
        AdaptiteBagStats otherStats = (AdaptiteBagStats) other;
        if (otherStats.durability == this.durability && otherStats.speed == this.speed)
            return true;
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 89;
        hash = 151 * hash + Integer.hashCode(durability);
        hash = 151 * hash + Integer.hashCode(speed);
        return hash;
    }

    public void readFromNBT(NBTTagCompound nbt) {
        if (nbt == null) {
            this.durability = AdaptiteStatConfig.ADAPTITE_INITIAL_DURABILITY;
            this.speed = AdaptiteStatConfig.ADAPTITE_INITIAL_SPEED;
            return;
        }

        if (nbt.hasKey(NBTTags.DURABILITY.tagName)) {
            this.durability = (int) NBTTags.getTag(nbt, NBTTags.DURABILITY);
        } else {
            this.durability = AdaptiteStatConfig.ADAPTITE_INITIAL_DURABILITY;
        }

        if (nbt.hasKey(NBTTags.SPEED.tagName)) {
            this.speed = (int) NBTTags.getTag(nbt, NBTTags.SPEED);
        } else {
            this.speed = AdaptiteStatConfig.ADAPTITE_INITIAL_SPEED;
        }
    }

    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        NBTTagCompound newNBTTag;
        if (nbt == null) {
            newNBTTag = new NBTTagCompound();
        } else {
            newNBTTag = nbt;
        }
        NBTTags.setTag(newNBTTag, NBTTags.DURABILITY, this.durability);
        NBTTags.setTag(newNBTTag, NBTTags.SPEED, this.speed);
        return newNBTTag;
    }
}
