package us.kosdt.mysticalmultitools.network;

import io.netty.buffer.ByteBuf;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;

public class BagSelectedSlotMessage implements IMessage {

    public BagSelectedSlotMessage() {

    }

    public int selected;

    public BagSelectedSlotMessage(int selected) {
        this.selected = selected;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
        selected = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
        buf.writeInt(selected);
    }
}
