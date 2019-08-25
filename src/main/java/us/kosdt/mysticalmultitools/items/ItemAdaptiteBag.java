package us.kosdt.mysticalmultitools.items;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.gui.AdaptiteBagGui;

public class ItemAdaptiteBag extends Item {

    public static final ResourceLocation ADAPTITE_BAG = new ResourceLocation(MysticalMultitools.MODID, "adaptite_bag");

    public ItemAdaptiteBag() {
        setRegistryName(ADAPTITE_BAG);
        setUnlocalizedName(MysticalMultitools.MODID + ".adaptite_bag");
        setMaxStackSize(1);
        setCreativeTab(MysticalMultitools.creativeTab);
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 1;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        if (!world.isRemote) {
            if (!player.isSneaking()) {
                player.openGui(MysticalMultitools.instance, MysticalMultitools.GUI_ADAPTITE_BAG, world, 0, 0, 0);
            }
        }
        return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(hand));
    }

    @SideOnly(Side.CLIENT)
    public void initModel() {
        ModelLoader.setCustomModelResourceLocation(this, 0, new ModelResourceLocation(getRegistryName(), "inventory"));
    }
}
