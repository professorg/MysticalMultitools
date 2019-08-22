package us.kosdt.mysticalmultitools.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.kosdt.mysticalmultitools.MysticalMultitools;

public class ItemAdaptite extends Item {

    public static final ResourceLocation ADAPTITE = new ResourceLocation(MysticalMultitools.MODID, "adaptite");

    public ItemAdaptite() {
        setRegistryName(ADAPTITE);
        setUnlocalizedName(MysticalMultitools.MODID + ".adaptite");
        setCreativeTab(MysticalMultitools.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}

