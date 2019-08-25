package us.kosdt.mysticalmultitools.network;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import us.kosdt.mysticalmultitools.gui.AdaptiteBagContainer;

public class BagSelectedSlotMessageHandler implements IMessageHandler<BagSelectedSlotMessage, IMessage> {

    @Override
    public IMessage onMessage(BagSelectedSlotMessage message, MessageContext ctx) {
        EntityPlayerMP serverPlayer = ctx.getServerHandler().player;
        int amount = message.selected;
        serverPlayer.getServerWorld().addScheduledTask(() -> {
            if (serverPlayer.openContainer instanceof AdaptiteBagContainer)
                ((AdaptiteBagContainer) serverPlayer.openContainer).setSelectedSlot(amount);
        });
        return null;
    }
}
