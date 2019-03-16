package com.lins.connect.drawable.widget.points;

import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.lins.connect.player.Player;
import com.lins.connect.player.PlayersList;

public class Connection implements Json.Serializable {
    static Point[] points;
    static PlayersList players;

    Point p0, p1;
    Player player;

    public Connection() {}

    Connection(Point point0, Point point1, Player player) {
        p0 = point0;
        p1 = point1;
        this.player = player;
    }

    boolean containsPoint(Point point) {
        return p0 == point || p1 == point;
    }

    Point otherPoint(Point point) {
        return p0 == point ? p1 : p0;
    }

    @Override
    public void write(Json json) {
        json.writeValue("p0", p0.getID(), Integer.class);
        json.writeValue("p1", p1.getID(), Integer.class);
        json.writeValue("player", player.getID(), Integer.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        final int p0id = jsonData.getInt("p0");
        final int p1id = jsonData.getInt("p1");
        for (Point p : points) {
            if (p.getID() == p0id)
                p0 = p;
            else if (p.getID() == p1id)
                p1 = p;
        }
        player = players.getPlayerById(jsonData.getInt("player"));
    }
}