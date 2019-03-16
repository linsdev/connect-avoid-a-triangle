package com.lins.shader;

import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.lins.shader.src.Frag;
import com.lins.shader.src.Vert;

public final class Shader {
    public static class Disk {
        /** uniform float u_texSize; */
        public static final String texSize = Frag.Disk.u_texSize;

        public static final float BORDER_SIZE = Frag.Disk.BORDER_SIZE;

        public static ShaderProgram newShader() {
            return new ShaderProgram(Vert.Default.src, Frag.Disk.src);
        }
    }

    public static class Circle {
        /** uniform float u_texSize; */
        public static final String texSize = Frag.Circle.u_texSize;
        /** uniform float u_lineWidth; */
        public static final String lineWidth = Frag.Circle.u_lineWidth;

        public static final float BORDER_SIZE = Frag.Circle.BORDER_SIZE;

        public static ShaderProgram newShader() {
            return new ShaderProgram(Vert.Default.src, Frag.Circle.src);
        }
    }

    public static class DistanceField {
        /** uniform float u_smoothing; */
        public static final String smoothing = Frag.DistanceField.u_smoothing;

        public static ShaderProgram newShader() {
            return new ShaderProgram(Vert.Default.src, Frag.DistanceField.src);
        }
    }
}