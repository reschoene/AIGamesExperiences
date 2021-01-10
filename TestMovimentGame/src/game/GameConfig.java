package game;

public class GameConfig {
    private final boolean usingHumanPlayer;
    private final int aIPlayerCount;
    private final int fps;
    private final boolean trainingPhase;

    public GameConfig(boolean usingHumanPlayer, int aIPlayerCount, boolean trainingPhase, int fps) {
        this.usingHumanPlayer = usingHumanPlayer;
        this.aIPlayerCount = aIPlayerCount;
        this.trainingPhase = trainingPhase;
        this.fps = fps;
    }

    public boolean isUsingHumanPlayer() {
        return usingHumanPlayer;
    }

    public int getAIPlayerCount() {
        return aIPlayerCount;
    }

    public boolean isTrainingPhase() {
        return trainingPhase;
    }

    public int getFps(){
        return this.fps;
    }
}
