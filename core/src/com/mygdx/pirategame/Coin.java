package com.mygdx.pirategame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;

public class Coin extends Entity {
    private float stateTime;
    private Texture coin;
    private boolean setToDestroyed;
    private boolean destroyed;
    Sound coinPickup;

    public Coin(GameScreen screen, float x, float y) {
        super(screen, x, y);
        coin = new Texture("coin.png");
        setBounds(0,0,48 / PirateGame.PPM, 48 / PirateGame.PPM);
        setRegion(coin);
        setOrigin(24 / PirateGame.PPM,24 / PirateGame.PPM);
        coinPickup = Gdx.audio.newSound(Gdx.files.internal("coin-pickup.mp3"));
    }

    public void update(float dt) {
        stateTime += dt;
        if(setToDestroyed && !destroyed) {
            world.destroyBody(b2body);
            destroyed = true;
        }
        else if(!destroyed) {
            setPosition(b2body.getPosition().x - getWidth() / 2f, b2body.getPosition().y - getHeight() / 2f);
        }
    }

    @Override
    protected void defineEntity() {
        BodyDef bdef = new BodyDef();
        bdef.position.set(getX(), getY());
        bdef.type = BodyDef.BodyType.DynamicBody;
        b2body = world.createBody(bdef);

        FixtureDef fdef = new FixtureDef();
        CircleShape shape = new CircleShape();
        shape.setRadius(24 / PirateGame.PPM);
        // setting BIT identifier
        fdef.filter.categoryBits = PirateGame.COIN_BIT;
        // determining what this BIT can collide with
        fdef.filter.maskBits = PirateGame.DEFAULT_BIT | PirateGame.PLAYER_BIT | PirateGame.ISLAND_BIT | PirateGame.ENEMY_BIT;
        fdef.shape = shape;
        fdef.isSensor = true;
        b2body.createFixture(fdef).setUserData(this);
    }

    @Override
    public void entityContact() {
        Hud.changeCoins(1);
        setToDestroyed = true;
        Gdx.app.log("coin", "collision");
        coinPickup.play();

    }

    public void draw(Batch batch) {
        if(!destroyed) {
            super.draw(batch);
        }
    }
}
