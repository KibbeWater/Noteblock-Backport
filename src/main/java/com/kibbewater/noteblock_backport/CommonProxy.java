package com.kibbewater.noteblock_backport;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.util.ArrayList;

import static com.kibbewater.noteblock_backport.NoteblockBackport.MODID;
import static com.kibbewater.noteblock_backport.NoteblockBackport.logger;

@Mod.EventBusSubscriber
public class CommonProxy {
    static ArrayList<SoundEvent> allSounds = new ArrayList<SoundEvent>();

    public static final SoundEvent BANJO_SOUND = registerSound("banjo");
    public static final SoundEvent BIT_SOUND = registerSound("bit");
    public static final SoundEvent COW_BELL_SOUND = registerSound("cow_bell");
    public static final SoundEvent DIDGERIDOO_SOUND = registerSound("didgeridoo");
    public static final SoundEvent IRON_XYLOPHONE_SOUND = registerSound("iron_xylophone");
    public static final SoundEvent PLING_SOUND = registerSound("pling");

    @SubscribeEvent
    public static void onSoundsRegistry(final RegistryEvent.Register<SoundEvent> event) {
        event.getRegistry().registerAll
                (
                        getAllSounds()
                );
        logger.info("Sounds Registered");
    }

    public static SoundEvent[] getAllSounds()
    {
        return allSounds.toArray(new SoundEvent[allSounds.size()]);
    }

    private static SoundEvent registerSound(String name)
    {
        logger.log(Level.INFO, "Registering sound {}", name);
        ResourceLocation soundLocation = new ResourceLocation(MODID, name);
        SoundEvent ret = new SoundEvent(soundLocation).setRegistryName(name);
        allSounds.add(ret);
        return ret;
    }
}
