package utils;

import java.io.*;
import java.nio.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

/**
 *
 * @author Notechus
 */
public class Utilities {

    public static String loadAsString(String location) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(location));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer);
                result.append("\n");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
        return result.toString();
    }

    public static int loadShader(String filepath, int type) {
        StringBuilder result = new StringBuilder();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(filepath));
            String buffer = "";
            while ((buffer = reader.readLine()) != null) {
                result.append(buffer);
                result.append("\n");
            }
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }

        int shaderID = glCreateShader(type);
        glShaderSource(shaderID, result.toString());
        glCompileShader(shaderID);

        if (glGetShaderi(shaderID, GL_COMPILE_STATUS) == GL_FALSE) {
            System.err.println(glGetShaderInfoLog(shaderID, 500));
            System.err.println("Could not compile shader.");
            System.err.println(-1);
        }
        return shaderID;
    }

    public static ByteBuffer createByteBuffer(byte[] array) {
        ByteBuffer result = ByteBuffer.allocateDirect(array.length).order(ByteOrder.nativeOrder());
        result.put(array).flip();

        return result;
    }

    public static FloatBuffer createFloatBuffer(float[] array) {
        FloatBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asFloatBuffer();
        result.put(array).flip();

        return result;
    }

    public static IntBuffer createIntBuffer(int[] array) {
        IntBuffer result = ByteBuffer.allocateDirect(array.length << 2).order(ByteOrder.nativeOrder()).asIntBuffer();
        result.put(array).flip();

        return result;
    }
}
