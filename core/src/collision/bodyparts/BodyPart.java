package collision.bodyparts;

import enums.AnimationName;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public abstract class BodyPart {
    public String id;
    public Body body;
    private AnimationName animation;
    private float offsetX, offsetY;

    public BodyPart(Body body, String id, AnimationName animation, float offsetX, float offsetY) {
        this.body = body;
        this.body.setActive(false);
        this.id = id;
        this.animation = animation;
        this.offsetX = offsetX;
        this.offsetY = offsetY;
    }

    public Body getBody() {
        return body;
    }

    public abstract void hit();

    public void updatePosition(Vector2 position, AnimationName currentAnimation) {
        if (currentAnimation.equals(animation)) {
            body.setActive(true);
            body.setTransform((position.x / 10) + offsetX, position.y + offsetY, 0);
        } else {
            body.setActive(false);
        }
    }
}
