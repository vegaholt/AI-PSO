package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import task1.Particle;
import task1.Position;
import task1.Swarm;

public class DisplayScreen extends BaseScreen {

    final Swarm swarm;
    TextureRegion dot;
    float ratioH, ratioW;

    public DisplayScreen(final UiApp app, Swarm swarm) {
        super(app);
        this.swarm = swarm;
        swarm.initSwarm();
        dot = app.atlas.findRegion("colorNode0");
        ratioH = (float) (1.0f * Gdx.graphics.getHeight() / swarm.region);
        ratioW = (float) (1.0f * Gdx.graphics.getWidth() / swarm.region);
        //swarm.run();
    }

    @Override
    public void onBackPress() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);    //To change body of overridden methods use File | Settings | File Templates.oo
        float y = Gdx.graphics.getHeight() / 2.0f;
        for (Particle p : swarm.swarm) {
            Position pos = p.getPosition();
            if (swarm.dimensions > 1) {
                y = (float) p.getPosition().getPosition(1) * ratioH;
            }
            batch.draw(dot, (float) p.getPosition().getPosition(0) * ratioW, y);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);    //To change body of overridden methods use File | Settings | File Templates.
        swarm.updateParticles();
    }

}
