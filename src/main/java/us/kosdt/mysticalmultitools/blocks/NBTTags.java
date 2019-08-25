package us.kosdt.mysticalmultitools.blocks;

import net.minecraft.nbt.NBTTagCompound;

import static org.apache.commons.lang3.reflect.TypeUtils.isInstance;

public enum NBTTags {

    BAG_INVENTORY(String.class, "adaptite_bag"),
    COUNT(Integer.class, "adaptite_count"),
    DURABILITY(Integer.class, "adaptite_durability"),
    SPEED(Integer.class, "adaptite_speed");

    public String tagName;
    public Class type;

    NBTTags(Class type, String tagName) {
        this.type = type;
        this.tagName = tagName;
    }

    public static void setTag(NBTTagCompound nbt, NBTTags tag, Object value) {
        if (!(isInstance(value, tag.type)))
            throw new IllegalArgumentException("Tag value must match type");
        if (tag.type == Float.class) {
            nbt.setFloat(tag.tagName, (Float) value);
        } else if (tag.type == Integer.class) {
            nbt.setInteger(tag.tagName, (Integer) value);
        } else if (tag.type == String.class) {
            nbt.setString(tag.tagName, (String) value);
        } else {
            throw new IllegalArgumentException("Tag type not implemented");
        }
    }

    public static Object getTag(NBTTagCompound nbt, NBTTags tag) {
        if (tag.type == Float.class) {
            return nbt.getFloat(tag.tagName);
        } else if (tag.type == Integer.class) {
            return nbt.getInteger(tag.tagName);
        } else if (tag.type == String.class) {
            return nbt.getString(tag.tagName);
        } else {
            throw new IllegalArgumentException("Tag type not implemented");
        }
    }
}
