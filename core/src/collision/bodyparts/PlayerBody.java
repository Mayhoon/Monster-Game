package collision.bodyparts;

import Enums.AnimationName;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;

public class PlayerBody extends BodyPart {

    public PlayerBody(Body body, AnimationName animation, float offsetX, float offsetY) {
        super(body, "Body", animation, offsetX, offsetY );
        body.setUserData(this);
    }

    @Override
    public void hit() {
        System.out.println("[ Body hit ]");
    }
}