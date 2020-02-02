package com.mygdx.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.controllers.Controllers;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.mygdx.camera.Camera;
import com.mygdx.config.Paths;
import com.mygdx.hud.Hud;
import com.mygdx.input.ControllerInput;
import com.mygdx.player.Player;

public class GameScreen implements Screen {
    private Texture mapTexture;
    private Hud hud;
    private Camera camera;
    private SpriteBatch batch;
    private Player player;

    public GameScreen(SpriteBatch batch) {
        mapTexture = new Texture(Paths.TILESET_PATH);
        this.batch = batch;

        camera = new Camera();
        camera.zoom -= 0.9f;
        camera.position.x = 0;
        camera.position.y = 0;
        camera.update();

        player = new Player(camera);
        hud = new Hud();

        ControllerInput controllerInput = new ControllerInput(camera);
        Controllers.addListener(controllerInput);

        //input listener
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(player.getProcessor());
        inputMultiplexer.addProcessor(hud);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(99,155,255,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);


        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        player.render(batch);
        batch.draw(mapTexture, 0, 0);

        batch.setProjectionMatrix(hud.getStage().getCamera().combined);
        batch.end();
        hud.update();
        camera.update();
    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        batch.dispose();
        hud.dispose();
    }
}
