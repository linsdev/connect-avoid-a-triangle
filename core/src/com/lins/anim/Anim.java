package com.lins.anim;

public class Anim {
    /** Animation progress in the range [0,1] <p> The animation will end when progress >= 1 */
    protected float progress = 0;

    /** Don't forget to change the value of this.progress here
     * @param deltaTime value in milliseconds */
    protected void act(float deltaTime) {}
}