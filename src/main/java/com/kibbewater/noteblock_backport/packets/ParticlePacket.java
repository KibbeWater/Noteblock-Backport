package com.kibbewater.noteblock_backport.packets;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.util.EnumParticleTypes;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;

import static com.kibbewater.noteblock_backport.NoteblockBackport.logger;

public class ParticlePacket implements IMessage {
    private boolean messageValid;
    private double x, y, z;
    private double pitch;

    public ParticlePacket()
    {
        this.messageValid = false;
    }

    public ParticlePacket(double x, double y, double z, double pitch)
    {
        this.x = x;
        this.y = y;
        this.z = z;

        this.pitch = pitch;

        this.messageValid = true;
    }

    @Override
    public void fromBytes(ByteBuf buf)
    {
        try
        {
            this.x = buf.readDouble();
            this.y = buf.readDouble();
            this.z = buf.readDouble();
        }
        catch(IndexOutOfBoundsException ioe)
        {
            return;
        }
    }

    @Override
    public void toBytes(ByteBuf buf) {
        if(!this.messageValid)
            return;

        buf.writeDouble(x);
        buf.writeDouble(y);
        buf.writeDouble(z);
        buf.writeDouble(pitch);
    }

    public static class Handler implements IMessageHandler<ParticlePacket, IMessage>
    {
        @Override
        public IMessage onMessage(ParticlePacket message, MessageContext ctx) {
            if(!message.messageValid && ctx.side != Side.CLIENT)
            {
                return null;
            }

            Minecraft minecraft = Minecraft.getMinecraft();
            final WorldClient worldClient = minecraft.world;

            minecraft.addScheduledTask(() -> processMessage(message, worldClient));

            return null;
        }

        void processMessage(ParticlePacket message, WorldClient worldClient)
        {
            worldClient.spawnParticle(
                    EnumParticleTypes.NOTE, 
                    message.x + 0.5D, 
                    message.y + 1.2D,
                    message.z + 0.5D,
                    message.pitch / 24.0D,
                    0.0D, 
                    0.0D
            );
        }
    }
}


