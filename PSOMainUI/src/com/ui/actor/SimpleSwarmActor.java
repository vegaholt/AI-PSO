package com.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import pso.Particle;
import pso.SwarmTypes.SimpleSwarm;

public class SimpleSwarmActor extends Group {
    final ShapeRenderer shape;
    ParticleSprite[] sprites;
    TextureRegion dots[] = new TextureRegion[11];
    final SimpleSwarm swarmType;

    public SimpleSwarmActor(TextureAtlas atlas, SimpleSwarm swarmType) {
        this.shape = new ShapeRenderer();
        this.swarmType = swarmType;
        for (int i = 0; i < dots.length; i++) {
            dots[i] = atlas.findRegion("color" + i);
        }
    }

    @Override
    public boolean notify(Event event, boolean capture) {
        float ratioH = (float) (1.0f * Gdx.graphics.getHeight() / swarmType.getSwarm().region);
        float ratioW = (float) (1.0f * Gdx.graphics.getWidth() / swarmType.getSwarm().region);
        sprites = new ParticleSprite[swarmType.getSwarm().swarmSize];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new ParticleSprite(dots[i % dots.length], swarmType.getSwarm().particles.get(i), ratioW, ratioH);
        }

        return true;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        batch.setColor(Color.WHITE);

        batch.end();
        shape.setColor(Color.LIGHT_GRAY);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.line(Gdx.graphics.getWidth() / 2.0f, 0, Gdx.graphics.getWidth() / 2.0f, Gdx.graphics.getHeight());
        shape.line(0, Gdx.graphics.getHeight() / 2.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight() / 2.0f);
        shape.end();
        batch.begin();
        System.out.println(sprites.length);
        for (ParticleSprite sprite : sprites) {
            sprite.draw(batch);
        }
    }

    class ParticleSprite extends Sprite {
        public Particle<Float> particle;
        private float ratioW, ratioH;
        private boolean is2D = true;

        public ParticleSprite(TextureRegion region, Particle<Float> particle, float ratioW, float ratioH) {
            super(region);
            this.particle = particle;
            this.is2D = particle.getPosition().length > 1;
            this.ratioH = ratioH;
            this.ratioW = ratioW;
            this.setOrigin(5, 5);
        }

        @Override
        public void draw(SpriteBatch spriteBatch) {

            float x = particle.getPosition().getAxis(0) * ratioW + Gdx.graphics.getWidth() / 2.0f - 5.0f, y;
            float deltaX = (float) particle.getVelocity().getAxis(0), deltaY;
            if (is2D) {
                y = particle.getPosition().getAxis(1) * ratioW + Gdx.graphics.getHeight() / 2.0f - 5.0f;
                deltaY = (float) particle.getVelocity().getAxis(1);
            } else {
                y = Gdx.graphics.getHeight() / 2;
                deltaY = 0;
            }
            this.setPosition(x, y);
            if (is2D) {
                float length = MathUtils.clamp((float) Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 0.85f, 0.4f, 12);
                this.setRotation(MathUtils.atan2(deltaY, deltaX) * MathUtils.radiansToDegrees);
                this.setScale(length, 0.4f);
            }

            super.draw(spriteBatch);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
