package us.kosdt.mysticalmultitools.proxy;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.oredict.OreDictionary;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.blocks.BlockAdaptite;
import us.kosdt.mysticalmultitools.blocks.BlockAdaptiteOre;
import us.kosdt.mysticalmultitools.blocks.ModBlocks;
import us.kosdt.mysticalmultitools.blocks.TileBlockAdaptite;
import us.kosdt.mysticalmultitools.gui.AdaptiteBagConstants;
import us.kosdt.mysticalmultitools.gui.AdaptiteBagContainer;
import us.kosdt.mysticalmultitools.gui.AdaptiteBagGui;
import us.kosdt.mysticalmultitools.gui.AdaptiteBagInventory;
import us.kosdt.mysticalmultitools.items.ItemAdaptiteBag;
import us.kosdt.mysticalmultitools.items.ItemBlockAdaptite;
import us.kosdt.mysticalmultitools.items.ModItems;
import us.kosdt.mysticalmultitools.network.BagSelectedSlotMessage;
import us.kosdt.mysticalmultitools.network.BagSelectedSlotMessageHandler;
import us.kosdt.mysticalmultitools.network.MysticalMultitoolsPacketHandler;
import us.kosdt.mysticalmultitools.worldgen.WorldTickHandler;
import us.kosdt.mysticalmultitools.items.ItemAdaptite;
import us.kosdt.mysticalmultitools.worldgen.OreGenerator;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber
public class CommonProxy implements IGuiHandler {

    private static int mid = 0;

    public void preInit(FMLPreInitializationEvent e) {
        GameRegistry.registerWorldGenerator(OreGenerator.instance, 0);
        //MinecraftForge.EVENT_BUS.register(OreGenerator.instance);
        GameRegistry.registerTileEntity(TileBlockAdaptite.class, new ResourceLocation(MysticalMultitools.MODID, "tile_entity_adaptite_block"));
        NetworkRegistry.INSTANCE.registerGuiHandler(MysticalMultitools.instance, new CommonProxy());
        MysticalMultitoolsPacketHandler.INSTANCE.registerMessage(BagSelectedSlotMessageHandler.class, BagSelectedSlotMessage.class, mid++, Side.CLIENT);
        MysticalMultitoolsPacketHandler.INSTANCE.registerMessage(BagSelectedSlotMessageHandler.class, BagSelectedSlotMessage.class, mid++, Side.SERVER);
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
        event.getRegistry().register(new ItemBlockAdaptite(ModBlocks.blockAdaptite).setRegistryName(ModBlocks.blockAdaptite.getRegistryName()));
        event.getRegistry().register(new ItemBlock(ModBlocks.blockAdaptiteOre).setRegistryName(ModBlocks.blockAdaptiteOre.getRegistryName()));
        event.getRegistry().register(new ItemAdaptite());
        event.getRegistry().register(new ItemAdaptiteBag());
    }

    @Nullable
    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MysticalMultitools.GUI_ADAPTITE_BAG) {
            ItemStack itemStack;
            if (player.getHeldItemMainhand().getItem() instanceof ItemAdaptiteBag) {
                itemStack = player.getHeldItemMainhand();
            } else if (player.getHeldItemOffhand().getItem() instanceof  ItemAdaptiteBag) {
                itemStack = player.getHeldItemOffhand();
            } else {
                return null;
            }
            return new AdaptiteBagContainer(player, player.inventory, new AdaptiteBagInventory(itemStack));
        }
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
        if (ID == MysticalMultitools.GUI_ADAPTITE_BAG) {
            ItemStack itemStack;
            if (player.getHeldItemMainhand().getItem() instanceof ItemAdaptiteBag) {
                itemStack = player.getHeldItemMainhand();
            } else if (player.getHeldItemOffhand().getItem() instanceof  ItemAdaptiteBag) {
                itemStack = player.getHeldItemOffhand();
            } else {
                return null;
            }
            return new AdaptiteBagGui(player, player.inventory, new AdaptiteBagInventory(itemStack));
        }
        return null;
    }
}
