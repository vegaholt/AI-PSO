package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import task1.Particle;
import task1.Position;
import task1.Swarm;

public class DisplayScreen extends BaseScreen {

    public final Swarm theSwarm;
    TextureRegion dots[] = new TextureRegion[8];
    float ratioH, ratioW;
    float halfWidth = 800, halfHeight = 450;
    Label globalBest;
    public DisplayScreen(final UiApp app, Swarm swarm) {
        super(app);

        ClickListener menuBtnListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);    //To change body of overridden methods use File | Settings | File Templates.
            }
        };

        ClickListener restartBtnListener = new ClickListener(){
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                theSwarm.initSwarm();
            }
        };
        this.theSwarm = swarm;
        swarm.initSwarm();
        for (int i = 0; i < dots.length; i++){
            dots[i] = app.atlas.findRegion("color"+i);
        }

        ratioH = (float) (1.0f * Gdx.graphics.getHeight() / swarm.region);
        ratioW = (float) (1.0f * Gdx.graphics.getWidth() / swarm.region);
        //swarm.run();
        TextButton menuBtn = createSmallBtn("Show Menu");
        TextButton restartBtn = createSmallBtn("Restart");

        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
        //mainTable.add(menuBtn);
        //mainTable.add(restartBtn);
    }

    private TextButton createSmallBtn(String label){
        return new TextButton(label, app.skin, "small");
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
        for (Particle p : theSwarm.particles) {
            Position pos = p.getPosition();
            if (theSwarm.dimensions > 1) {
                y = (float) p.getPosition().getPosition(1) * ratioH;
            }
            batch.draw(dots[i%dots.length], (float) p.getPosition().getPosition(0) * ratioW + halfWidth, y+halfHeight);
            i++;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);    //To change body of overridden methods use File | Settings | File Templates.
        theSwarm.updateParticles();
    }

}
