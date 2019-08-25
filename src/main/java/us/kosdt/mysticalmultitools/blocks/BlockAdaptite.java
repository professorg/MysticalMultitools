package us.kosdt.mysticalmultitools.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.config.AdaptiteStatConfig;

import javax.annotation.Nullable;
import java.util.ArrayList;
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
        if (average(worldIn, pos, spreadBlockPos, spreadBlock, rand) && AdaptiteStatConfig.VERBOSE) {
            // Block turned to adaptite
            TileBlockAdaptite thisTile = (TileBlockAdaptite) worldIn.getTileEntity(pos);
            TileBlockAdaptite otherTile = (TileBlockAdaptite) worldIn.getTileEntity(spreadBlockPos);
            MysticalMultitools.logger.log(Level.DEBUG, String.format("This block at %s updated to have %d durability and %d speed stats.", pos.toString(), thisTile.durabilityStat, thisTile.speedStat));
            MysticalMultitools.logger.log(Level.DEBUG, String.format("Other block at %s updated to have %d durability and %d speed stats.", spreadBlockPos.toString(), otherTile.durabilityStat, otherTile.speedStat));
        }
    }

    public boolean average(World worldIn, BlockPos thisPos, BlockPos otherPos, Block other, Random rand) {
        TileBlockAdaptite thisTile = (TileBlockAdaptite) worldIn.getTileEntity(thisPos);
        int oldDurability = thisTile.durabilityStat;
        int oldSpeed = thisTile.speedStat;
        TileBlockAdaptite otherTile = null;
        int otherDurability;
        int otherSpeed;
        if (other == ModBlocks.blockAdaptite) {
            otherTile = (TileBlockAdaptite) worldIn.getTileEntity(otherPos);
            otherDurability = otherTile.durabilityStat;
            otherSpeed = otherTile.speedStat;
        } else if (other == Blocks.IRON_BLOCK) {
            otherDurability = AdaptiteStatConfig.IRON_INITIAL_DURABILITY;
            otherSpeed = AdaptiteStatConfig.IRON_INITIAL_SPEED;
        } else if (other == Blocks.GOLD_BLOCK) {
            otherDurability = AdaptiteStatConfig.GOLD_INITIAL_DURABILITY;
            otherSpeed = AdaptiteStatConfig.GOLD_INITIAL_SPEED;
        } else if (other == Blocks.DIAMOND_BLOCK) {
            otherDurability = AdaptiteStatConfig.DIAMOND_INITIAL_DURABILITY;
            otherSpeed = AdaptiteStatConfig.DIAMOND_INITIAL_SPEED;
        } else {
            return false;
        }
        if (otherTile == null) {
            worldIn.setBlockState(otherPos, ModBlocks.blockAdaptite.getDefaultState());
            otherTile = (TileBlockAdaptite) worldIn.getTileEntity(otherPos);
        }
        int avgDurability = (oldDurability + otherDurability) / 2;
        int avgSpeed = (oldSpeed + otherSpeed) / 2;
        thisTile.durabilityStat = avgDurability;
        thisTile.speedStat = avgSpeed;
        otherTile.durabilityStat = avgDurability;
        otherTile.speedStat = avgSpeed;
        thisTile.markDirty();
        otherTile.markDirty();
        return true;
    }

    @Override
    public boolean hasTileEntity(IBlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(World world, IBlockState state) {
        return new TileBlockAdaptite();
    }

    @Override
    public void getDrops(NonNullList<ItemStack> drops, IBlockAccess world, BlockPos pos, IBlockState state, int fortune) {
        ItemStack blockItem = new ItemStack(this);
        NBTTagCompound nbt = new NBTTagCompound();
        TileEntity te = world.getTileEntity(pos);
        te.writeToNBT(nbt);
        blockItem.setTagCompound(nbt);
        drops.add(blockItem);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest)
    {
        if (willHarvest) return true; //If it will harvest, delay deletion of the block until after getDrops
        return super.removedByPlayer(state, world, pos, player, willHarvest);
    }

    /**
     * Spawns the block's drops in the world. By the time this is called the Block has possibly been set to air via
     * Block.removedByPlayer
     */
    @Override
    public void harvestBlock(World world, EntityPlayer player, BlockPos pos, IBlockState state, @Nullable TileEntity te, ItemStack tool)
    {
        super.harvestBlock(world, player, pos, state, te, tool);
        world.setBlockToAir(pos);
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack)
    {
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
        TileEntity te = worldIn.getTileEntity(pos);
        if (te == null || !(te instanceof TileBlockAdaptite))
            return;
        TileBlockAdaptite tile = (TileBlockAdaptite) te;
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            return;
        tile.readFromNBT(nbt);
    }
}

