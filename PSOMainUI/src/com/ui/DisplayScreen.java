package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import pso.Particle;
import pso.Swarm;

public class DisplayScreen extends BaseScreen {

    public final Swarm theSwarm;
    final TextButton menuBtn, restartBtn;
    TextureRegion dots[] = new TextureRegion[11];
    float ratioH, ratioW, scale;
    float halfWidth = 800, halfHeight = 450;
    Label globalBest, updatesPrSecond;
    SwarmThread swarmThread;

    public DisplayScreen(final UiApp app, Swarm swarm) {
        super(app);

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
                //swarmThread.startSimulation(1);
                restartBtn.setChecked(false);
            }
        };
        this.theSwarm = swarm;
        swarm.initSwarm();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = app.atlas.findRegion("color" + i);
        }

        ratioH = (float) (1.0f * Gdx.graphics.getHeight() / swarm.region);
        ratioW = (float) (1.0f * Gdx.graphics.getWidth() / swarm.region);
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
        float y = 0;
        int i = 0;
        batch.setColor(Color.WHITE);
        for (int j = 0; j < theSwarm.particles.size(); j++) {
            Particle p = theSwarm.particles.get(j);
            if (theSwarm.dimensions > 1) {
                y = (float) p.getPosition().getPosition(1) * ratioH;
            }
            batch.draw(dots[i % dots.length], (float) p.getPosition().getPosition(0) * ratioW + halfWidth, y + halfHeight, 0, 0, 10, 10, scale, scale, 0);
            i++;
        }

    }

    @Override
    public void act(float delta) {
        super.act(delta);    //To change body of overridden methods use File | Settings | File Templates.
        theSwarm.updateParticles();
        globalBest.setText(String.format("Global Best:%.6f", theSwarm.bestPosition.getFitness()));
    }
}
