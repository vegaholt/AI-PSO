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
    TextureRegion dots[] = new TextureRegion[8];
    float ratioH, ratioW;
    float halfWidth = 800, halfHeight = 450;
    public DisplayScreen(final UiApp app, Swarm swarm) {
        super(app);
        this.swarm = swarm;
        swarm.initSwarm();
        for (int i = 0; i < dots.length; i++){
            dots[i] = app.atlas.findRegion("color"+i);
        }

        ratioH = (float) (1.0f * Gdx.graphics.getHeight() / swarm.region);
        ratioW = (float) (1.0f * Gdx.graphics.getWidth() / swarm.region);
        //swarm.run();

        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
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
        for (Particle p : swarm.swarm) {
            Position pos = p.getPosition();
            if (swarm.dimensions > 1) {
                y = (float) p.getPosition().getPosition(1) * ratioH;
            }
            batch.draw(dots[i%dots.length], (float) p.getPosition().getPosition(0) * ratioW + halfWidth, y+halfHeight);
            i++;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);    //To change body of overridden methods use File | Settings | File Templates.
        swarm.updateParticles();
    }

}
