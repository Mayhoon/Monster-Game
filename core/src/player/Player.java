package player;

import Enums.AnimationName;
import animations.Animator;
import collision.BodyCollider;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.physics.box2d.World;
import networking.CharacterData;

public class Player {
    private GamePadInput gamePadInput;
    private PlayerMotor playerMotor;
    private Animator animator;
    private SpriteBatch batch;
    private BodyCollider bodyCollider;

    public Player(SpriteBatch batch, CharacterData state, boolean enableControls, World world) {
        this.batch = batch;
        bodyCollider = new BodyCollider(world);
        animator = new Animator();

        if (enableControls) {
            playerMotor = new PlayerMotor(state);
            gamePadInput = new GamePadInput(playerMotor, state);
        }
    }

    public void update(float delta) {
        playerMotor.calculatePosition(delta);
    }

    public void render(float delta, CharacterData data) {
        bodyCollider.updatePositions(data.position, AnimationName.IDLE_SWORD_NOT_DRAWN);
        animator.update(batch, delta, data);
    }
}
