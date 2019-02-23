package it.michele.onlinelobby.entities;

import it.michele.netty.packets.client.CPacketMove;
import it.michele.onlinelobby.ClientHandler;
import it.michele.onlinelobby.Game;
import it.michele.onlinelobby.Main;

import java.awt.*;
import java.util.Random;

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
public class Player extends Creature {

    private Color color;
    private int oldX, oldY;

    public Player(int x, int y){
        super(x, y);
        oldX = x;
        oldY = y;
        int random = new Random().nextInt(7);

        if(random == 0) color = Color.WHITE;
        if(random == 1) color = Color.RED;
        if(random == 2) color = Color.GREEN;
        if(random == 3) color = Color.BLUE;
        if(random == 4) color = Color.PINK;
        if(random == 5) color = Color.CYAN;
        if(random == 6) color = Color.MAGENTA;
    }

    @Override
    public void tick() {
        x += velX;
        y += velY;

        x = Game.clamp(x, 0, 608);
        y = Game.clamp(y, 0, 447);

        if(oldX != x || oldY != y){
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, x, y, 1, 1));
        }

        oldX = x;
        oldY = y;
    }

    @Override
    public void render(Graphics graphics){
        graphics.setColor(color);
        graphics.fillRect(x, y, 32, 32);
    }
}
