import game.Game;
import game.GameConfig;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class GameMenu extends JFrame {
    private JLabel lblUsingHumanPlayer, lblTrainingPhase, lblAIPlayerCount, lblFps;
    private JCheckBox chkUsingHumanPlayer, chkIsAiTrainingPhase;
    private JTextField txtAiPlayerCount, txtFps;
    private JButton btnStartGame;

    public GameMenu(){
        super();

        buildScreen();
        setupScreen();
        setVisible(true);
    }

    private void setupScreen(){
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setResizable(false);
        setTitle("Movement Ai Game");
        pack();
        setLocationRelativeTo(null);
    }

    private void buildScreen(){
        var c = new GridBagConstraints();

        setLayout(new GridBagLayout());

        lblUsingHumanPlayer = new JLabel();
        lblUsingHumanPlayer.setText("Add human player");

        chkUsingHumanPlayer = new JCheckBox();
        chkUsingHumanPlayer.setSelected(true);

        lblTrainingPhase = new JLabel();
        lblTrainingPhase.setText("Is AI training phase");

        chkIsAiTrainingPhase = new JCheckBox();
        chkIsAiTrainingPhase.setSelected(true);
        chkIsAiTrainingPhase.addActionListener((ActionEvent e) ->{
            lblAIPlayerCount.setEnabled(chkIsAiTrainingPhase.isSelected());
            txtAiPlayerCount.setEnabled(chkIsAiTrainingPhase.isSelected());
        });

        lblAIPlayerCount = new JLabel();
        lblAIPlayerCount.setText("AI Player Count");

        txtAiPlayerCount = new JTextField();
        txtAiPlayerCount.setText("300");

        lblFps = new JLabel();
        lblFps.setText("FPS");

        txtFps = new JTextField();
        txtFps.setText("60");

        btnStartGame = new JButton();
        btnStartGame.setText("Start Game");
        btnStartGame.addActionListener((ActionEvent e) -> {
            int aIplayerCount=0;
            try {
                aIplayerCount = Integer.parseInt(txtAiPlayerCount.getText());
            }catch (NumberFormatException nfe){
                aIplayerCount = 300;
            }

            int fps=0;
            try {
                fps = Integer.parseInt(txtFps.getText());
            }catch (NumberFormatException nfe){
                fps = 60;
            }

            var gc = new GameConfig(chkUsingHumanPlayer.isSelected(), aIplayerCount, chkIsAiTrainingPhase.isSelected(), fps);
            new Game(gc).start();

            this.setVisible(false);
        });

        c.gridy = 0;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblUsingHumanPlayer, c);

        c.gridy = 0;
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(chkUsingHumanPlayer, c);

        c.gridy = 1;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblTrainingPhase, c);

        c.gridy = 1;
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(chkIsAiTrainingPhase, c);

        c.gridy = 2;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblAIPlayerCount, c);

        c.gridy = 2;
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        add(txtAiPlayerCount, c);

        c.gridy = 3;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        add(lblFps, c);

        c.gridy = 3;
        c.gridx = 1;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.ipadx = 100;
        add(txtFps, c);

        c.gridy = 4;
        c.gridx = 0;
        c.fill = GridBagConstraints.HORIZONTAL;
        c.gridwidth = 2;
        add(btnStartGame, c);
    }

    public static void main(String[] args) {
        new GameMenu();
    }
}
