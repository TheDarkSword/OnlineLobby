package it.michele.netty.packets.client;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import it.michele.netty.NetworkHandler;
import it.michele.netty.packets.Packet;
import it.michele.netty.packets.PacketEnum;

import java.nio.charset.Charset;
import java.util.UUID;

/**
 * Copyright © 2019 by Michele Giacalone
 * This file is part of NettyLib.
 * NettyLib is under "The 3-Clause BSD License", you can find a copy <a href="https://opensource.org/licenses/BSD-3-Clause">here</a>.
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
public class CPacketLogin implements Packet {
    private PacketEnum type = PacketEnum.C_PACKET_LOGIN;

    private UUID uuid;
    private String name;
    private int protocol;

    public CPacketLogin(){

    }

    public CPacketLogin(UUID uuid, String name, int protocol){
        this.uuid = uuid;
        this.name = name;
        this.protocol = protocol;
    }

    public UUID getUuid(){
        return uuid;
    }

    public String getName() {
        return name;
    }

    public int getProtocol() {
        return protocol;
    }

    @Override
    public void processPacket(ChannelHandlerContext ctx, NetworkHandler handler){
        handler.processLogin(this, ctx);
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Packet packet, ByteBuf out){
        byte[] name = this.name.getBytes();
        byte[] uuid = this.uuid.toString().getBytes();

        out.writeInt(type.getId());
        out.writeInt(uuid.length);
        out.writeBytes(uuid);
        out.writeInt(name.length);
        out.writeBytes(name);
        out.writeInt(protocol);
    }

    @Override
    public Packet decode(ByteBuf buf){
        Charset charset = Charset.forName("UTF-8");
        int uuidLen = buf.readInt();
        uuid = UUID.fromString(buf.readCharSequence(uuidLen, charset).toString());
        int nameLen = buf.readInt();
        name = buf.readCharSequence(nameLen, charset).toString();
        protocol = buf.readInt();

        return this;
    }
}
