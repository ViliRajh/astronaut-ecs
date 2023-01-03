package si.um.feri.astronaut.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.systems.IntervalSystem;

import si.um.feri.astronaut.config.GameConfig;
import si.um.feri.astronaut.ecs.system.passive.EntityFactorySystem;

public class BananaSpawnSystem extends IntervalSystem {

    private EntityFactorySystem factory;

    public BananaSpawnSystem() {
        super(GameConfig.BANANA_SPAWN_TIME);
    }

    @Override
    public void addedToEngine(Engine engine) {
        super.addedToEngine(engine);
        factory = engine.getSystem(EntityFactorySystem.class);
    }

    @Override
    protected void updateInterval() {
        factory.createBanana();
    }
}
