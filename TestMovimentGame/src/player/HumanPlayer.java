package player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HumanPlayer extends Player{
    private int score;

    public HumanPlayer(String name, Dimension mapDimension) {
        super(name, mapDimension);

        this.setColor(Color.WHITE);
        this.score = 0;

        actions.put(KeyEvent.VK_UP, () -> {
            this.moveUp();
        });

        actions.put(KeyEvent.VK_DOWN, () -> {
            this.moveDown();
        });

        actions.put(KeyEvent.VK_LEFT, () -> {
            this.moveLeft();
        });

        actions.put(KeyEvent.VK_RIGHT, () -> {
            this.moveRight();
        });
    }

    @Override
    public void update(List<Integer> pressedKeys) {
        pressedKeys.forEach(i -> {
            if (actions.containsKey(i)) {
                actions.get(i).execute();
                this.score++;
            }
        });
    }

    @Override
    public void saveScore(List<Integer> bestSequence) {
        FileWriter writer;
        try {
            writer = new FileWriter("scorePlayer.txt", true);
            writer.write(this.name + ", you've solved it in " + this.score + " movements");
            writer.write(System.lineSeparator());
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
