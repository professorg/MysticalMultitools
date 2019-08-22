package us.kosdt.mysticalmultitools.blocks;

import net.minecraftforge.fml.common.registry.GameRegistry;

public class ModBlocks {

    @GameRegistry.ObjectHolder("mysticalmultitools:adaptite_block")
    public static BlockAdaptite blockAdaptite;
    @GameRegistry.ObjectHolder("mysticalmultitools:adaptite_ore")
    public static BlockAdaptiteOre blockAdaptiteOre;

    public static void initModels() {
        blockAdaptite.initModel();
        blockAdaptiteOre.initModel();
    }
}
