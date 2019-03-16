package com.lins;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;

public class Font extends BitmapFont {
    private static float readSmoothFromFile(FileHandle file) {
        final byte[] buffer = new byte[128];
        file.readBytes(buffer, 0, buffer.length);
        final String s = new String(buffer);
        final int i = s.indexOf("smooth=") + 7;
        return Float.valueOf(s.substring(i, s.indexOf(" ", i)));
    }

    private static Texture createFontTexture(FileHandle fileFont, Texture texture, float scale) {
        final float smoothing = readSmoothFromFile(fileFont);
        return com.lins.rendering.ApplyDistanceField.apply(texture, smoothing * scale, 2 * scale, false, true);
    }

    private Texture originTexture;

    public Font(FileHandle fontFile) {
        super(fontFile);
        init(fontFile, 1f);
    }

    public Font(FileHandle fontFile, float capHeight) {
        super(fontFile);
        final float scale = capHeight / getCapHeight();
        init(fontFile, scale);
        getData().setScale(scale);
    }

    private void init(FileHandle fontFile, float scale) {
        originTexture = createFontTexture(fontFile, getRegion().getTexture(), scale);
        getRegion().getTexture().dispose();
        setTexture(originTexture);
        setUseIntegerPositions(false);
    }

    public void setTexture(Texture texture) {
        getRegion().setTexture(texture);
    }

    public void setTextureRegion(Texture texture, int x, int y, int width, int height) {
        getRegion().setTexture(texture);
        getRegion().setRegion(x, y, width, height);
    }

    public Texture getOriginTexture() {
        return originTexture;
    }

    @Override
    public void dispose() {
        originTexture.dispose();
    }
}