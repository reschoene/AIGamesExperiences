package br.com.reschoene.player;

import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode
public abstract class Player {
    protected int velocity;
    protected Color color;
    protected String name;

    @Getter
    protected Dimension dimension;
    protected Dimension mapDimension;
    protected Point position;
    protected Map<Integer, Executable> actions;

    public Player(String name, Dimension mapDimension) {
        this.name = name;
        this.mapDimension = mapDimension;
        this.dimension = new Dimension(30, 30);
        this.velocity=10;
        this.position = new Point();
        this.position.setLocation(0, 0);
        this.color = Color.blue;

        this.actions = new HashMap<>();
    }

    public void moveUp(){
        position.y-= (position.y > 0? velocity: 0);
    }

    public void moveDown(){
        int deltaHeight = mapDimension.height-dimension.height;

        if (position.y < deltaHeight)
            position.y += velocity;
        else
            position.y = deltaHeight;
    }

    public void moveLeft(){
        position.x-= (position.x > 0? velocity: 0);
    }

    public void moveRight(){
        int deltaWidth = mapDimension.width-dimension.width;

        if (position.x < deltaWidth)
            position.x += velocity;
        else
            position.x = deltaWidth;
    }

    public abstract void update(List<Integer> pressedKeys);

    public void render(Graphics g) {
        g.setColor(this.color);
        g.drawRect(position.x, position.y, dimension.width, dimension.height);
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public Point getPosition(){
        return position;
    }
}
