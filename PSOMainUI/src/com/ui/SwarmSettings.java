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
import com.sun.org.apache.bcel.internal.generic.ALOAD;

public class SwarmSettings extends BaseScreen {

    private final Slider[] sliders;
    private final Label[] labels;
    private final SliderData[] slidersData;
    public SwarmSettings(final UiApp app, final pso.SwarmSettings presets) {
        super(app);
        slidersData = new SliderData[]{
                new SliderData("Particles", 50, 15000, 50, presets.swarmSize),
                new SliderData("Region", 1, 5000, 1, (int)presets.region),
                new SliderData("Inertia Start", 1, 100, 1, (int)(presets.inertiaWeightStart * 100), "% of 1"),
                new SliderData("Inertia End", 1, 100, 1, (int)(presets.inertiaWeightEnd * 100), "% of 1"),
                new SliderData("Local Weight", 0, 100, 1, (int)(presets.c1 * 50), "% of 2"),
                new SliderData("Global Weight", 0, 100, 1, (int)(presets.c2 * 50), "% of 2"),
                new SliderData("Iterations", 0, 10000, 200,presets.iterations),
                new SliderData("Acceptance", 1, 1000, 10, (int)(presets.acceptanceValue* 100000), "E-5"),
                new SliderData("Neighbours", 0, 100, 1, presets.neighbourCount)
        };
        sliders = new Slider[slidersData.length];
        labels = new Label[slidersData.length];
        int slideWidth = 900;


        ClickListener startBtnListener = new

                ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        int i = 0;
                        presets.swarmSize = (int) sliders[i++].getValue();
                        presets.region = (double) sliders[i++].getValue();
                        presets.inertiaWeightStart = (double) sliders[i++].getValue() / 100.0;
                        presets.inertiaWeightEnd = (double) sliders[i++].getValue() / 100.0;
                        presets.c1 = (sliders[i++].getValue() / 100.0) * 2;
                        presets.c2 = (sliders[i++].getValue() / 100.0) * 2;
                        presets.iterations = (int) sliders[i++].getValue();
                        presets.acceptanceValue = sliders[i++].getValue() / 100000;
                        presets.neighbourCount = (int) sliders[i++].getValue();

                        app.switchScreens(new DisplayScreen(app, presets));
                    }
                };
        ChangeListener changeListener = new

                ChangeListener() {

                    @Override
                    public void changed(ChangeEvent event, Actor actor) {
                        String[] s = actor.getName().split("-");
                        int index = Integer.parseInt(s[1]);
                        setSliderLabel(index);
                    }
                };

        TextButton startBtn = new TextButton("Next", app.skin);
        startBtn.addListener(startBtnListener);
        startBtn.setColor(app.skin.getColor("green"));

        TextButton backBtn = new TextButton("Back", app.skin);
        backBtn.setColor(app.skin.getColor("green"));
        backBtn.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                app.switchScreens(new MainScreen(app));
            }
        });
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
            setSliderLabel(i);
            mainTable.add(Style.label(slidersData[i].label, Color.WHITE, app)).align(Align.right);
            mainTable.add(sliders[i]).width(slideWidth).colspan(6);
            mainTable.add(labels[i]).width(150);
            mainTable.row();
        }


        mainTable.add(Style.label("Simulation settings", Color.WHITE, app)).colspan(8).align(Align.center);
        mainTable.row();

        mainTable.add(fastMode).width(300).height(60).colspan(4).align(Align.right);
        mainTable.add(runSimulations).width(300).height(60).colspan(4).align(Align.left);

        mainTable.row();
        mainTable.add(backBtn).width(300).height(80).colspan(4).align(Align.right);
        mainTable.add(startBtn).width(300).height(80).colspan(4).align(Align.left);
    }

    private void setSliderLabel(int sliderIndex) {
        labels[sliderIndex].setText(String.format("%.0f%s", sliders[sliderIndex].getValue(), slidersData[sliderIndex].append));
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
        public String append = "";

        public SliderData(String label, float min, float max, float step, float value, String append) {
            super();
            this.label = label;
            this.min = min;
            this.max = max;
            this.step = step;
            this.value = value;
            this.append = append;
        }

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
