package us.kosdt.mysticalmultitools;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import org.apache.logging.log4j.Logger;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import us.kosdt.mysticalmultitools.blocks.ModBlocks;
import us.kosdt.mysticalmultitools.proxy.CommonProxy;

@Mod(modid = MysticalMultitools.MODID, name = MysticalMultitools.MODNAME, version = MysticalMultitools.MODVERSION, dependencies = "required-after:forge@[14.23.5.2838,)", useMetadata = true)
public class MysticalMultitools {

    public static final String MODID = "mysticalmultitools";
    public static final String MODNAME = "Mystical Multitools";
    public static final String MODVERSION = "0.0.1";

    @SidedProxy(clientSide = "us.kosdt.mysticalmultitools.proxy.ClientProxy", serverSide = "us.kosdt.mysticalmultitools.proxy.ServerProxy")
    public static CommonProxy proxy;

    private static int modGuiIndex = 0;
    public static final int GUI_ADAPTITE_BAG = modGuiIndex++;

    public static CreativeTabs creativeTab = new CreativeTabs("mysticalmultitools") {
        @Override
        public ItemStack getTabIconItem() { return new ItemStack(ModBlocks.blockAdaptite); }
    };

    @Mod.Instance
    public static MysticalMultitools instance;

    public static Logger logger;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        proxy.preInit(event);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        proxy.init(event);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        proxy.postInit(event);
    }

}
