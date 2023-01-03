package si.um.feri.astronaut;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Logger;

import si.um.feri.astronaut.assets.AssetDescriptors;
import si.um.feri.astronaut.ecs.system.debug.support.ViewportUtils;
import si.um.feri.astronaut.screen.GameScreen;

public class MonkeyGame extends Game {

    private AssetManager assetManager;
    private SpriteBatch batch;

    @Override
    public void create() {
        Gdx.app.setLogLevel(Application.LOG_DEBUG);
        ViewportUtils.DEFAULT_CELL_SIZE = 32;

        assetManager = new AssetManager();
        assetManager.getLogger().setLevel(Logger.DEBUG);

        batch = new SpriteBatch();
        assetManager.load(AssetDescriptors.FONT32);
        assetManager.load(AssetDescriptors.GAME_PLAY);
        assetManager.load(AssetDescriptors.PICK_SOUND);
        assetManager.finishLoading();
        setScreen(new GameScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        assetManager.dispose();
        batch.dispose();
    }

    public AssetManager getAssetManager() {
        return assetManager;
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}
