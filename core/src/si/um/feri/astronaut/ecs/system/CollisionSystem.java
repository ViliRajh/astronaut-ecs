package si.um.feri.astronaut.ecs.system;

import com.badlogic.ashley.core.Engine;
import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.EntitySystem;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.utils.ImmutableArray;
import com.badlogic.gdx.math.Intersector;

import si.um.feri.astronaut.common.GameManager;
import si.um.feri.astronaut.common.Mappers;
import si.um.feri.astronaut.ecs.component.StoneComponent;
import si.um.feri.astronaut.ecs.component.BananaComponent;
import si.um.feri.astronaut.ecs.component.BoundsComponent;
import si.um.feri.astronaut.ecs.component.MonkeyComponent;
import si.um.feri.astronaut.ecs.system.passive.SoundSystem;

public class CollisionSystem extends EntitySystem {

    private static final Family MONKEY_FAMILY = Family.all(MonkeyComponent.class, BoundsComponent.class).get();
    private static final Family STONE_FAMILY = Family.all(StoneComponent.class, BoundsComponent.class).get();
    private static final Family BANANA_FAMILY = Family.all(BananaComponent.class, BoundsComponent.class).get();

    private SoundSystem soundSystem;

    public CollisionSystem() {
    }

    @Override
    public void addedToEngine(Engine engine) {
        soundSystem = engine.getSystem(SoundSystem.class);
    }

    @Override
    public void update(float deltaTime) {
        if (GameManager.INSTANCE.isGameOver()) return;

        ImmutableArray<Entity> monkeys = getEngine().getEntitiesFor(MONKEY_FAMILY);
        ImmutableArray<Entity> stones = getEngine().getEntitiesFor(STONE_FAMILY);
        ImmutableArray<Entity> bananas = getEngine().getEntitiesFor(BANANA_FAMILY);

        for (Entity monkeyEntity : monkeys) {
            BoundsComponent monkeyBounds = Mappers.BOUNDS.get(monkeyEntity);

            // check collision with stones
            for (Entity stoneEntity : stones) {
                StoneComponent stone = Mappers.STONES.get(stoneEntity);

                if (stone.hit) {
                    continue;
                }

                BoundsComponent stoneBounds = Mappers.BOUNDS.get(stoneEntity);

                if (Intersector.overlaps(monkeyBounds.rectangle, stoneBounds.rectangle)) {
//                    stone.hit = true;
                    GameManager.INSTANCE.damage();
                    soundSystem.pick();
                }
            }

            // check collision with bananas
            for (Entity bananaEntity : bananas) {
                BananaComponent banana = Mappers.BANANAS.get(bananaEntity);

                if (banana.hit) {
                    continue;
                }

                BoundsComponent bananaBounds = Mappers.BOUNDS.get(bananaEntity);

                if (Intersector.overlaps(monkeyBounds.rectangle, bananaBounds.rectangle)) {
                    banana.hit = true;
                    GameManager.INSTANCE.incResult();
                    soundSystem.pick();
                    getEngine().removeEntity(bananaEntity);
                }
            }
        }
    }
}
