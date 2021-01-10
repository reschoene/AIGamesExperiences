package player;

import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class Player {
    private int velocity;
    private Color color;
    protected String name;

    private Dimension dimension;
    private Dimension mapDimension;
    private Point position;
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

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getVelocity() {
        return velocity;
    }

    public void setVelocity(int velocity) {
        this.velocity = velocity;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Dimension getMapDimension() {
        return mapDimension;
    }

    public void setMapDimension(Dimension mapDimension) {
        this.mapDimension = mapDimension;
    }

    public Point getPosition(){
        return position;
    }

    public abstract void saveScore(List<Integer> bestSequence);
}
