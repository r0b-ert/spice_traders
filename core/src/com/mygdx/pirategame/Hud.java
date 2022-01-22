package com.mygdx.pirategame;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Disposable;
import com.badlogic.gdx.utils.viewport.ExtendViewport;
import com.badlogic.gdx.utils.viewport.Viewport;


public class Hud implements Disposable {
    public Stage stage;
    private Viewport viewport;

    private Integer worldTimer;
    private float timeCount;
    private Integer score;
    private static Integer health;
    private Texture minimap;
    private Texture hp;

    private Label timeLabel;
    private Label scoreLabel;
    private static Label healthLabel;
    private static Label coinLabel;
    private static Integer coins;
    private Image minimapImg;
    private Image hpImg;

    public Hud(SpriteBatch sb) {
        worldTimer = 0;
        health = 100;
        score = 0;
        coins = 0;
        minimap = new Texture("minimap.png");
        hp = new Texture("hp.png");
        minimapImg = new Image(minimap);
        hpImg = new Image(hp);

        viewport = new ExtendViewport(PirateGame.WIDTH, PirateGame.HEIGHT, new OrthographicCamera());
        stage = new Stage(viewport, sb);

        Table table1 = new Table();
        Table table2 = new Table();
        table1.bottom();
        table1.setFillParent(true);
        table2.bottom();
        table2.setFillParent(true);

        timeLabel = new Label(String.format("%03d", worldTimer), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        timeLabel.setFontScale(2f);
        scoreLabel = new Label(String.format("%03d", score), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        scoreLabel.setFontScale(2f);
        healthLabel = new Label(String.format("%03d", health), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        healthLabel.setFontScale(2f);
        coinLabel = new Label(String.format("%03d", coins), new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        coinLabel.setFontScale(2f);

        table2.add(hpImg).width(64).height(64).padBottom(15).padLeft(30);
        table1.add(healthLabel).padBottom(30).padLeft(37).center();
        table1.add(timeLabel).expandX().padBottom(15);
        table1.add(coinLabel).expandX().padBottom(15);
        table1.add(scoreLabel).expandX().padBottom(15);
        table2.add(minimapImg).width(64).height(64).padBottom(15).expandX().padLeft(1100);
        stage.addActor(table2);
        stage.addActor(table1);
    }

    public void update(float dt) {
        timeCount += dt;
        if(timeCount >= 1) {
            worldTimer += 1;
            timeLabel.setText(String.format("%03d", worldTimer));
            if(health != 100) {
                health += 1;
                healthLabel.setText(String.format("%02d", health));
            }
            timeCount = 0;
        }
    }

    public static void changeHealth(int value) {
        health += value;
        healthLabel.setText(String.format("%02d", health));

    }
    public static void changeCoins(int value) {
        coins += value;
        coinLabel.setText(String.format("%03d", coins / 2));
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

