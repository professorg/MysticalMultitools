package us.kosdt.mysticalmultitools.config;

import net.minecraftforge.common.config.Config;
import us.kosdt.mysticalmultitools.MysticalMultitools;

@Config(modid = MysticalMultitools.MODID, category = "oregen", name = MysticalMultitools.MODID + "_oregen")
public class OregenConfig {

    @Config.Comment(value = "Enable retrogen")
    public static boolean RETROGEN = true;

    @Config.Comment(value = "Enable verbose logging for retrogen")
    public static boolean VERBOSE = false;

    @Config.Comment(value = "Minimum size of every ore vein")
    public static int MIN_VEIN_SIZE = 1;

    @Config.Comment(value = "Maximum size of every ore vein")
    public static int MAX_VEIN_SIZE = 5;

    @Config.Comment(value = "Maximum veins per chunk")
    public static int CHANCES_TO_SPAWN = 5;

    @Config.Comment(value = "Minimum height for the ore")
    public static int MIN_Y = 2;

    @Config.Comment(value = "Maximum height for the ore")
    public static int MAX_Y = 10;

}
