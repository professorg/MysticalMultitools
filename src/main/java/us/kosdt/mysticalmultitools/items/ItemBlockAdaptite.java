package us.kosdt.mysticalmultitools.items;

import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.logging.log4j.Level;
import us.kosdt.mysticalmultitools.MysticalMultitools;
import us.kosdt.mysticalmultitools.blocks.NBTTags;

import javax.annotation.Nullable;
import java.util.List;

public class ItemBlockAdaptite extends ItemBlock {

    public ItemBlockAdaptite(Block block) {
        super(block);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        NBTTagCompound nbt = stack.getTagCompound();
        if (nbt == null)
            return;
        if (nbt.hasKey(NBTTags.DURABILITY.tagName)) {
            int durability = (int) NBTTags.getTag(nbt, NBTTags.DURABILITY);
            tooltip.add(String.format("Durability: %d.%02d", durability / 100, durability % 100));
        }
        if (nbt.hasKey(NBTTags.SPEED.tagName)) {
            int speed = (int) NBTTags.getTag(nbt, NBTTags.SPEED);
            tooltip.add(String.format("Speed: %d.%02d", speed / 100, speed % 100));
        }
    }

}
