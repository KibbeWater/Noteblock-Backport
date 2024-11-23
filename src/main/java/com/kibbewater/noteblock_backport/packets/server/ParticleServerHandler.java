package com.kibbewater.noteblock_backport.packets.server;

import com.kibbewater.noteblock_backport.packets.ParticlePacket;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class ParticleServerHandler implements IMessageHandler<ParticlePacket, IMessage>
{
    @Override
    public IMessage onMessage(ParticlePacket message, MessageContext ctx)
    {
        return null;
    }
}
