package us.kosdt.mysticalmultitools.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.items.ModItems;

import java.util.Random;

public class BlockAdaptiteOre extends Block {

    public static final ResourceLocation ADAPTITE_ORE = new ResourceLocation(MysticalMultitools.MODID, "adaptite_ore");

    public BlockAdaptiteOre() {
        super(Material.IRON);
        setRegistryName(ADAPTITE_ORE);
        setUnlocalizedName(MysticalMultitools.MODID + ".adaptite_ore");
        setHarvestLevel("pickaxe", 3);
        setHardness(4.5f);
        setResistance(15f);
        setCreativeTab(MysticalMultitools.creativeTab);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return ModItems.itemAdaptite;
    }

    @Override
    public int quantityDropped(Random rand) {
        return 1;
    }

    @Override
    public int quantityDroppedWithBonus(int fortune, Random rand) {
        // Copied from BlockOre
        int i = rand.nextInt(fortune + 2) - 1;
        if (i < 0) {
            i = 0;
        }
        return this.quantityDropped(rand) * (i + 1);
    }

}
