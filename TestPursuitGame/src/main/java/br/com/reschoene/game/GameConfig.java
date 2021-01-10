package br.com.reschoene.game;

public class GameConfig {
    private final int aIPlayerCount;
    private final int fps;

    public GameConfig(int aIPlayerCount, int fps) {
        this.aIPlayerCount = aIPlayerCount;
        this.fps = fps;
    }

    public int getAIPlayerCount() {
        return aIPlayerCount;
    }

    public int getFps(){
        return this.fps;
    }
}
