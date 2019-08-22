package us.kosdt.mysticalmultitools.proxy;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import us.kosdt.mysticalmultitools.blocks.BlockAdaptite;
import us.kosdt.mysticalmultitools.blocks.BlockAdaptiteOre;
import us.kosdt.mysticalmultitools.blocks.ModBlocks;
import us.kosdt.mysticalmultitools.worldgen.WorldTickHandler;
import us.kosdt.mysticalmultitools.items.ItemAdaptite;
import us.kosdt.mysticalmultitools.worldgen.OreGenerator;

@Mod.EventBusSubscriber
public class CommonProxy {

    public void preInit(FMLPreInitializationEvent e) {
        GameRegistry.registerWorldGenerator(OreGenerator.instance, 0);
        //MinecraftForge.EVENT_BUS.register(OreGenerator.instance);
    }

    public void init(FMLInitializationEvent e) {
        MinecraftForge.EVENT_BUS.register(WorldTickHandler.instance);
    }

    public void postInit(FMLPostInitializationEvent e) {
        OreDictionary.registerOre("oreAdaptite", ModBlocks.blockAdaptiteOre);
    }

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().register(new BlockAdaptite());
        event.getRegistry().register(new BlockAdaptiteOre());
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new ItemBlock(ModBlocks.blockAdaptite).setRegistryName(ModBlocks.blockAdaptite.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockAdaptiteOre).setRegistryName(ModBlocks.blockAdaptiteOre.getRegistryName()));
        event.getRegistry().register(new ItemAdaptite());
    }

}
