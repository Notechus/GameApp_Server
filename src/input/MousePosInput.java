package input;

import org.lwjgl.glfw.GLFWCursorPosCallback;

/**
 *
 * @author Notechus
 */
public class MousePosInput extends GLFWCursorPosCallback {

    @Override
    public void invoke(long window, double xpos, double ypos) {
        //System.out.println("X: " + xpos + " Y: " + ypos);
    }

}
