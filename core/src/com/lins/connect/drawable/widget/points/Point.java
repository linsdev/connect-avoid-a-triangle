package com.lins.connect.drawable.widget.points;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.lins.anim.MoveAnim;
import com.lins.connect.player.Player;
import com.lins.drawable.Drawable;

import static com.badlogic.gdx.math.MathUtils.roundPositive;

public class Point extends Drawable implements Json.Serializable {
    static float BOUND_SIZE;
    static float RADIUS;
    static Color COLOR;
    static TextureRegion texReg;
    static Vector2 globalPos;

    private int id;
    public int getID() {
        return id;
    }

    Vector2 pos = new Vector2();
    private Vector2 posTex = new Vector2();
    Color color;
    MoveAnim moveAnim;
    Array<Connection> connections;

    public Point() {}

    Point(int id, float x, float y, int maxConnections) {
        init(id, x, y);
        connections = new Array<Connection>(true, maxConnections);
    }

    private void init(int id, float x, float y) {
        this.id = id;
        this.color = COLOR;
        boundRect = new Rectangle(0, 0, BOUND_SIZE, BOUND_SIZE);
        setPos(0, 0);
        moveAnim = new MoveAnim(posTex, .001f, new Vector2(posTex),
                new Vector2(x + globalPos.x - RADIUS, y + globalPos.y - RADIUS));
    }

    @Override
    public void setPos(float x, float y) {
        pos.set(x, y).add(globalPos);
        posTex.set(pos).sub(RADIUS, RADIUS);
        boundRect.setCenter(pos);
    }

    void onMoveAnimFinish() {
        posTex.x = roundPositive(posTex.x);
        posTex.y = roundPositive(posTex.y);
        pos.set(posTex).add(RADIUS, RADIUS);
        boundRect.setCenter(pos);
        moveAnim = null;
    }

    @Override
    public void draw(Batch batch) {
        batch.setColor(color);
        batch.draw(texReg, posTex.x, posTex.y);
    }

    boolean isNotConnected(Point point) {
        for (Connection c : connections)
            if (c.containsPoint(point)) return false;
        return true;
    }

    boolean isCompleteTriangle(Point secondPoint, Player player) {
        for (Connection c0 : connections)
            for (Connection c1 : secondPoint.connections)
                if (c0.player == player && c1.player == player &&
                        c0.otherPoint(this) == c1.otherPoint(secondPoint)) return true;
        return false;
    }

    @Override
    public void write(Json json) {
        json.writeValue("id", id, Integer.class);
        json.writeValue("x", pos.x - globalPos.x, Float.class);
        json.writeValue("y", pos.y - globalPos.y, Float.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        init(jsonData.getInt("id"), jsonData.getFloat("x"), jsonData.getFloat("y"));
    }
}