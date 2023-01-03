package si.um.feri.astronaut.common;

import com.badlogic.ashley.core.ComponentMapper;

import si.um.feri.astronaut.ecs.component.BackgroundComponent;
import si.um.feri.astronaut.ecs.component.StoneComponent;
import si.um.feri.astronaut.ecs.component.BananaComponent;
import si.um.feri.astronaut.ecs.component.BoundsComponent;
import si.um.feri.astronaut.ecs.component.DimensionComponent;
import si.um.feri.astronaut.ecs.component.MovementComponentXYR;
import si.um.feri.astronaut.ecs.component.PositionComponent;
import si.um.feri.astronaut.ecs.component.MonkeyComponent;
import si.um.feri.astronaut.ecs.component.TextureComponent;
import si.um.feri.astronaut.ecs.component.ZOrderComponent;

//TODO Explain how Mappers work (see ComponentMapper and Entity implementation)
public final class Mappers {

    public static final ComponentMapper<StoneComponent> STONES =
            ComponentMapper.getFor(StoneComponent.class);

    public static final ComponentMapper<BananaComponent> BANANAS =
            ComponentMapper.getFor(BananaComponent.class);

    public static final ComponentMapper<BoundsComponent> BOUNDS =
            ComponentMapper.getFor(BoundsComponent.class);

    public static final ComponentMapper<DimensionComponent> DIMENSION =
            ComponentMapper.getFor(DimensionComponent.class);

    public static final ComponentMapper<MovementComponentXYR> MOVEMENT =
            ComponentMapper.getFor(MovementComponentXYR.class);

    public static final ComponentMapper<PositionComponent> POSITION =
            ComponentMapper.getFor(PositionComponent.class);

    public static final ComponentMapper<MonkeyComponent> MONKEY =
            ComponentMapper.getFor(MonkeyComponent.class);

    public static final ComponentMapper<TextureComponent> TEXTURE =
            ComponentMapper.getFor(TextureComponent.class);

    public static final ComponentMapper<ZOrderComponent> Z_ORDER =
            ComponentMapper.getFor(ZOrderComponent.class);

    public static final ComponentMapper<BackgroundComponent> BACKGROUND =
            ComponentMapper.getFor(BackgroundComponent.class);

    private Mappers() {
    }
}
