package com.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider.SliderStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.TextField.TextFieldStyle;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.NinePatchDrawable;
import com.broken_e.ui.UiApp;

/**
 * @author trey miller
 */
public class Style {

    /**
     * used to tidy up the label adding a bit for the how to play description
     */
    public static Label label(String text, Color color, UiApp app) {
        Label label = new Label(text, app.skin);
        label.setAlignment(Align.center, Align.center);
        label.setColor(color);
        return label;
    }

    public void styleSkin(Skin skin, TextureAtlas atlas) {
        //FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("data/SansaProFont.otf"));

        BitmapFont font = new BitmapFont(Gdx.files.internal("font.fnt"));
        BitmapFont smallFont = new BitmapFont(Gdx.files.internal("smallfont.fnt"));
        //generator.dispose();

        skin.add("default", font);
        skin.add("small", smallFont);

        skin.add("white", new Color(1f, 1f, 1f, 1f));
        skin.add("lt-grey", new Color(0.7f, 0.7f, 0.7f, 1f));
        skin.add("blue", new Color(0, 155f / 256f, 213f / 256f, 1f));
        skin.add("green", new Color(113f / 256f, 186f / 256f, 14f / 256f, 1f));
        skin.add("lt-green", new Color(.6f, .9f, .6f, 1f));
        skin.add("dark-blue", new Color(.1f, .3f, 1f, 1f));
        skin.add("dark-grey", new Color(0.1f, 0.1f, 0.1f, 1.0f));

        NinePatchDrawable btn1up = new NinePatchDrawable(atlas.createPatch("button"));
        NinePatchDrawable btn1down = new NinePatchDrawable(atlas.createPatch("buttonDown"));
        NinePatch window1patch = atlas.createPatch("window");
        skin.add("btn1up", btn1up);
        skin.add("btn1down", btn1down);
        skin.add("window1", window1patch);
        //skin.add("white-pixel", atlas.findRegion("white-pixel"), TextureRegion.class);

        LabelStyle lbs = new LabelStyle();
        lbs.font = font;
        lbs.fontColor = Color.WHITE;
        skin.add("default", lbs);

        lbs = new LabelStyle();
        lbs.font = smallFont;
        lbs.fontColor = Color.WHITE;
        skin.add("small", lbs);

        TextButtonStyle tbs = new TextButtonStyle(btn1up, btn1down, btn1down, font);
        tbs.fontColor = skin.getColor("white");
        tbs.pressedOffsetX = Math.round(1f * Gdx.graphics.getDensity());
        tbs.pressedOffsetY = tbs.pressedOffsetX * -1f;
        skin.add("default", tbs);

        NinePatchDrawable sliderBg = new NinePatchDrawable(atlas.createPatch("slide-bg"));
        NinePatchDrawable knob = new NinePatchDrawable(atlas.createPatch("knob"));

        SliderStyle slider = new SliderStyle(sliderBg, knob);
        skin.add("default-horizontal", slider);

        NinePatchDrawable textBg = new NinePatchDrawable(atlas.createPatch("text-field"));
        NinePatchDrawable cursor = new NinePatchDrawable(atlas.createPatch("cursor"));
        TextFieldStyle textField = new TextFieldStyle(font, skin.getColor("white"), cursor, null, textBg);

        skin.add("default", textField);
    }
}