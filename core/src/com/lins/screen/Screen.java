package com.lins.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.utils.Disposable;
import com.lins.Disposer;
import com.lins.Game;
import com.lins.InputMultiplexer;

/** Basic screen class with {@link Disposer} res <br>
 *  To add custom InputProcessor using this.inputMultiplexer */
public class Screen implements com.badlogic.gdx.Screen, Disposable {
    /** Gdx.gl.glClearColor(c.r, c.g, c.b, c.a); */
    public static void ClearColor(Color c) {
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
    }

    /** Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); */
    public static void Clear() {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    /** Gdx.gl.glClearColor(c.r, c.g, c.b, c.a); <p> Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT); */
    public static void Clear(Color c) {
        Gdx.gl.glClearColor(c.r, c.g, c.b, c.a);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }

    protected final Disposer res = new Disposer();
    protected final InputMultiplexer inputMultiplexer;

    public Screen() {
        inputMultiplexer = new InputMultiplexer();
    }

    public Screen(final Game game) {
        inputMultiplexer = new InputMultiplexer(game.inputAdapter);
    }

    /** Here is setting Gdx.input.setInputProcessor(inputMultiplexer) */
    public void show() {
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void onBackKey() {}

    public void render(float delta) {}

    /** Screen::resize() called twice if this Screen set in Game::create() <p>
     * First, with width==0 and height==0 */
    public void resize(int width, int height) {}

    public void pause() {}

    public void resume() {}

    /** Here is setting Gdx.input.setInputProcessor(null) */
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    public void dispose() {
        res.dispose();
    }
}