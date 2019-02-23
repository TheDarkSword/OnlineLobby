package it.michele.netty.packets.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import it.michele.netty.NetworkHandler;
import it.michele.netty.packets.Packet;
import it.michele.netty.packets.PacketEnum;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Copyright © 2019 by Michele Giacalone
 * This file is part of OnlineLobby.
 * OnlineLobby is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
 * <p>
 * Redistribution and use in source and binary forms, with or without modification, are permitted provided that the following conditions are met:
 * 1. Redistributions of source code must retain the above copyright notice, this list of conditions and the following disclaimer.
 * 2. Redistributions in binary form must reproduce the above copyright notice, this list of conditions and the following
 * disclaimer in the documentation and/or other materials provided with the distribution.
 * 3. Neither the name of the copyright holder nor the names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 * <p>
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING,
 * BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED.
 * IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY,
 * OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA,
 * OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF
 * THE POSSIBILITY OF SUCH DAMAGE.
 */
public class SPacketNewPlayer implements Packet {
    private PacketEnum type = PacketEnum.S_PACKET_NEW_PLAYER;

    private UUID uuid;
    private int x;
    private int y;

    public SPacketNewPlayer(){

    }

    public SPacketNewPlayer(UUID uuid, int x, int y){
        this.uuid = uuid;
        this.x = x;
        this.y = y;
    }

    public UUID getUuid(){
        return uuid;
    }

    public int getX(){
        return x;
    }

    public int getY(){
        return y;
    }

    @Override
    public void processPacket(ChannelHandlerContext ctx, NetworkHandler handler){
        handler.processNewPlayer(this, ctx);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out){
        byte[] uuid = this.uuid.toString().getBytes();

        out.writeInt(type.getId());
        out.writeInt(uuid.length);
        out.writeBytes(uuid);
        out.writeInt(x);
        out.writeInt(y);
    }

    @Override
    public Packet decode(ByteBuf buf){
        Charset charset = Charset.forName("UTF-8");
        int uuidLen = buf.readInt();
        uuid = UUID.fromString(buf.readCharSequence(uuidLen, charset).toString());
        x = buf.readInt();
        y = buf.readInt();

        return this;
    }
}
