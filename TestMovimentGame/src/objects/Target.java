package objects;

import java.awt.*;

public class Target {
    private Point position;
    private Dimension dimension;
    private Color color;

    public Target() {
        this.position = new Point(450, 450);
        this.dimension = new Dimension(30, 30);
        this.color = Color.yellow;
    }

    public Target(Point position, Dimension dimension, Color color) {
        this.position = position;
        this.dimension = dimension;
        this.color = color;
    }

    public void render(Graphics g) {
        g.setColor(this.color);
        g.drawRect(position.x, position.y, dimension.width, dimension.height);
    }

    public Point getPosition() {
        return position;
    }

    public void setPosition(Point position) {
        this.position = position;
    }

    public Dimension getDimension() {
        return dimension;
    }

    public void setDimension(Dimension dimension) {
        this.dimension = dimension;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}
