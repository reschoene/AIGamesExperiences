package br.com.reschoene.player;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AiPlayer extends Player {
    private HumanPlayer humanPlayer;

    public AiPlayer(HumanPlayer humanPlayer, String name, Dimension mapDimension) {
        super(name, mapDimension);

        this.humanPlayer = humanPlayer;

        setRandomLocation();

        actions.put(0, () -> {
            //do nothing
        });

        actions.put(1, () -> {
            this.moveUp();
        });

        actions.put(2, () -> {
            this.moveDown();
        });

        actions.put(3, () -> {
            this.moveLeft();
        });

        actions.put(4, () -> {
            this.moveRight();
        });
    }

    private void setRandomLocation() {
        final int MIN_START_DIST_FROM_HUMAN = 200;

        int posX = new Random().nextInt(mapDimension.width - dimension.width);
        int posY = new Random().nextInt(mapDimension.height - dimension.height);
        this.getPosition().setLocation(posX, posY);

        //if is so close to the human player, randomize location again
        if ((Math.abs(posX - humanPlayer.getPosition().x) < MIN_START_DIST_FROM_HUMAN) ||
                (Math.abs(posY - humanPlayer.getPosition().y) < MIN_START_DIST_FROM_HUMAN))
            setRandomLocation();
    }

    @Override
    public void update(List<Integer> pressedKeys) {
        final int MIN_DIST_TO_PURSUIT = 100;
        int action = 0;

        var pos = this.humanPlayer.getPosition();

        boolean up = pos.y < getPosition().y;
        boolean left = pos.x < getPosition().x;

        int deltaX = Math.abs(pos.x - getPosition().x);
        int deltaY = Math.abs(pos.y - getPosition().y);

        boolean playerIsFarAway = (deltaY > MIN_DIST_TO_PURSUIT) && (deltaX > MIN_DIST_TO_PURSUIT);

        if (!playerIsFarAway && (new Random().nextInt(3) == 1)) {
            action = getPursuitNextMovAction(up, left, deltaX, deltaY);
        } else {
            action = new Random().nextInt(5);
        }

        actions.get(action).execute();
    }

    private int getPursuitNextMovAction(boolean up, boolean left, int deltaX, int deltaY) {
        int action;

        if (deltaX > deltaY) {
            //horizontal move
            if (left)
                action = 3;
            else
                action = 4;
        } else {
            if (up)
                action = 1;
            else
                action = 2;
        }
        return action;
    }
}
