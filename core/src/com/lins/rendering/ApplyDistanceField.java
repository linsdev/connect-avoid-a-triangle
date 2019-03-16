package com.lins.rendering;

import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.lins.shader.Shader.DistanceField;

public final class ApplyDistanceField {
    public static Texture apply(FileHandle file, float smoothing, float scale, boolean flipX, boolean flipY) {
        final Texture texture = new Texture(file);
        final Texture result = apply(texture, smoothing, scale, flipX, flipY);
        texture.dispose();
        return result;
    }

    public static Texture apply(Texture texture, float smoothing, float scale, boolean flipX, boolean flipY) {
        texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        final int width = (int) (.5f + texture.getWidth() * scale);
        final int height = (int) (.5f + texture.getHeight() * scale);
        com.lins.rendering.RenderToTexture r = new com.lins.rendering.RenderToTexture(width, height);
        initBatch(r, smoothing);
        r.batch.draw(texture, 0, 0, width, height, 0, 0, texture.getWidth(), texture.getHeight(), flipX, flipY);
        return r.end(Texture.TextureFilter.Nearest);
    }

    public static Texture apply(TextureRegion textureRegion, float smoothing, float scale) {
        textureRegion.getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        final int width = (int) (.5f + textureRegion.getRegionWidth() * scale);
        final int height = (int) (.5f + textureRegion.getRegionHeight() * scale);
        com.lins.rendering.RenderToTexture r = new com.lins.rendering.RenderToTexture(width, height);
        initBatch(r, smoothing);
        r.batch.draw(textureRegion, 0, 0, width, height);
        return r.end(Texture.TextureFilter.Nearest);
    }

    private static void initBatch(com.lins.rendering.RenderToTexture r, float smoothing) {
        r.begin();
        r.batch.setShader(DistanceField.newShader());
        r.batch.begin();
        r.batch.getShader().setUniformf(DistanceField.smoothing, smoothing);
    }
}