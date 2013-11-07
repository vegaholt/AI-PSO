package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import pso.Particle;
import pso.Swarm;

public class DisplayScreen extends BaseScreen {

    public final Swarm<Float> theSwarm;
    final ShapeRenderer shape;
    final TextButton menuBtn, restartBtn;
    TextureRegion dots[] = new TextureRegion[11];
    float ratioH, ratioW, scale;
    Label globalBest, updatesPrSecond;
    SwarmThread swarmThread;
    ParticleSprite[] sprites;

    public DisplayScreen(final UiApp app, Swarm<Float> swarm) {
        super(app);
        shape = new ShapeRenderer();
        ClickListener menuBtnListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
                app.switchScreens(new MainScreen(app));
                //((TextButton)event.getTarget()).setChecked(false);
            }
        };

        ClickListener restartBtnListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                theSwarm.initSwarm();
                for (int i = 0; i < theSwarm.particles.size(); i++) {
                    sprites[i].particle = theSwarm.particles.get(i);
                }
                //swarmThread.startSimulation(1);
                restartBtn.setChecked(false);
            }
        };
        ratioH = (float) (1.0f * Gdx.graphics.getHeight() / swarm.region);
        ratioW = (float) (1.0f * Gdx.graphics.getWidth() / swarm.region);
        this.theSwarm = swarm;
        swarm.initSwarm();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = app.atlas.findRegion("color" + i);
        }
        sprites = new ParticleSprite[swarm.swarmSize];
        for (int i = 0; i < sprites.length; i++) {
            sprites[i] = new ParticleSprite(dots[i % dots.length], swarm.particles.get(i), ratioW, ratioH);
        }


        scale = (float) Math.min(1, Math.max(ratioW, 0.2));
        //swarm.run();

        menuBtn = createSmallBtn("Show Menu", menuBtnListener, Gdx.graphics.getWidth() - 180, 20);
        restartBtn = createSmallBtn("Restart", restartBtnListener, Gdx.graphics.getWidth() - 300, 20);
        this.addActor(menuBtn);
        this.addActor(restartBtn);
        globalBest = new Label("Global Best:", app.skin, "small", Color.WHITE);
        globalBest.setPosition(30, Gdx.graphics.getHeight() - 50);
        this.addActor(globalBest);

        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
        // mainTable.add(menuBtn);
        // mainTable.add(restartBtn);
        //this.swarmThread = new SwarmThread(this.theSwarm);
        //Thread thread = new Thread(swarmThread);
        //swarmThread.startSimulation(1);
        //thread.start();
    }

    private TextButton createSmallBtn(String label, ClickListener listener, int x, int y) {
        TextButton btn = new TextButton(label, app.skin, "small");
        btn.setColor(app.skin.getColor("blue"));
        btn.setPosition(x, y);
        btn.addListener(listener);
        return btn;
    }

    @Override
    public void onBackPress() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);    //To change body of overridden methods use File | Settings | File Templates.oo
        batch.setColor(Color.WHITE);
        batch.end();

        shape.setColor(Color.LIGHT_GRAY);
        shape.begin(ShapeRenderer.ShapeType.Line);
        shape.line(Gdx.graphics.getWidth()/2.0f, 0, Gdx.graphics.getWidth()/2.0f, Gdx.graphics.getHeight());
        shape.line(0, Gdx.graphics.getHeight()/2.0f, Gdx.graphics.getWidth(), Gdx.graphics.getHeight()/2.0f);
        shape.end();
        batch.begin();
        for (ParticleSprite sprite : sprites) {
            sprite.draw(batch);
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);
        theSwarm.updateParticles();
        globalBest.setText(String.format("Global Best:%.6f", theSwarm.bestPosition.getFitness()));
    }

    class ParticleSprite extends Sprite {
        public Particle<Float> particle;
        private float ratioW, ratioH;

        public ParticleSprite(TextureRegion region, Particle particle, float ratioW, float ratioH) {
            super(region);
            this.particle = particle;
            this.ratioH = ratioH;
            this.ratioW = ratioW;
            this.setOrigin(5,5);
        }

        @Override
        public void draw(SpriteBatch spriteBatch) {
            this.setPosition((float) particle.getPosition().getPosition(0) * ratioW + Gdx.graphics.getWidth() / 2.0f - 5.0f,
                    (float) particle.getPosition().getPosition(1) * ratioW + Gdx.graphics.getHeight() / 2.0f - 5.0f);

            float deltaX = (float)particle.getVelocity().getVelocity(0),
                    deltaY = (float)particle.getVelocity().getVelocity(1);
            float length = MathUtils.clamp((float) Math.sqrt(deltaX * deltaX + deltaY * deltaY) * 0.85f, 0.4f, 12);
            this.setRotation(MathUtils.atan2(deltaY,deltaX) * MathUtils.radiansToDegrees);
            this.setScale(length, 0.4f);

            super.draw(spriteBatch);    //To change body of overridden methods use File | Settings | File Templates.
        }
    }
}
