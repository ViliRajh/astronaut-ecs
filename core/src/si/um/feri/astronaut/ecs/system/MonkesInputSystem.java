package si.um.feri.astronaut.ecs.system;

import com.badlogic.ashley.core.Entity;
import com.badlogic.ashley.core.Family;
import com.badlogic.ashley.systems.IteratingSystem;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;

import si.um.feri.astronaut.common.Mappers;
import si.um.feri.astronaut.config.GameConfig;
import si.um.feri.astronaut.ecs.component.MovementComponentXYR;
import si.um.feri.astronaut.ecs.component.MonkeyComponent;


public class MonkesInputSystem extends IteratingSystem {

    private static final Family FAMILY = Family.all(
            MonkeyComponent.class,
            MovementComponentXYR.class
    ).get();

    public MonkesInputSystem() {
        super(FAMILY);
    }

    // we don't need to override the update Iterating system method because there is no batch begin/end

    @Override
    protected void processEntity(Entity entity, float deltaTime) {
        MovementComponentXYR movement = Mappers.MOVEMENT.get(entity);
        movement.xSpeed = 0;

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            movement.xSpeed = GameConfig.MAX_MONKEY_X_SPEED * deltaTime;
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            movement.xSpeed = -GameConfig.MAX_MONKEY_X_SPEED * deltaTime;
        }

    }
}
