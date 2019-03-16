package com.lins.connect.drawable.widget.points;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
import com.lins.anim.AnimGroupPlayer;
import com.lins.connect.preferences.GameSaving;
import com.lins.connect.player.PlayersList;
import com.lins.drawable.Drawable;
import com.lins.screen.Screen;

import static com.badlogic.gdx.math.MathUtils.PI2;
import static com.badlogic.gdx.math.MathUtils.cos;
import static com.badlogic.gdx.math.MathUtils.random;
import static com.badlogic.gdx.math.MathUtils.sin;

public class PointsArray extends Drawable {
    public interface GameEndListener {
        void end(PlayersList players);
    }

    private class Input extends InputAdapter {
        public boolean touchDown(int screenX, int screenY, int pointer, int button) {
            if (!boundRect.contains(screenX, screenY)) return false;
            for (Point p : points)
                if (p.boundRect.contains(screenX, screenY)) {
                    if (selectedPoint == null || selectedPoint == p) {
                        selectedPoint = p;
                        selectedPoint.color = players.current.colorLine;
                        return true;
                    }
                    if (p.isNotConnected(selectedPoint)) {
                        addConnection(p, selectedPoint);
                        if (p.isCompleteTriangle(selectedPoint, players.current)) {
                            onCompleteTriangle.end(players);
                            break;
                        }
                        if (connections.size == MAX_CONNECTIONS) {
                            onGameEnd.end(players);
                            break;
                        }
                        players.next();
                        break;
                    }
                    else return true;
                }
            if (selectedPoint != null) {
                selectedPoint.color = Point.COLOR;
                selectedPoint = null;
            }
            return true;
        }
    }

    public GameEndListener onCompleteTriangle, onGameEnd;
    private final int MAX_CONNECTIONS;
    private Vector2 globalPos = new Vector2();
    private final Point[] points;
    private final Array<Connection> connections;
    private Point selectedPoint;
    private AnimGroupPlayer animGroupPlayer;
    private PlayersList players;

    private void init(TextureRegion texReg, float circleRadius) {
        Point.texReg = texReg;
        Point.RADIUS = (float) texReg.getRegionWidth() / 2;
        Point.BOUND_SIZE = 3.5f * texReg.getRegionWidth();
        Point.COLOR = Color.WHITE;
        Point.globalPos = globalPos;

        final float circleBoundSize = 2 * circleRadius + Point.BOUND_SIZE;
        boundRect = new Rectangle(0, 0, circleBoundSize, circleBoundSize);
        globalPos.set(Gdx.graphics.getWidth()/2, Gdx.graphics.getHeight()/2);
        boundRect.setCenter(globalPos);
    }

    /** Load saving game */
    public PointsArray(TextureRegion texReg, float circleRadius) {
        init(texReg, circleRadius);
        GameSaving.Load game = new GameSaving.Load();
        players = game.loadPlayerList();
        Connection.players = players;
        points = game.loadPoints();
        Connection.points = points;
        connections = game.loadConnections();
        MAX_CONNECTIONS = game.loadMaxConnections();

        Screen.ClearColor(players.current.color);

        for (Point p : points) {
            p.connections = new Array<Connection>(MAX_CONNECTIONS);
            for (Connection c : connections)
                if (c.containsPoint(p)) p.connections.add(c);
        }

        initAnimGroupPlayer(points.length);
    }

    public PointsArray(TextureRegion texReg, int arraySize, float circleRadius, PlayersList players) {
        init(texReg, circleRadius);
        Connection.players = players;
        this.players = players;
        Screen.ClearColor(players.current.color);

        points = new Point[arraySize];
        Connection.points = points;
        // Original = (Math.round(.5f + N/2f) - 1) * N + (1 - N % 2) * N/2;
        MAX_CONNECTIONS = (int)(arraySize/2f-.5f) * arraySize + (1-arraySize%2) * arraySize/2;
        connections = new Array<Connection>(true, MAX_CONNECTIONS);

        final float rot = random(0, PI2);
        final float step = PI2/arraySize;
        Point.BOUND_SIZE = Math.min(Point.BOUND_SIZE, .75f * step * circleRadius);
        final float minAngle = (float) (2 * Math.asin(Point.BOUND_SIZE / (2 * circleRadius)));
        for (byte i = 0; i < arraySize; ++i) {
            final float t = i * step;
            final float ra = rot + random(t+minAngle, t+step);
            points[i] = new Point(i, circleRadius * sin(ra), circleRadius * cos(ra), arraySize-1);
        }

        initAnimGroupPlayer(arraySize);
    }

    private void initAnimGroupPlayer(int arraySize) {
        animGroupPlayer = new AnimGroupPlayer(arraySize);
        animGroupPlayer.onAnimFinish = new Runnable() {
            public void run() {
                for (Point p : points) p.onMoveAnimFinish();
                animGroupPlayer = null;
            }
        };
        for (Point p : points) animGroupPlayer.add(p.moveAnim);
    }

    private void addConnection(Point p0, Point p1) {
        Connection c = new Connection(p0, p1, players.current);
        connections.add(c);
        p0.connections.add(c);
        p1.connections.add(c);
    }

    @Override
    public void draw(Batch batch) {
        for (Drawable p : points) p.draw(batch);
    }

    @Override
    public void draw(ShapeRenderer draw) {
        for (Connection c : connections) {
            draw.setColor(c.player.colorLine);
            draw.line(c.p0.pos, c.p1.pos);
        }
    }

    @Override
    public void debugDraw(ShapeRenderer draw) {
        super.debugDraw(draw);
        for (Drawable p : points) p.debugDraw(draw);
    }

    public void animStart() {
        animGroupPlayer.start();
    }

    public void addToInputMultiplexer(InputMultiplexer im) {
        im.addProcessor(new Input());
    }

    public void saveGame() {
        GameSaving.save(players, points, connections, MAX_CONNECTIONS);
    }
}