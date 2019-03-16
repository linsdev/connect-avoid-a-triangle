package com.lins.connect.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.lins.connect.Colour;
import com.lins.anim.AnimPlayer;
import com.lins.anim.BackColorAnim;

public class PlayersList implements Json.Serializable {
    public Player current;
    private Player player1, player2;
    private AnimPlayer animTask;
    private BackColorAnim backColorAnim;

    public PlayersList() {}

    public PlayersList(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        //current = random.nextBoolean() ? player1 : player2;
        current = player1;
        initAnim();
    }

    private void initAnim() {
        backColorAnim = new BackColorAnim(.004f, current.colorLine, current.color);
        animTask = new AnimPlayer(backColorAnim);
    }

    public PlayersList random() {
        player1 = new Player(1, Colour.random());
        player2 = new Player(2);
        do player2.setColor(Colour.random());
        while (player1.color == player2.color);
        current = player1;
        initAnim();
        return this;
    }

    public void next() {
        final Color prevColor = current.color;
        current = getOtherPlayer();
        backColorAnim.reset(prevColor, current.color);
        animTask.start();
    }

    public Player getOtherPlayer() {
        return (current == player1) ? player2 : player1;
    }

    public Color mixPlayersColor() {
        return new Color(player1.color).lerp(player2.color, .5f);
    }

    @Override
    public void write(Json json) {
        json.writeValue("player1", player1, Player.class);
        json.writeValue("player2", player2, Player.class);
        json.writeValue("current", current.id, Integer.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        player1 = json.readValue("player1", Player.class, jsonData);
        player2 = json.readValue("player2", Player.class, jsonData);
        current = (jsonData.getInt("current") == 1) ? player1 : player2;
        initAnim();
    }

    public Player getPlayerById(int playerID) {
        return (player1.id == playerID) ? player1 : player2;
    }
}