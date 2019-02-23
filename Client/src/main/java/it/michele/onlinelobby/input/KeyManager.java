package it.michele.onlinelobby.input;

import it.michele.netty.packets.client.CPacketMove;
import it.michele.onlinelobby.ClientHandler;
import it.michele.onlinelobby.Game;
import it.michele.onlinelobby.Main;
import it.michele.onlinelobby.entities.Player;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

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
public class KeyManager implements KeyListener {

    public boolean keyDown[];
    private Player player;

    public KeyManager(){
        keyDown = new boolean[] {false, false, false, false};
        player = Game.players.get(Main.ID);
    }

    public void keyPressed(KeyEvent event){
        int key = event.getKeyCode();

        if(key == KeyEvent.VK_W) {
            player.setVelY(-5); keyDown[0] = true;
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, player.getX(), player.getY(), 1, -5));
        }
        if(key == KeyEvent.VK_S) {
            player.setVelY(5); keyDown[1] = true;
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, player.getX(), player.getY(), 1, 5));
        }
        if(key == KeyEvent.VK_D) {
            player.setVelX(5); keyDown[2] = true;
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, player.getX(), player.getY(), 5, 1));
        }
        if(key == KeyEvent.VK_A) {
            player.setVelX(-5); keyDown[3] = true;
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, player.getX(), player.getY(), -5, 1));
        }
    }

    public void keyReleased(KeyEvent event){
        int key = event.getKeyCode();

        if(key == KeyEvent.VK_W) keyDown[0] = false;
        if(key == KeyEvent.VK_S) keyDown[1] = false;
        if(key == KeyEvent.VK_D) keyDown[2] = false;
        if(key == KeyEvent.VK_A) keyDown[3] = false;

        //vertical movement
        if(!keyDown[0] && !keyDown[1]) {
            player.setVelY(0);
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, player.getX(), player.getY(), 1, 0));
        }

        //horizontal movement
        if(!keyDown[2] && !keyDown[3]) {
            player.setVelX(0);
            ClientHandler.ctx.writeAndFlush(new CPacketMove(Main.ID, player.getX(), player.getY(), 0, 1));
        }
    }

    public void keyTyped(KeyEvent event){

    }
}
