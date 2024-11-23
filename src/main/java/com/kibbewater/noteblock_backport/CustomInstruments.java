package com.kibbewater.noteblock_backport;

import com.kibbewater.noteblock_backport.packets.ParticlePacket;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.BlockNote;
import net.minecraft.client.Minecraft;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Level;

import java.util.Dictionary;
import java.util.Hashtable;

import static com.kibbewater.noteblock_backport.NoteblockBackport.MODID;
import static com.kibbewater.noteblock_backport.NoteblockBackport.logger;

@Mod.EventBusSubscriber
public class CustomInstruments {
    static Dictionary<Block, SoundEvent> instruments = new Hashtable<Block, SoundEvent>();

    public static void registerInstrument(Block block, SoundEvent sound) {
        instruments.put(block, sound);
    }

    @SubscribeEvent
    public static void onNotePlayed(final NoteBlockEvent.Play played) {
        BlockPos pos = played.getPos();
        BlockPos down = pos.down();
        World world = played.getWorld();

        int height = played.getVanillaNoteId();

        if (!world.isRemote) { // SERVER
            Block block = world.getBlockState(down).getBlock();

            SoundEvent instrument = instruments.get(block);
            if (instrument == null) return;

            float pitch = (float) Math.pow(2.0D, (double) (height - 12) / 12.0D);
            world.playSound(null, played.getPos(), instrument, SoundCategory.RECORDS, 3.0f, pitch);

            ParticlePacket particlePacket = new ParticlePacket(pos.getX(), pos.getY(), pos.getZ(), height);

            NetworkRegistry.TargetPoint target = new NetworkRegistry.TargetPoint(world.provider.getDimension(), pos.getX(), pos.getY(), pos.getZ(), 20.d);
            NoteblockBackport.simpleNetworkWrapper.sendToAllAround(particlePacket, target);

            played.setCanceled(true);
        }
    }
}
