package graphicEngine;

/**
 *
 * @author Notechus
 */
public class ShaderManager {

    private static Shader shader1;

    public static void loadAll() {
        shader1 = new Shader("src/shaders/shader_pong_vertex.glsl", "src/shaders/shader_pong_fragment.glsl");
    }

    public Shader getShader1() {
        return shader1;
    }

    public void setShader1(Shader shader1) {
        ShaderManager.shader1 = shader1;
    }

}
