package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;

public class MainScreen extends BaseScreen {


    public MainScreen(final UiApp app) {
        super(app);

        TextButton task1Btn = new TextButton("Task 1",app.skin);
        task1Btn.setColor(app.skin.getColor("blue"));
        TextButton task2Btn = new TextButton("Task 1",app.skin);
        task2Btn.setColor(app.skin.getColor("blue"));
        TextButton task3Btn = new TextButton("Task 1",app.skin);
        task3Btn.setColor(app.skin.getColor("blue"));
        TextButton task4Btn = new TextButton("Task 1",app.skin);
        task4Btn.setColor(app.skin.getColor("blue"));

        mainTable.defaults().pad(6f);
        mainTable.setBackground(app.skin.getDrawable("window1"));
        mainTable.add(Style.label("Select puzzel to solve", Color.WHITE, app)).colspan(6);
        mainTable.row();
        mainTable.add(task1Btn);
        mainTable.add(task2Btn);
        mainTable.add(task3Btn);
        mainTable.add(task4Btn);
    }

    @Override
    public void onBackPress() {
        Gdx.app.exit();
    }

}
