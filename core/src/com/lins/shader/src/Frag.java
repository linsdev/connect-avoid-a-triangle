package com.lins.shader.src;

public final class Frag {
    public static final class Circle {
        /** uniform float u_texSize; */
        public static final String u_texSize = "u_texSize";
        /** uniform float u_lineWidth; */
        public static final String u_lineWidth = "u_lineWidth";
        public static final float BORDER_SIZE = 1.5f;
        public static final String src =
                "#ifdef GL_ES\n" +
                "    #define LOWP lowp\n" +
                "    precision mediump float;\n" +
                "#else\n" +
                "    #define LOWP \n" +
                "#endif\n" +

                "varying LOWP vec4 v_color;" +
                "varying vec2 v_texCoords;" +

                "uniform sampler2D u_texture;" +
                "uniform float u_texSize;" +
                "uniform float u_lineWidth;" +

                "void main() {" +
                "    float border = 1.5 / u_texSize;" +
                "    float R = .5 * (1. - 4.*border);" +
                "    float r = R - (u_lineWidth-border);" +
                "    float dist = distance(vec2(.5,.5), v_texCoords);" +
                "    gl_FragColor = v_color;" +
                "    gl_FragColor.a *= smoothstep(r-border, r, dist) - smoothstep(R, R+border, dist);" +
                "}";
    }

    public static final class Disk {
        /** uniform float u_texSize; */
        public static final String u_texSize = "u_texSize";
        public static final float BORDER_SIZE = 0.5f;
        public static final String src =
                "#ifdef GL_ES\n" +
                "    #define LOWP lowp\n" +
                "    precision mediump float;\n" +
                "#else\n" +
                "    #define LOWP \n" +
                "#endif\n" +

                "varying LOWP vec4 v_color;" +
                "varying vec2 v_texCoords;" +

                "uniform sampler2D u_texture;" +
                "uniform float u_texSize;" +

                "void main() {" +
                "    float border = .5 / u_texSize;" +
                "    float radius = .5 * (1. - 4.*border);" +
                "    float dist = distance(vec2(.5,.5), v_texCoords);" +
                "    gl_FragColor = v_color;" +
                "    gl_FragColor.a *= smoothstep(radius+border, radius-border, dist);" +
                "}";
    }

    public static final class DistanceField {
        /** uniform float u_smoothing; */
        public static final String u_smoothing = "u_smoothing";
        public static final String src =
                "#ifdef GL_ES\n" +
                "    #define LOWP lowp\n" +
                "    precision mediump float;\n" +
                "#else\n" +
                "    #define LOWP \n" +
                "#endif\n" +

                "varying LOWP vec4 v_color;" +
                "varying vec2 v_texCoords;" +

                "uniform sampler2D u_texture;" +
                "uniform float u_smoothing;" +

                "void main() {" +
                "    float smoothing = .25 / u_smoothing;" +
                "    float dist = texture2D(u_texture, v_texCoords).a;" +
                "    gl_FragColor = v_color;" +
                "    gl_FragColor.a *= smoothstep(.5-smoothing, .5+smoothing, dist);" +
                "}";
    }
}