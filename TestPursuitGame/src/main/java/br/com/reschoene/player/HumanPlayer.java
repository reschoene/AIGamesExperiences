package br.com.reschoene.player;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class HumanPlayer extends Player{

    public HumanPlayer(String name, Dimension mapDimension) {
        super(name, mapDimension);

        this.setColor(Color.WHITE);

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
            }
        });
    }
}
