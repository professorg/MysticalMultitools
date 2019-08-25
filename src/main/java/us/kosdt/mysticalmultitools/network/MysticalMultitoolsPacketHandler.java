package us.kosdt.mysticalmultitools.network;

import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import us.kosdt.mysticalmultitools.MysticalMultitools;

public class MysticalMultitoolsPacketHandler {

    public static final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(MysticalMultitools.MODID);
}
