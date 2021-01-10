package player;

import java.awt.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class TrainedAIPlayer extends AiPlayer {

    public TrainedAIPlayer(String name, Dimension mapDimension) {
        super(name, mapDimension, List.of());
    }

    public void loadIATrainingData() {
        final String FILE_NAME = "scoreIA.txt";

        bestSequence.clear();

        List<String> result = null;
        try (Stream<String> lines = Files.lines(Paths.get(FILE_NAME))) {
            result = lines.collect(Collectors.toList());
        }catch (IOException e){
            e.printStackTrace();
        }

        if ((result != null) && (!result.isEmpty())){
            String firstLine = result.get(0);

            String[] content = firstLine.split(" ");
            for(String c : content){
                try {
                    int v = Integer.parseInt(c);
                    bestSequence.add(v);
                }catch (NumberFormatException nfe){
                    nfe.printStackTrace();
                }
            }
        }
    }

    @Override
    public void update(List<Integer> pressedKeys) {
        int action = 0;

        if (!bestSequence.isEmpty())
            action = ((LinkedList<Integer>) bestSequence).removeFirst();

        actions.get(action).execute();
    }

    @Override
    public void saveScore(List<Integer> bestSequence) {
        //As it already was trained, it is not necessary to save score
    }
}

