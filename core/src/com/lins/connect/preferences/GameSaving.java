package com.lins.connect.preferences;

import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.Json;
import com.lins.connect.drawable.widget.points.Connection;
import com.lins.connect.drawable.widget.points.Point;
import com.lins.connect.player.PlayersList;

public final class GameSaving {
    private static final String keyPlayers = "saving.players";
    private static final String keyPoints = "saving.points";
    private static final String keyConnections = "saving.connections";
    private static final String keyMaxConnections = "saving.maxConnections";

    public static boolean isExists() {
        return GamePref.getPref().contains(keyPoints);
    }

    public static void save(PlayersList players, Point[] points, Array<Connection> connections, int maxConnections) {
        Json j = new Json();
        Preferences p = GamePref.getPref();
        p.putString(keyPlayers, j.toJson(players, PlayersList.class));
        p.putString(keyPoints, j.toJson(new Array<Point>(points), Array.class, Point.class));
        p.putString(keyConnections, j.toJson(connections, Array.class, Connection.class));
        p.putInteger(keyMaxConnections, maxConnections);
        p.flush();
    }

    public static void clear() {
        Preferences p = GamePref.getPref();
        p.remove(keyPlayers);
        p.remove(keyPoints);
        p.remove(keyConnections);
        p.remove(keyMaxConnections);
        p.flush();
    }

    public static class Load {
        private final Json json = new Json();
        private final Preferences pref = GamePref.getPref();

        public PlayersList loadPlayerList() {
            return json.fromJson(PlayersList.class, pref.getString(keyPlayers));
        }

        public Point[] loadPoints() {
            Array a = json.fromJson(Array.class, Point.class, pref.getString(keyPoints));
            final Point[] p = new Point[a.size];
            for (int i = 0; i < p.length; ++i)
                p[i] = (Point) a.items[i];
            return p;
        }

        public Array<Connection> loadConnections() {
            return json.fromJson(Array.class, Connection.class, pref.getString(keyConnections));
        }

        public int loadMaxConnections() {
            return pref.getInteger(keyMaxConnections);
        }
    }
}