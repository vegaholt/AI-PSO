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

    public KnappSackActor(TextureAtlas atlas, KnapSackSwarm swarmType) {
        dot = new Sprite(atlas.findRegion("whitedot"));
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
        double weight = 0, value = 0;

        outer:
        for (int y = 0; y < steps; y++) {
            for (int x = 0; x < steps; x++) {
                if (swarm.bestPosition.getAxis(index)) {
                    weight += type.packages.get(index).getWeight();
                    value += type.packages.get(index).getValue();
                }

                dot.setPosition(x * stepWidth+20, y * stepHeight+20);
                float color = 0;
                for (Particle<Boolean> particle : swarm.particles) {
                    if (particle.getPosition().getAxis(index)) color++;
                }
                dot.setColor(new Color(1.0f - color / swarm.particles.size(), color / swarm.particles.size(), 0.0f, 1.0f));
                dot.draw(batch);
                index++;
                if (index == swarm.dimensions) {
                    break outer;
                }
            }
        }
    }
}
