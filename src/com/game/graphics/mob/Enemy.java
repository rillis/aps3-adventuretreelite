package com.game.graphics.mob;

import com.game.graphics.*;
import com.game.graphics.screens.*;
import com.game.world.*;

public class Enemy extends Sprite {
    public final static int IDLE = 0;
    public final static int GOING = 1;
    public final static int BREAKING = 2;
    public final static int RETURNING = 3;

    Animation idle = new Animation();
    Animation going = new Animation();
    Animation returning = new Animation();
    Animation breaking = new Animation();

    private static long enemyCooldown = 0;
    public static int status = IDLE;

    public Enemy(float posX, float posY, boolean center) {
        super(posX, posY, center);

        idle.images.add(Renderer.loadImage("/com/game/resources/enemy.png"));

        going.images.add(Renderer.loadImage("/com/game/resources/enemygoing1.png"));
        going.images.add(Renderer.loadImage("/com/game/resources/enemygoing2.png"));
        going.images.add(Renderer.loadImage("/com/game/resources/enemygoing3.png"));

        returning.images.add(Renderer.loadImage("/com/game/resources/enemyreturning1.png"));
        returning.images.add(Renderer.loadImage("/com/game/resources/enemyreturning2.png"));
        returning.images.add(Renderer.loadImage("/com/game/resources/enemyreturning3.png"));

        breaking.images.add(Renderer.loadImage("/com/game/resources/enemybreaking1.png"));
        breaking.images.add(Renderer.loadImage("/com/game/resources/enemybreaking2.png"));

        changeAnimation(idle);
    }


    @Override
    public void update(float delta) {
        if(status==GOING && animations[0]!=going){ changeAnimation(going); }
        else if(status==BREAKING && animations[0]!=breaking){ changeAnimation(breaking); }
        else if(status==RETURNING && animations[0]!=returning){ changeAnimation(returning); }
        else if(status==IDLE){ changeAnimation(idle); }

        if(status==IDLE){
            Terrain breakTerrain=null;
            SlotTree breakSlotTree=null;
            for(SlotTree slotTree : Terrain.currentTerrain.treeSlots){
                if (slotTree.getStatus() == SlotTree.STATUS_ADULT && Math.random()<=0.05){
                    breakTerrain = Terrain.currentTerrain;
                    breakSlotTree = slotTree;
                    break;
                }
            }
            if (breakSlotTree==null){return;}
            if(System.nanoTime() > enemyCooldown){
                breakSlotTree.remove(breakTerrain);
                System.out.println("Arvore cortada");
                enemyCooldown = System.nanoTime()+ 1000000000L * 3;
            }
        }
    }

    private void changeAnimation(Animation a){ animations = new Animation[]{a}; }
}
