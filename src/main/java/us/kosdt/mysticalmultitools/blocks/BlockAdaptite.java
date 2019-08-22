package us.kosdt.mysticalmultitools.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import us.kosdt.mysticalmultitools.MysticalMultitools;

import java.util.Random;

public class BlockAdaptite extends Block {

    public static final ResourceLocation ADAPTITE_BLOCK = new ResourceLocation(MysticalMultitools.MODID, "adaptite_block");

    public BlockAdaptite() {
        super(Material.IRON);
        setRegistryName(ADAPTITE_BLOCK);
        setUnlocalizedName(MysticalMultitools.MODID + ".adaptite_block");
        setHarvestLevel("pickaxe", 2);
        setHardness(4f);
        setResistance(30f);
        setCreativeTab(MysticalMultitools.creativeTab);
        setTickRandomly(true);
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(this), 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }

    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
        // Make sure Adaptite block is fully encased: Not bordering air
        for (EnumFacing face : EnumFacing.values()) {
            BlockPos adjPos = pos.offset(face);
            if (worldIn.isAirBlock(adjPos)) {
                return;
            }
        }
        EnumFacing spreadFace = EnumFacing.random(rand);
        BlockPos spreadBlockPos = pos.offset(spreadFace);
        // Make sure adjacent block is fully encased
        for (EnumFacing face : EnumFacing.values()) {
            BlockPos adjPos = spreadBlockPos.offset(face);
            if (worldIn.isAirBlock(adjPos)) {
                return;
            }
        }
        Block spreadBlock = worldIn.getBlockState(spreadBlockPos).getBlock();
        if (spreadBlock == Blocks.IRON_BLOCK) {
            worldIn.setBlockState(spreadBlockPos, ModBlocks.blockAdaptite.getDefaultState());
        }
        if (spreadBlock == Blocks.GOLD_BLOCK) {
            worldIn.setBlockState(spreadBlockPos, ModBlocks.blockAdaptite.getDefaultState());
        }
        if (spreadBlock == Blocks.DIAMOND_BLOCK) {
            worldIn.setBlockState(spreadBlockPos, ModBlocks.blockAdaptite.getDefaultState());
        }
    }
}

