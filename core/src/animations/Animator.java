package animations;

import Enums.AnimationState;
import Enums.Direction;
import Enums.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import networking.ServerClientWrapper;
import player.PlayerAnimations;
import space.earlygrey.shapedrawer.ShapeDrawer;

import java.util.List;

public class Animator {
    private ServerClientWrapper wrapper;
    private Entity entity;
    private AnimationState animationState;
    private List<String> animationQueue;
    private float elapsedTime = 0f;
    private TextureRegion region;
    private TextureRegion keyFrame;
    private Direction currentDirection;
    private int xDirection;

    private ShapeRenderer shapeRenderer;
    private PlayerAnimations playerAnimations;

    public Animator(ServerClientWrapper wrapper, Entity entity) {
        this.wrapper = wrapper;
        this.entity = entity;
        this.xDirection = -1;
        this.playerAnimations = new PlayerAnimations();

        //Debug renderer for shapes
        shapeRenderer = new ShapeRenderer();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap); //remember to dispose of later
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);

        setAnimation(AnimationState.IDLE_SWORD_NOT_DRAWN);
    }

    public void update(SpriteBatch batch, Vector2 position, Direction direction) {
        elapsedTime += (Gdx.graphics.getDeltaTime());
        ShapeDrawer shapedrawer = new ShapeDrawer(batch, region);

        if (entity.equals(Entity.Player)) {
            keyFrame = playerAnimations.get(animationState).getKeyFrame(elapsedTime, true);

            if (direction.equals(Direction.LEFT)) {
                xDirection = -1;
            } else if (direction.equals(Direction.RIGHT)) {
                xDirection = 1;
            }

            batch.draw(keyFrame, position.x, position.y, keyFrame.getRegionWidth() / 2, keyFrame.getRegionHeight() / 2, keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), xDirection, 1, 0);
            wrapper.ownData().animation = animationState;
            shapedrawer.rectangle(position.x, position.y, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());

        } else if (entity.equals(Entity.Opponent)) {
            TextureRegion keyFrame = playerAnimations.get(wrapper.opponentData().animation).getKeyFrame(elapsedTime, true);
            batch.draw(keyFrame, wrapper.opponentData().position.x, wrapper.opponentData().position.y);
            shapedrawer.rectangle(wrapper.opponentData().position.x, wrapper.opponentData().position.y, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
        }
    }

    public void setAnimation(AnimationState animationState) {
        this.animationState = animationState;
        wrapper.ownData().animation = animationState;
    }
}
