package com.lins.rendering;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.math.Matrix4;

public class FrameBuffer2D extends FrameBuffer {

    public Matrix4 projectionMatrix = new Matrix4();

    public FrameBuffer2D(int width, int height) {
        super(Pixmap.Format.RGBA8888, width, height, false);
        projectionMatrix.setToOrtho2D(0, 0, width, height);
    }

    @Override
    protected void disposeColorTexture(Texture colorTexture) {
        //super.disposeColorTexture(colorTexture);  not dispose Texture at dispose FrameBuffer
    }
}