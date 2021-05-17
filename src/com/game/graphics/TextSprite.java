package com.game.graphics;

import java.awt.*;

public class TextSprite {

    public int x, y;
    public String text;
    public Color c;


    public TextSprite(int x, int y, String text, Color c){
        this.x = x;
        this.y = y;
        this.text = text;
        this.c = c;
    }

    public void render (Graphics g){
        g.setColor(c);
        g.setFont(Renderer.getFont("TeenyTinyPixls"));
        g.drawString(text, x, y);
    }

}
