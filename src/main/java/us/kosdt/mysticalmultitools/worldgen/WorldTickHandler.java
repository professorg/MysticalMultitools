package us.kosdt.mysticalmultitools.worldgen;

import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.ArrayDeque;
import java.util.Random;

public class WorldTickHandler {

    public static WorldTickHandler instance = new WorldTickHandler();

    public static TIntObjectHashMap<ArrayDeque<ChunkPos>> chunksToGen = new TIntObjectHashMap<>();

    @SubscribeEvent
    public void tickEnd(TickEvent.WorldTickEvent event) {
        if (event.side != Side.SERVER) {
            return;
        }

        if (event.phase == TickEvent.Phase.END) {

            World world = event.world;
            int dim = world.provider.getDimension();

            ArrayDeque<ChunkPos> chunks = chunksToGen.get(dim);

            if (chunks != null && !chunks.isEmpty()) {
                ChunkPos c = chunks.pollFirst();
                long worldSeed = world.getSeed();
                Random rand = new Random(worldSeed);
                long xSeed = rand.nextLong() >> 2 + 1L;
                long zSeed = rand.nextLong() >> 2 + 1L;
                rand.setSeed(xSeed * c.x + zSeed + c.z ^ worldSeed);
                OreGenerator.instance.generateWorld(rand, c.x, c.z, world, false);
                chunksToGen.put(dim, chunks);
            } else if (chunks != null) {
                chunksToGen.remove(dim);
            }
        }
    }
}
