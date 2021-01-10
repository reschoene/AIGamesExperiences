package br.com.reschoene.game;

import br.com.reschoene.player.AiPlayer;
import br.com.reschoene.player.HumanPlayer;
import br.com.reschoene.player.Player;

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
    private HumanPlayer humanPlayer;

    private int fps;

    private boolean gameOver = false;

    private final String BASE_TITLE = "Pursuit Game";

    private final GameConfig gameConfig;

    public Game(GameConfig gameConfig) {
        this.gameConfig = gameConfig;
        this.mapSize = new Dimension(500, 500);
        this.setPreferredSize(this.mapSize);

        this.fps = gameConfig.getFps();

        this.pressedKeys = new LinkedList<>();

        this.players = new LinkedList<>();

        this.addPlayers();

        this.addKeyListener(this);

        this.setFocusable(true);
    }

    private void addPlayers() {
        this.humanPlayer = new HumanPlayer("Renato", mapSize);
        this.players.add(this.humanPlayer);

        for (int i = 0; i < gameConfig.getAIPlayerCount(); i++)
            players.add(new AiPlayer(this.humanPlayer, "IA Player " + (i + 1), mapSize));
    }

    @Override
    public void run() {
        while (!gameOver)
            processGame();

        processGame();

        JOptionPane.showMessageDialog(null, "Game Over");
    }

    private void processGame() {
        this.tick();
        this.render();

        try {
            Thread.sleep(1000 / fps);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private void tick() {
        this.players.forEach((player) -> {
            player.update(this.pressedKeys);

            if (checkCollision(this.humanPlayer, player))
                gameOver = true;
        });
    }

    private boolean checkCollision(HumanPlayer humanPlayer, Player player) {
        boolean hasCollision = false;

        if (!humanPlayer.equals(player)) {
            var rHuman = new Rectangle(humanPlayer.getPosition().x,
                    humanPlayer.getPosition().y,
                    humanPlayer.getDimension().width,
                    humanPlayer.getDimension().height);

            var rAi = new Rectangle(player.getPosition().x,
                    player.getPosition().y,
                    player.getDimension().width,
                    player.getDimension().height);
            hasCollision = rHuman.intersects(rAi);
        }

        return hasCollision;
    }

    private void render() {
        var bs = this.getBufferStrategy();
        if (bs == null) {
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
        this.players.forEach((player) -> {
            player.render(g);
        });
    }

    private void clearScreen(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0, 0, mapSize.width, mapSize.height);
    }

    public void start() {
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
