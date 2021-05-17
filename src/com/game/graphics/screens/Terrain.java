package com.game.graphics.screens;

import com.game.graphics.mob.*;
import com.game.graphics.*;
import com.game.world.*;

import java.awt.*;
import java.util.*;

public class Terrain{

    private static long lastTime = System.nanoTime();
    private static boolean spritesLocked= false;
    public SlotTree[] treeSlots;
    private final ArrayList<Sprite> sprites = new ArrayList<>();

    public static Terrain currentTerrain = new Terrain(1, 1, Renderer.gameSize.width-1, new SlotTree[]{new SlotTree(16,62,22,11),new SlotTree(44,62,22,10) ,new SlotTree(72,62,22,10)});

    private final Enemy enemy = new Enemy(-10,50,false);

    public Terrain(int i, int colLeft, int colRight, SlotTree[] treeSlots){
        this.treeSlots = treeSlots;
        sprites.add(new StaticSprite(0,0,false, "skybox"));
        sprites.add(new StaticSprite(0,49,false, "floor"+i));
        sprites.add(enemy);
        sprites.add(new StaticSprite(32,76,false, "coin"));
        sprites.add(new StaticSprite(20,85,false, "keys"));
        sprites.add(new StaticSprite(20,76,false, "chest", 8, 8));
    }

    public static void update(){
        if (currentTerrain==null){return;}
        float deltaTime = (System.nanoTime() - lastTime)/ 1000000000.0f;
        lastTime=System.nanoTime();

        boolean ran = false;
        while(!ran){
            try{
                for (Sprite sprite : currentTerrain.sprites){ sprite.update(deltaTime); }
                ran=true;
            }catch (ConcurrentModificationException e){System.out.println("Concurrent!");}
        }
    }

    public static void render (Graphics g){
        if (currentTerrain==null){ return; }

        lockSprites("RENDER every Sprite & debug slot tree");
        boolean ran = false;
        while(!ran){
            try{
                for (Sprite sprite : currentTerrain.sprites){ sprite.render(g); }
                ran=true;
            }catch (ConcurrentModificationException e){System.out.println("Concurrent!");}
        }
        unlockSprites("RENDER every Sprite & debug slot tree");

    }
    public Enemy getEnemy(){ return enemy; }
    public void removeSprite(Sprite sprite){ while(true){if(!spritesLocked){sprites.remove(sprite); break;}} }
    public void addSprite(Sprite sprite){ while(true){if(!spritesLocked){sprites.add(sprite); break;}} }
    public static void lockSprites(String e){spritesLocked=true; }
    public static void unlockSprites(String e){spritesLocked=false; }

}

