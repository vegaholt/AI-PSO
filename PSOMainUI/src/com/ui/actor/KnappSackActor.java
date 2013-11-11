package com.ui.actor;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import pso.Particle;
import pso.Swarm;
import pso.SwarmTypes.KnapSackSwarm;

public class KnappSackActor extends Group {
    private final KnapSackSwarm type;
    private final Swarm<Boolean> swarm;
    private final Sprite dot;
    private final Sprite ring;

    public KnappSackActor(TextureAtlas atlas, KnapSackSwarm swarmType) {
        dot = new Sprite(atlas.findRegion("whitedot"));
        ring = new Sprite(atlas.findRegion("whitering"));
        type = swarmType;
        swarm = swarmType.getSwarm();
    }

    @Override
    public boolean notify(Event event, boolean capture) {
        return true;
    }

    @Override
    public void draw(SpriteBatch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);    //To change body of overridden methods use File | Settings | File Templates. @Override
        //super.draw(batch, parentAlpha);

        int steps = (int) Math.ceil(Math.sqrt(type.packages.size()));
        float stepWidth = (Gdx.graphics.getWidth()) / steps;
        float stepHeight = (Gdx.graphics.getHeight()-40) / steps;
        int index = 0;
        float xPos,yPos;
        outer:
        for (int y = 0; y < steps; y++) {
            xPos = 0;
            yPos = y * stepHeight +20;
            for (int x = 0; x < steps; x++) {

               xPos += stepWidth;
                dot.setPosition(xPos+3,yPos+3);
                ring.setPosition(xPos,yPos);
                float color = 0;
                for (Particle<Boolean> particle : swarm.particles) {
                    if (particle.getPosition().getAxis(index)) color++;
                }
                color = color / swarm.particles.size();
                ring.setColor(new Color(1-color, color, 0.0f, 1.0f));
                ring.draw(batch);
                dot.setScale(0.8f, 0.8f);
                dot.setColor(swarm.bestPosition.getAxis(index) ? new Color(0, 1, 0, 1) : new Color(1, 0, 0, 1));
                dot.draw(batch);
                index++;
                if (index == swarm.dimensions) {
                    break outer;
                }
            }

        }
    }
}
