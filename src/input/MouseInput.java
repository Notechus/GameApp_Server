package input;

import static org.lwjgl.glfw.GLFW.*;
import org.lwjgl.glfw.GLFWMouseButtonCallback;

/**
 *
 * @author Notechus
 */
public class MouseInput extends GLFWMouseButtonCallback {

    public static boolean[] buttons = new boolean[12];

    @Override
    public void invoke(long window, int button, int action, int mods) {
        buttons[button] = action != GLFW_RELEASE;
    }

    public static boolean isKeyDown(int keycode) {
        return buttons[keycode];
    }

}
