package com.kibbewater.noteblock_backport;

import com.kibbewater.noteblock_backport.packets.ParticlePacket;
import com.kibbewater.noteblock_backport.packets.server.ParticleServerHandler;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.event.world.NoteBlockEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.Logger;

import static com.kibbewater.noteblock_backport.CommonProxy.*;

@Mod(modid = NoteblockBackport.MODID, name = NoteblockBackport.NAME, version = NoteblockBackport.VERSION)
public class NoteblockBackport
{
    public static final String MODID = "noteblock_backport";
    public static final String NAME = "Noteblock Backport";
    public static final String VERSION = "1.0";

    public static SimpleNetworkWrapper simpleNetworkWrapper;
    private int id = 1;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        simpleNetworkWrapper = NetworkRegistry.INSTANCE.newSimpleChannel("NOTEBLOCK_BACKPORT");
        simpleNetworkWrapper.registerMessage(ParticleServerHandler.class, ParticlePacket.class, id++, Side.SERVER);
        simpleNetworkWrapper.registerMessage(ParticlePacket.Handler.class, ParticlePacket.class, id++, Side.CLIENT);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        CustomInstruments.registerInstrument(Blocks.HAY_BLOCK, BANJO_SOUND);
        CustomInstruments.registerInstrument(Blocks.EMERALD_BLOCK, BIT_SOUND);
        CustomInstruments.registerInstrument(Blocks.SOUL_SAND, COW_BELL_SOUND);
        CustomInstruments.registerInstrument(Blocks.PUMPKIN, DIDGERIDOO_SOUND);
        CustomInstruments.registerInstrument(Blocks.IRON_BLOCK, IRON_XYLOPHONE_SOUND);
        CustomInstruments.registerInstrument(Blocks.GLOWSTONE, PLING_SOUND);
    }
}
