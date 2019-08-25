package us.kosdt.mysticalmultitools.config;

import net.minecraftforge.common.config.Config;
import us.kosdt.mysticalmultitools.MysticalMultitools;

@Config(modid = MysticalMultitools.MODID, category = "adaptite_stats", name = MysticalMultitools.MODID + "_adaptite_stats")
public class AdaptiteStatConfig {

    @Config.Comment(value = "Adaptite initial durability stat")
    public static int ADAPTITE_INITIAL_DURABILITY = 0;

    @Config.Comment(value = "Adaptite initial speed stat")
    public static int ADAPTITE_INITIAL_SPEED = 0;

    @Config.Comment(value = "Iron initial durability stat")
    public static int IRON_INITIAL_DURABILITY = 1000;

    @Config.Comment(value = "Iron initial speed stat")
    public static int IRON_INITIAL_SPEED = 500;

    @Config.Comment(value = "Gold initial durability stat")
    public static int GOLD_INITIAL_DURABILITY = 0;

    @Config.Comment(value = "Gold initial speed stat")
    public static int GOLD_INITIAL_SPEED = 1500;

    @Config.Comment(value = "Diamond initial durability stat")
    public static int DIAMOND_INITIAL_DURABILITY = 2000;

    @Config.Comment(value = "Diamond initial speed stat")
    public static int DIAMOND_INITIAL_SPEED = 1000;

    @Config.Comment(value = "Should spreading print extra info?")
    public static boolean VERBOSE = true;

}
