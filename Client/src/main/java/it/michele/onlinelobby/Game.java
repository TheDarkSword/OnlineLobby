package it.michele.onlinelobby;

import it.michele.onlinelobby.display.Display;
import it.michele.onlinelobby.entities.Player;
import it.michele.onlinelobby.input.KeyManager;
import it.michele.onlinelobby.states.GameState;
import it.michele.onlinelobby.states.State;

import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.HashMap;
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
public class Game implements Runnable {

    private Display display;

    private int width, height;
    public String title;

    private boolean running = false;
    private Thread thread;

    private BufferStrategy bufferStrategy;
    private Graphics graphics;

    private State gameState;

    private KeyManager keyManager;

    public static HashMap<UUID, Player> players = new HashMap<>();

    public Game(String title, int width, int height){
        this.title = title;
        this.width = width;
        this.height = height;

        players.put(Main.ID, new Player(width/2 -16, height/2 -16));
        keyManager = new KeyManager();
    }

    private void init(){
        display = new Display(title, width, height);
        display.getFrame().addKeyListener(keyManager);

        gameState = new GameState(this);
        State.setState(gameState);
    }

    private void tick(){

        if(State.getState() != null){
            State.getState().tick();
        }
    }

    public void render(){
        bufferStrategy = display.getCanvas().getBufferStrategy();
        if(bufferStrategy == null){
            display.getCanvas().createBufferStrategy(3);
            return;
        }

        graphics = bufferStrategy.getDrawGraphics();

        //Clear Screen

        graphics.clearRect(0, 0, width, height);

        //Start Drawing

        graphics.setColor(Color.BLACK);
        graphics.fillRect(0, 0, width, height);

        if(State.getState() != null){
            State.getState().render(graphics);
        }

        //End Drawing

        bufferStrategy.show();
        graphics.dispose();
    }

    @Override
    public void run(){
        init();

        int ticks = 60;

        double tickPerSecond = 1000000000 / ticks;
        double delta = 0;
        long now;
        long lastTime = System.nanoTime();

        while(running){
            now = System.nanoTime();
            delta += (now - lastTime) / tickPerSecond;
            lastTime = now;

            if(delta >= 1){
                tick();
                delta--;
            }

            render();
        }

        stop();
    }

    public KeyManager getKeyManager() {
        return keyManager;
    }

    public synchronized void start(){
        if(running) return;
        thread = new Thread(this);
        running = true;
        thread.start();
    }

    public synchronized void stop(){
        if(!running) return;
        try{
            thread.join();
            running = false;
        } catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    public static int clamp(int pos, int min, int max) {
        if(pos >= max) return max;
        else if (pos <= min) return min;
        else return pos;
    }
}
