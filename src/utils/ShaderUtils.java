package utils;

import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL11.*;

/**
 *
 * @author Notechus
 */
public class ShaderUtils {

    private ShaderUtils() {

    }

    public static int load(String vertPath, String fragPath) {
        String vert = Utilities.loadAsString(vertPath);
        String frag = Utilities.loadAsString(fragPath);

        return create(vert, frag);
    }

    public static int create(String vert, String frag) {
        int program = glCreateProgram();
        // create shaders
        int vertID = glCreateShader(GL_VERTEX_SHADER);
        int fragID = glCreateShader(GL_FRAGMENT_SHADER);

        // get from source
        glShaderSource(vertID, vert);
        glShaderSource(fragID, frag);

        // Compile the vertex shader and check errors
        glCompileShader(vertID);
        if (glGetShaderi(vertID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile vertex shader!");
            System.err.println(glGetShaderInfoLog(vertID));
        }

        // Compile the fragment shader and check errors
        glCompileShader(fragID);
        if (glGetShaderi(fragID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println("Failed to compile fragment shader!");
            System.err.println(glGetShaderInfoLog(fragID));
        }

        // Attach shaders
        glAttachShader(program, vertID);
        glAttachShader(program, fragID);

        // Link and validate
        glLinkProgram(program);
        glValidateProgram(program);

        return program;
    }

}
