package it.michele.onlinelobby;

import io.netty.channel.ChannelHandlerContext;
import it.michele.netty.NetworkHandler;
import it.michele.netty.packets.Packet;
import it.michele.netty.packets.client.CPacketHandshake;
import it.michele.netty.packets.client.CPacketLogin;
import it.michele.netty.packets.server.*;
import it.michele.onlinelobby.entities.Player;

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
public class ClientHandler implements NetworkHandler {
    public static ChannelHandlerContext ctx;

    @Override
    public void channelActive(ChannelHandlerContext ctx){
        ctx.writeAndFlush(new CPacketHandshake(Main.PROTOCOL_VERSION));
    }

    @Override
    public void processHandshake(Packet packet, ChannelHandlerContext ctx){
        SPacketHandshake in = (SPacketHandshake) packet;

        if(!in.isConnectionAllowed()){
            System.err.println("Errore, Versione Obsoleta");
            return;
        }

        ctx.writeAndFlush(new CPacketLogin(Main.ID, "TheDarkSword", Main.PROTOCOL_VERSION));
    }

    @Override
    public void processLogin(Packet packet, ChannelHandlerContext ctx){
        SPacketLogin in = (SPacketLogin) packet;

        if(!in.isLoginSuccessful()){
            System.err.println("Login Error, Server refused");
            return;
        }

        ClientHandler.ctx = ctx;

        System.out.println("Login successful!");
    }

    @Override
    public void processLogout(Packet packet, ChannelHandlerContext ctx){
        SPacketLogout in = (SPacketLogout) packet;

        if(in.isLogoutSuccessful()) System.exit(1);
    }

    @Override
    public void processTitle(Packet packet, ChannelHandlerContext ctx){
        SPacketTitle in = (SPacketTitle) packet;

    }

    @Override
    public void processSubTitle(Packet packet, ChannelHandlerContext ctx){
        SPacketSubTitle in = (SPacketSubTitle) packet;

    }

    @Override
    public void processRestart(Packet packet, ChannelHandlerContext ctx){
        SPacketRestart in = (SPacketRestart) packet;


    }

    @Override
    public void processMove(Packet packet, ChannelHandlerContext ctx){
        SPacketMove in = (SPacketMove) packet;

        Player player = Game.players.get(in.getUuid());

        if(in.getVelX() != 1){
            player.setVelX(in.getVelX());
        }
        if(in.getVelY() != 1){
            player.setVelY(in.getVelY());
        }
    }

    @Override
    public void processNewPlayer(Packet packet, ChannelHandlerContext ctx){
        SPacketNewPlayer in = (SPacketNewPlayer) packet;

        Game.players.put(in.getUuid(), new Player(in.getX(), in.getY()));
    }
}
