package com.lins;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;

public class InputMultiplexer extends com.badlogic.gdx.InputMultiplexer {
    public InputMultiplexer() {
        super();
    }

    public InputMultiplexer(InputProcessor... processors) {
        super(processors);
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        return super.touchDown(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        screenY = Gdx.graphics.getHeight() - screenY;
        return super.touchUp(screenX, screenY, pointer, button);
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        screenY = Gdx.graphics.getHeight() - screenY;
        return super.touchDragged(screenX, screenY, pointer);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        screenY = Gdx.graphics.getHeight() - screenY;
        return super.mouseMoved(screenX, screenY);
    }
}