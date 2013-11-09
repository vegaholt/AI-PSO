package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;

public class MainScreen extends BaseScreen {
    private static final int TASK_WIDTH = 100;
    final TextButton[] taskBtns;
    pso.SwarmSettings selectedTask;

    public MainScreen(final UiApp app) {
        super(app);

        ClickListener taskListener = new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                super.clicked(event, x, y);
                TextButton b = (TextButton) event.getListenerActor();
                int index = Integer.valueOf(b.getName());
                for (int i = 0; i < taskBtns.length; i++) {
                    if (i == index) continue;
                    taskBtns[i].setChecked(false);
                }

                selectedTask = pso.SwarmSettings.tasks[index];
            }
        };

        taskBtns = new TextButton[pso.SwarmSettings.tasks.length];
        for (int i = 0; i < pso.SwarmSettings.tasks.length; i++) {
            pso.SwarmSettings task = pso.SwarmSettings.tasks[i];
            taskBtns[i] = createButton(task.name, taskListener);
            taskBtns[i].setName("" + i);
        }
        selectedTask = pso.SwarmSettings.tasks[0];
        taskBtns[0].setChecked(true);


        ClickListener startBtnListener = new

                ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        super.clicked(event, x, y);
                        app.switchScreens(new com.ui.SwarmSettings(app, selectedTask));
                    }
                };

        TextButton startBtn = new TextButton("Next", app.skin);
        startBtn.addListener(startBtnListener);
        startBtn.setColor(app.skin.getColor("green"));

        int cols = (int) Math.ceil(Math.sqrt(pso.SwarmSettings.tasks.length));
        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
        mainTable.add(Style.label("Select Task Presets", Color.WHITE, app)).colspan(cols);
        mainTable.row();

        outer:
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < cols; j++) {
                int index = i * cols + j;
                mainTable.add(taskBtns[index]).width(350);
                if (index == pso.SwarmSettings.tasks.length - 1) {
                    break outer;
                }
            }
            mainTable.row();
        }

        mainTable.row();
        mainTable.add(startBtn).width(350).height(80).colspan(cols);
    }

    private TextButton createButton(String label, ClickListener listener) {
        TextButton btn = new TextButton(label, app.skin, "small");
        btn.setColor(app.skin.getColor("blue"));
        btn.addListener(listener);

        return btn;
    }

    @Override
    public void onBackPress() {
        Gdx.app.exit();
    }

}
