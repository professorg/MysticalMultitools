package us.kosdt.mysticalmultitools.proxy;

import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import us.kosdt.mysticalmultitools.blocks.ModBlocks;
import us.kosdt.mysticalmultitools.items.ModItems;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientProxy extends CommonProxy {
    
    @Override
    public void preInit(FMLPreInitializationEvent e) {
        super.preInit(e);
    }
    
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        ModBlocks.initModels();
        ModItems.initModels();
    }
}
