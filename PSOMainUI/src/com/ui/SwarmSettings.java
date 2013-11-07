package com.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import pso.Swarm;

public class SwarmSettings extends BaseScreen {

    private final SliderData[] slidersData = new SliderData[]{
            new SliderData("Particles", 10, 20000, 50, 100),
            new SliderData("Region", 100, 20000, 100, 1000),
            new SliderData("Inertia Start", 1, 100, 1, 95),
            new SliderData("Inertia End", 1, 100, 1, 90),
            new SliderData("Local Weight", 0, 100, 1, 1),
            new SliderData("Global Weight", 0, 100, 1, 4),
            new SliderData("Iterations", 0, 100000, 100, 1000),
            new SliderData("Acceptance", 1, 1000, 10, 10),
            new SliderData("Neighbours", 0, 100, 1, 3)
    };

    public SwarmSettings(final UiApp app) {
        super(app);
        final Slider[] sliders = new Slider[slidersData.length];
        final Label[] labels = new Label[slidersData.length];
        int slideWidth = 400;


        ClickListener startBtnListener = new

                ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        int i = 0;
                        int particles = (int) sliders[i++].getValue();
                        double region = (double) sliders[i++].getValue();
                        double ineStart = (double) sliders[i++].getValue() / 100.0;
                        double ineEnd = (double) sliders[i++].getValue() / 100.0;
                        double weight1 = (sliders[i++].getValue() / 100.0) * 2;
                        double weight2 = (sliders[i++].getValue() / 100.0) * 2;
                        int iterations = (int) sliders[i++].getValue();
                        double accept = sliders[i++].getValue() / 100000;
                        int neigh = (int) sliders[i++].getValue();

                        System.out.printf("Swarm inti Particles:%d Region:%f WeightStart:%f  WeightEnd:%f  LocalW:%f GlobalW:%f  Acceptance:%f  Neigh:%d",
                                particles, region, ineStart, ineEnd, weight1, weight2, accept, neigh);
                        Swarm swarm = new Swarm(particles, 2, region, ineStart, ineEnd, weight1, weight2, neigh, iterations, accept);

                        app.switchScreens(new DisplayScreen(app, swarm));
                    }
                };
        ChangeListener changeListener = new

                ChangeListener() {

                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String[] s = actor.getName().split("-");
                        int index = Integer.parseInt(s[1]);

                        if ("" + sliders[index].getValue() == labels[index].getText()) return;

                        labels[index].setText("" + sliders[index].getValue());
                    }
                };

        TextButton startBtn = new TextButton("Next", app.skin);
        startBtn.addListener(startBtnListener);
        startBtn.setColor(app.skin.getColor("green"));

        TextButton fastMode = new TextButton("Fast Mode", app.skin);
        fastMode.setColor(app.skin.getColor("blue"));

        TextButton runSimulations = new TextButton("20 simulations", app.skin);
        runSimulations.setColor(app.skin.getColor("blue"));


        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
        mainTable.add(Style.label("Swarm settings", Color.WHITE, app)).colspan(6);
        mainTable.row();

        for (int i = 0; i < slidersData.length; i++) {
            sliders[i] = new Slider(slidersData[i].min, slidersData[i].max, slidersData[i].step, false, app.skin);
            sliders[i].setName("slider-" + i);
            sliders[i].setValue(slidersData[i].value);
            sliders[i].addListener(changeListener);

            labels[i] = new Label("" + sliders[i].getValue(), app.skin);
            labels[i].setName("text-" + i);

            mainTable.add(Style.label(slidersData[i].label, Color.WHITE, app)).align(Align.right);
            mainTable.add(sliders[i]).width(slideWidth).colspan(3);
            mainTable.add(labels[i]).width(150);
            mainTable.row();
        }
        mainTable.add(Style.label("Simulation settings", Color.WHITE, app)).colspan(6);
        mainTable.row();
        mainTable.add(fastMode).width(300).height(60).colspan(3);
        mainTable.add(runSimulations).width(300).height(60).colspan(3);
        mainTable.row();
        mainTable.add(startBtn).width(400).height(80).colspan(6);

    }

    @Override
    public void onBackPress() {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    class SliderData {

        public String label;
        public float min;
        public float max;
        public float step;
        public float value;

        public SliderData(String label, float min, float max, float step, float value) {
            super();
            this.label = label;
            this.min = min;
            this.max = max;
            this.step = step;
            this.value = value;
        }
    }
}
