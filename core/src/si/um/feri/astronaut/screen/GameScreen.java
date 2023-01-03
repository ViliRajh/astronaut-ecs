package si.um.feri.astronaut.screen;

import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.PooledEngine;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.utils.Logger;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

import si.um.feri.astronaut.MonkeyGame;
import si.um.feri.astronaut.assets.AssetDescriptors;
import si.um.feri.astronaut.common.GameManager;
import si.um.feri.astronaut.config.GameConfig;
import si.um.feri.astronaut.ecs.system.StoneSpawnSystem;
import si.um.feri.astronaut.ecs.system.BananaSpawnSystem;
import si.um.feri.astronaut.ecs.system.BoundsSystem;
import si.um.feri.astronaut.ecs.system.CleanUpSystem;
import si.um.feri.astronaut.ecs.system.CollisionSystem;
import si.um.feri.astronaut.ecs.system.HudRenderSystem;
import si.um.feri.astronaut.ecs.system.MovementSystem;
import si.um.feri.astronaut.ecs.system.RenderSystem;
import si.um.feri.astronaut.ecs.system.MonkesInputSystem;
import si.um.feri.astronaut.ecs.system.WorldWrapSystem;
import si.um.feri.astronaut.ecs.system.debug.DebugCameraSystem;
import si.um.feri.astronaut.ecs.system.debug.DebugInputSystem;
import si.um.feri.astronaut.ecs.system.debug.DebugRenderSystem;
import si.um.feri.astronaut.ecs.system.debug.GridRenderSystem;
import si.um.feri.astronaut.ecs.system.passive.EntityFactorySystem;
import si.um.feri.astronaut.ecs.system.passive.SoundSystem;
import si.um.feri.astronaut.ecs.system.passive.StartUpSystem;

/**
 * Artwork from https://goodstuffnononsense.com/about/
 * https://goodstuffnononsense.com/hand-drawn-icons/space-icons/
 */
public class GameScreen extends ScreenAdapter {

    private static final Logger log = new Logger(GameScreen.class.getSimpleName(), Logger.DEBUG);

    private final AssetManager assetManager;
    private final SpriteBatch batch;
//    private final AstronautGame game;

    private OrthographicCamera camera;
    private Viewport viewport;
    private Viewport hudViewport;
    private ShapeRenderer renderer;
    private PooledEngine engine;
    private BitmapFont font;
    private boolean debug = true;

    public GameScreen(MonkeyGame game) {
//        this.game = game;
        assetManager = game.getAssetManager();
        batch = game.getBatch();
    }

    @Override
    public void show() {
        camera = new OrthographicCamera();
        viewport = new FitViewport(GameConfig.WIDTH, GameConfig.HEIGHT, camera);
        hudViewport = new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        renderer = new ShapeRenderer();
        font = assetManager.get(AssetDescriptors.FONT32);

        engine = new PooledEngine();

        // passive systems
        engine.addSystem(new EntityFactorySystem(assetManager));
        engine.addSystem(new SoundSystem(assetManager));
        engine.addSystem(new StartUpSystem());  // called only at the start, to generate first entities

        engine.addSystem(new MonkesInputSystem());
        engine.addSystem(new MovementSystem());
        engine.addSystem(new WorldWrapSystem());
        engine.addSystem(new BoundsSystem());
        engine.addSystem(new StoneSpawnSystem());
        engine.addSystem(new BananaSpawnSystem());
        engine.addSystem(new CleanUpSystem());
        engine.addSystem(new CollisionSystem());
        engine.addSystem(new RenderSystem(batch, viewport));
        engine.addSystem(new HudRenderSystem(batch, hudViewport, font));

        // debug systems
        if (debug) {
            engine.addSystem(new GridRenderSystem(viewport, renderer));
            engine.addSystem(new DebugRenderSystem(viewport, renderer));
            engine.addSystem(new DebugCameraSystem(
                    GameConfig.WIDTH / 2, GameConfig.HEIGHT / 2,
                    camera
            ));
        }
        engine.addSystem(new DebugInputSystem());

        GameManager.INSTANCE.resetResult();
        logAllSystemsInEngine();
    }

    @Override
    public void render(float delta) {
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) Gdx.app.exit();
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE)) GameManager.INSTANCE.resetResult();

        ScreenUtils.clear(Color.BLACK);

        if (GameManager.INSTANCE.isGameOver()) {
            engine.update(0);
        } else {
            engine.update(delta);
        }

//        if (GameManager.INSTANCE.isGameOver()) {
//            game.setScreen(new MenuScreen(game));
//        }
    }

    @Override
    public void resize(int width, int height) {
        viewport.update(width, height, true);
        hudViewport.update(width, height, true);
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void dispose() {
        renderer.dispose();
        engine.removeAllEntities();
    }

    public void logAllSystemsInEngine() {
        for (EntitySystem system : engine.getSystems()) {
            log.debug(system.getClass().getSimpleName());
        }
    }
}
