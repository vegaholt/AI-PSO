package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import com.ui.actor.KnappSackActor;
import com.ui.actor.SimpleSwarmActor;
import pso.*;
import pso.SwarmSettings;
import pso.SwarmTypes.KnapSackSwarm;
import pso.SwarmTypes.SimpleSwarm;

public class DisplayScreen extends BaseScreen {
    public final Swarm theSwarm;
    public SwarmType swarmType;
    final TextButton menuBtn, restartBtn;
    Group swarmActor;
    float ratioH, ratioW, scale;
    Label swarmStats, iterationLabel;
    int iterationCount;

    public DisplayScreen(final UiApp app, SwarmSettings presets) {
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
                restartSwarm();
                restartBtn.setChecked(false);
            }
        };
        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));

        if (presets.type == pso.SwarmSettings.TypeOfSwarm.KNAPSACK) {
            swarmType = new KnapSackSwarm(presets,Gdx.files.internal("packages.txt").read());
            swarmActor = new KnappSackActor(app.atlas, (KnapSackSwarm) swarmType);
        } else {
            swarmType = new SimpleSwarm(presets);
            swarmActor = new SimpleSwarmActor(app.atlas, (SimpleSwarm) swarmType);
        }

        theSwarm = swarmType.getSwarm();
        this.addActor(swarmActor);
        restartSwarm();

        //Adds buttons and stat label
        menuBtn = createSmallBtn("Show Menu", menuBtnListener, Gdx.graphics.getWidth() - 120, 20);
        restartBtn = createSmallBtn("Restart", restartBtnListener, Gdx.graphics.getWidth() - 190, 20);
        this.addActor(menuBtn);
        this.addActor(restartBtn);

        swarmStats = new Label("", app.skin, "small", Color.WHITE);
        swarmStats.setPosition(30, Gdx.graphics.getHeight() - 30);
        this.addActor(swarmStats);

        iterationLabel = new Label("", app.skin, "small", Color.WHITE);
        iterationLabel.setPosition(30, 30);
        this.addActor(iterationLabel);
    }

    private void restartSwarm() {
        iterationCount = 0;
        theSwarm.initSwarm();
        swarmActor.notify(new Event(), true);
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
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (theSwarm.bestPosition.getFitness() <= theSwarm.acceptanceValue || theSwarm.iterations == iterationCount) {
            swarmStats.setText(swarmType.getCurrentStats());
        } else {
            theSwarm.updateParticles();
            iterationCount++;
        }
        if(iterationCount%10 == 0){
            swarmStats.setText(swarmType.getCurrentStats());
        }
        iterationLabel.setText("Iteration:" + iterationCount);

//        swarmStats.setText(String.format("Global Best:%.6f", theSwarm.bestPosition.getFitness()));
    }


}
