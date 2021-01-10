package game;

import objects.Target;
import player.AiPlayer;
import player.HumanPlayer;
import player.Player;
import player.TrainedAIPlayer;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.LinkedList;
import java.util.List;

public class Game extends Canvas implements Runnable, KeyListener {
    private final Dimension mapSize;

    private final List<Integer> pressedKeys;

    private final List<Player> players;
    private final Target target;

    private int fps;

    private int generationNumber = 0;

    private boolean gameOver = false;

    private List<Integer> bestSequence;

    private final String BASE_TITLE = "Movement game";

    private final GameConfig gameConfig;

    public Game(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.mapSize = new Dimension(500, 500);
        this.setPreferredSize(this.mapSize);

        this.fps = gameConfig.getFps();

        this.pressedKeys = new LinkedList<>();

        this.bestSequence = new LinkedList<>();

        this.players = new LinkedList<>();

        this.addPlayers();

        this.target = new Target();

        this.addKeyListener(this);

        this.setFocusable(true);
    }

    private void addPlayers() {
        if (gameConfig.isTrainingPhase()) {
            for (int i = 0; i < gameConfig.getAIPlayerCount(); i++)
                players.add(new AiPlayer("IA Player " + (i + 1), mapSize, bestSequence));
        }else{
            var trainedAIPlayer = new TrainedAIPlayer("Trained IA Player ", mapSize);
            trainedAIPlayer.loadIATrainingData();
            players.add(trainedAIPlayer);
        }

        if (this.gameConfig.isUsingHumanPlayer())
            this.players.add(new HumanPlayer("Renato", mapSize));
    }

    @Override
    public void run() {
        while(true) {
            while (!gameOver) {
                this.tick();
                this.render();

                try {
                    Thread.sleep(1000 / fps);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            restart();
        }
    }

    private void tick() {
        this.players.forEach((player) -> {
            if (player.getPosition().equals(target.getPosition())) {
                player.saveScore(this.bestSequence);
                gameOver = true;
            }

            player.update(this.pressedKeys);
        });
    }

    private void render() {
        var bs = this.getBufferStrategy();
        if (bs == null){
            this.createBufferStrategy(3);
            return;
        }

        var g = bs.getDrawGraphics();

        this.clearScreen(g);
        this.drawScene(g);

        g.dispose();
        bs.show();
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawScene(Graphics g) {
        this.players.forEach((player) -> player.render(g));

        this.target.render(g);
    }

    private void clearScreen(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0, mapSize.width, mapSize.height);
    }

    private void restart(){
        this.players.clear();
        this.addPlayers();
        gameOver = false;
        JFrame topFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        generationNumber++;
        topFrame.setTitle(BASE_TITLE + " - " + "geração " + generationNumber + " - nro mov: " + bestSequence.size());
    }

    public void start(){
        var frame = new JFrame(BASE_TITLE);

        frame.add(this);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.pack();
        frame.setLocationRelativeTo(null);

        frame.setVisible(true);

        var gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (!pressedKeys.contains(e.getKeyCode()))
            pressedKeys.add(e.getKeyCode());
    }

    @Override
    public void keyReleased(KeyEvent e) {
        int idx = pressedKeys.indexOf(e.getKeyCode());
        if (idx >= 0)
            pressedKeys.remove(idx);
    }
}
