package player;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class AiPlayer extends Player{
    protected List<Integer> moviments;
    protected List<Integer> bestSequence;

    private final int WELL_TRAINED_MOV_COUNT = 120;

    public AiPlayer(String name, Dimension mapDimension, List<Integer> pBestSequence) {
        super(name, mapDimension);

        moviments = new LinkedList<>();
        this.bestSequence = new LinkedList<>();
        pBestSequence.forEach((i) -> this.bestSequence.add(i));

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

    @Override
    public void update(List<Integer> pressedKeys) {
        int action = 0;

        if (!bestSequence.isEmpty()){
            action = ((LinkedList<Integer>)bestSequence).removeFirst();
        }

        //Se a melhor sequencia de todos os individuos tiver menos de 120 passos, entao para as mutacoes e ja
        //temos uma seq campea
        if ((bestSequence.isEmpty() || (bestSequence.size() > WELL_TRAINED_MOV_COUNT)) && (new Random().nextInt(2) == 1))
            action = new Random().nextInt(5); //number between 0 and 4

        this.moviments.add(action);

        actions.get(action).execute();
    }

    @Override
    public void saveScore(List<Integer> bestSequence) {
        FileWriter writer = null;
        try {
            boolean foundNewBest = false;
            foundNewBest = ((bestSequence.isEmpty()) || (bestSequence.size() > moviments.size()));

            if (foundNewBest)
                bestSequence.clear();

            boolean writeRes = ((moviments.size() > 0) && (moviments.size() <= WELL_TRAINED_MOV_COUNT));
            if (writeRes)
                writer = new FileWriter("scoreIA.txt", false);

            for (Integer i : this.moviments) {
                if (writeRes)
                    writer.write(String.valueOf(i) + " ");

                if (foundNewBest)
                    bestSequence.add(i);
            }

            if (writeRes) {
                writer.write(System.lineSeparator());

                writer.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
