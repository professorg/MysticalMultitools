package us.kosdt.mysticalmultitools.gui;

public class AdaptiteBagConstants {

    public static final int HOTBAR_LENGTH = 9;
    public static final int PLAYER_INVENTORY_COLS = 9;
    public static final int PLAYER_INVENTORY_ROWS = 3;

    public static final int CUSTOM_INV_START = 0;
    public static final int INPUT_SLOT = CUSTOM_INV_START;
    public static final int OUTPUT_SLOT = CUSTOM_INV_START + 1;
    public static final int CUSTOM_INV_END = CUSTOM_INV_START + AdaptiteBagInventory.INV_SIZE;

    public static final int PLAYER_INVENTORY_START = CUSTOM_INV_END;
    public static final int PLAYER_INVENTORY_END = PLAYER_INVENTORY_START + PLAYER_INVENTORY_COLS * PLAYER_INVENTORY_ROWS;

    public static final int HOTBAR_START = PLAYER_INVENTORY_END;
    public static final int HOTBAR_END = HOTBAR_START + HOTBAR_LENGTH;

    public static final String INVENTORY_NBT_NAME = "adaptite_bag_inventory";

}
