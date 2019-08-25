package us.kosdt.mysticalmultitools.items;

import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ModItems {

    @GameRegistry.ObjectHolder("mysticalmultitools:adaptite")
    public static ItemAdaptite itemAdaptite;
    @GameRegistry.ObjectHolder("mysticalmultitools:adaptite_bag")
    public static ItemAdaptiteBag itemAdaptiteBag;

    @SideOnly(Side.CLIENT)
    public static void initModels() {
        itemAdaptite.initModel();
        itemAdaptiteBag.initModel();
    }
}
