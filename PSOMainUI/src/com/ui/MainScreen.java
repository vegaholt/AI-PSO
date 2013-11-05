package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;
import task1.Swarm;

public class MainScreen extends BaseScreen {
    private static final int TASK_WIDTH = 100;

    //String[][] tasks = new String[4][3]{
    //    {"1D circle","10 simulations","2D"},
    //    {"1D Nearest neighbors", "2D Nearest Neighbours", "Intertia Weight"},
    //    {"Knap Sack 1 0", "Weighted Knape Sack", "Decresing Weight Knap Sack"},
    //    {"Knap Sack with Volum"}
    //}

    public MainScreen(final UiApp app) {
        super(app);

        ClickListener taskListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);

            }
        };
        ClickListener startBtnListener = new

                ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        app.switchScreens(new DisplayScreen(app, new Swarm(10000, 2, 1000, 0.99, 0.99, 0.01, 0.01,
                                1000,
                                0.001)));
                    }
                };

        TextButton task1Btn = createButton("Task 1", taskListener);
        TextButton task2Btn = createButton("Task 2", taskListener);
        TextButton task3Btn = createButton("Task 2", taskListener);
        TextButton task4Btn = createButton("Task 2", taskListener);

        TextButton startBtn = new TextButton("Start Simulation", app.skin);
        startBtn.addListener(startBtnListener);
        startBtn.setColor(app.skin.getColor("green"));

        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
        mainTable.add(Style.label("Select puzzel to solve", Color.WHITE, app)).colspan(4);
        mainTable.row();
        mainTable.add(task1Btn).width(TASK_WIDTH);
        mainTable.add(task2Btn).width(TASK_WIDTH);
        mainTable.add(task3Btn).width(TASK_WIDTH);
        mainTable.add(task4Btn).width(TASK_WIDTH);
        mainTable.row();
        mainTable.add(startBtn).width(350).height(80).colspan(4);
    }

    private TextButton createButton(String label, ClickListener listener) {
        TextButton btn = new TextButton(label, app.skin);
        btn.setColor(app.skin.getColor("blue"));
        btn.addListener(listener);

        return btn;
    }

    @Override
    public void onBackPress() {
        Gdx.app.exit();
    }

}
