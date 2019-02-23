package it.michele.netty.packets;

import it.michele.netty.packets.client.*;
import it.michele.netty.packets.server.*;

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
public enum PacketEnum {
    C_PACKET_HANDSHAKE(0x1, CPacketHandshake.class),
    C_PACKET_LOGIN(0x2, CPacketLogin.class),
    C_PACKET_LOGOUT(0x3, CPacketLogout.class),
    C_PACKET_TITLE(0x4, CPacketTitle.class),
    C_PACKET_SUBTITLE(0x5, CPacketSubTitle.class),
    C_PACKET_RESTART(0x6, CPacketRestart.class),
    C_PACKET_MOVE(0x7, CPacketMove.class),
    C_PACKET_NEW_PLAYER(0x8, CPacketNewPlayer.class),

    S_PACKET_HANDSHAKE(0x100, SPacketHandshake.class),
    S_PACKET_LOGIN(0x101, SPacketLogin.class),
    S_PACKET_LOGOUT(0x102, SPacketLogout.class),
    S_PACKET_TITLE(0x103, SPacketTitle.class),
    S_PACKET_SUBTITLE(0x104, SPacketSubTitle.class),
    S_PACKET_RESTART(0x105, SPacketRestart.class),
    S_PACKET_MOVE(0x106, SPacketMove.class),
    S_PACKET_NEW_PLAYER(0x107, SPacketNewPlayer.class),
    ;

    private int id;
    private Class clazz;

    PacketEnum(int id, Class clazz){
        this.id = id;
        this.clazz = clazz;
    }

    public int getId() {
        return id;
    }

    public Class getClazz() {
        return clazz;
    }

    public static Class getClazz(int id){
        for(PacketEnum packet : values()){
            if(packet.getId() == id){
                return packet.getClazz();
            }
        }

        return null;
    }
}
