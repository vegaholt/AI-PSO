package com.ui;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.broken_e.ui.BaseScreen;
import com.broken_e.ui.UiApp;

public class PsoMainUI extends UiApp {
    @Override
    protected String atlasPath() {
        return "atlas.atlas";
    }

    @Override
    protected String skinPath() {
        return null;
    }
    @Override
    protected void styleSkin(Skin skin, TextureAtlas atlas) {
        new Style().styleSkin(skin, atlas);
    }

    @Override
    protected BaseScreen getFirstScreen() {
        return new MainScreen(this);
    }

}

