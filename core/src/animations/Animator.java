package animations;

import Enums.AnimationName;
import Enums.Direction;
import Enums.Entity;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import networking.ServerClientWrapper;
import player.PlayerAnimations;
import space.earlygrey.shapedrawer.ShapeDrawer;

public class Animator {
    private ServerClientWrapper wrapper;
    private Entity entity;
    private float elapsedTime = 0f;
    private TextureRegion region;
    private TextureRegion keyFrame;
    private Direction currentDirection;
    private int xDirection;
    private PlayerAnimations playerAnimations;

    public Animator(ServerClientWrapper wrapper, Entity entity) {
        this.wrapper = wrapper;
        this.entity = entity;
        this.playerAnimations = new PlayerAnimations();
        this.xDirection = -1;

        textureRegion();
        setAnimation(AnimationName.IDLE_SWORD_NOT_DRAWN);
    }

    private void textureRegion() {
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(Color.BLACK);
        pixmap.drawPixel(0, 0);
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        region = new TextureRegion(texture, 0, 0, 1, 1);
    }

    public void update(SpriteBatch batch) {
        elapsedTime += (Gdx.graphics.getDeltaTime());
        ShapeDrawer shapedrawer = new ShapeDrawer(batch, region);

        if (entity.equals(Entity.Player)) {
            keyFrame = playerAnimations.get(wrapper.ownData().animation).getKeyFrame(elapsedTime);
            wrapper.ownData().elapsedTime = elapsedTime;
            if (wrapper.ownData().equals(Direction.LEFT)) {
                xDirection = -1;
            } else if (wrapper.ownData().direction.equals(Direction.RIGHT)) {
                xDirection = 1;
            }
            batch.draw(keyFrame, wrapper.ownData().position.x, wrapper.ownData().position.y, keyFrame.getRegionWidth() / 2, keyFrame.getRegionHeight() / 2, keyFrame.getRegionWidth(), keyFrame.getRegionHeight(), xDirection, 1, 0);
            shapedrawer.rectangle(wrapper.ownData().position.x, wrapper.ownData().position.y, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());

        } else if (entity.equals(Entity.Opponent)) {
            TextureRegion keyFrame = playerAnimations.get(wrapper.opponentData().animation).getKeyFrame(wrapper.opponentData().elapsedTime);
            batch.draw(keyFrame, wrapper.opponentData().position.x, wrapper.opponentData().position.y);
            shapedrawer.rectangle(wrapper.opponentData().position.x, wrapper.opponentData().position.y, keyFrame.getRegionWidth(), keyFrame.getRegionHeight());
        }
        wrapper.sendTCP();
    }

    public void setAnimation(AnimationName animationName) {
        wrapper.ownData().animation = animationName;
    }

    public int getKeyFrameIndex(){
        return playerAnimations.get(wrapper.ownData().animation).getKeyFrameIndex(elapsedTime);
    }
}
