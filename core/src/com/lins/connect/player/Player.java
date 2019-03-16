package com.lins.connect.player;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.utils.Json;
import com.badlogic.gdx.utils.JsonValue;
import com.lins.connect.Colour;

public class Player implements Json.Serializable {
    int id;
    public int getID() { return id; }

    public Color color, colorLine;

    public Player() {}

    Player(int id) {
        this.id = id;
    }

    public Player(int id, Color color) {
        this.id = id;
        setColor(color);
    }

    void setColor(Color color) {
        this.color = color;
        this.colorLine = Colour.lighten(color);
    }

    @Override
    public void write(Json json) {
        json.writeValue("id", id, Integer.class);
        json.writeValue("color", color.toString(), String.class);
    }

    @Override
    public void read(Json json, JsonValue jsonData) {
        id = jsonData.getInt("id");
        setColor(Color.valueOf(jsonData.getString("color")));
    }
}