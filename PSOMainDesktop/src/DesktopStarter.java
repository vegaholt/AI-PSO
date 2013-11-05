import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.badlogic.gdx.tools.imagepacker.TexturePacker2;
import com.ui.PsoMainUI;

/**
 * Created with IntelliJ IDEA.
 * User: Nicolay
 * Date: 05.11.13
 * Time: 12:00
 * To change this template use File | Settings | File Templates.
 */

public class DesktopStarter {
    public static void main(String[] args) {
        TexturePacker2.process("../../images", "./", "atlas");
        LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
        cfg.title = "Title";
        cfg.useGL20 = true;
        cfg.width = 1600;
        cfg.height = 900;
        new LwjglApplication(new PsoMainUI(), cfg);
    }
}
