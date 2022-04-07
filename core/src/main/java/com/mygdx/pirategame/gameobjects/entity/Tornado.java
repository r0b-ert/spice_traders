package com.mygdx.pirategame.gameobjects.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;
import com.mygdx.pirategame.Hud;
import com.mygdx.pirategame.PirateGame;
import com.mygdx.pirategame.gameobjects.Player;
import com.mygdx.pirategame.screen.GameScreen;

/**
 * Tornado
 * Creates an object for each tornado
 * Extends the entity class to define tornado as an entity
 *
 *@author Robert Murphy
 *@version 1.0
 */
public class Tornado extends Entity {
    private Texture tornado;
    private Sound tornadoSound;
    private Player player;
    public Body b2bodyTornado;
    public static boolean active = false;
    private float timer;

    /**
     * Instantiates a new Tornado.
     *
     * @param screen the screen it's going onto
     * @param x      the x value to be placed at
     * @param y      the y value to be placed at
     */
    public Tornado(GameScreen screen, float x, float y) {
        super(screen, x, y);

        // Set tornado image
        tornado = new Texture("entity/tornado.png"); // CHANGE
        // Set the position and size of the tornado
        setBounds(0, 0, 144 / PirateGame.PPM, 144 / PirateGame.PPM);
        // Set the texture
        setRegion(tornado);
        // Sets origin of the tornado
        setOrigin(24 / PirateGame.PPM, 24 / PirateGame.PPM);
        tornadoSound = Gdx.audio.newSound(Gdx.files.internal("sfx_and_music/coin-pickup.mp3")); // CHANGE

        player = screen.getPlayer();

        setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
    }

    /**
     * Updates the tornado state.
     */
    public void update(float dt) {
        timer += dt;
        // Once tornado has existed for a certain amount of time it is removed
        if (timer > 40) {
            setToDestroyed = true;
        }
        /*
        if (distance > 10) {
            //Player.inTornadoRange = false;
        } else {
            System.out.println(distance);
            Player.inTornadoRange = true;
        }

         */
    }

    /**
     * Defines all the parts of the tornado physical model. Sets it up for collisions
     */
    @Override
    protected void defineEntity() {
        // set the body definition for the default body
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(getX(), getY());
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bodyDef);

        //set the body definition for the tornado body
        b2bodyTornado = world.createBody(bodyDef);

        //Sets collision boundaries
        FixtureDef fixtureDef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(30 / PirateGame.PPM);

        // setting BIT identifier
        fixtureDef.filter.categoryBits = PirateGame.TORNADO_BIT;
        // determining what this BIT can collide with
        fixtureDef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ENEMY_BIT;
        fixtureDef.shape = shape;
        fixtureDef.isSensor = true;
        b2body.createFixture(fixtureDef).setUserData(this);

        // Create fixture for larger collision box whilst coin magnet is active
        // Disabled until coin magnet power up is collected
        fixtureDef.shape.setRadius(250 / PirateGame.PPM);
        b2bodyTornado.createFixture(fixtureDef).setUserData(this);
        //b2bodyTornado.setActive(false);
    }

    /**
     * What happens when an entity collides with the tornado.
     */
    @Override
    public void entityContact() {
        //System.out.println("near");
        Player.inTornadoRange = true;
        /*
        if (active) {
            //inTornadoRange = true;
            Player.inTornadoRange = true;
            System.out.println("active");
        }

         */
    }

    /**
     * Calculates the distance from the player to the tornado
     *
     * @return The distance from the player to the tornado
     */
    public double getDistance() {
        // Position of player
        float playerX = player.b2body.getPosition().x;
        float playerY = player.b2body.getPosition().y;

        // Calculate distance from player to tornado
        double distance = Math.sqrt(Math.pow(playerX - b2body.getWorldCenter().x, 2) + Math.pow(playerY - b2body.getWorldCenter().y, 2));
        return distance;
    }

    /**
     * Draws the tornado using batch
     *
     * @param batch The batch of the program
     */
    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }
}