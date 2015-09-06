package gameEngine;

import graphicEngine.VertexArrayObject;
import input.MouseInput;
import math.Vector3f;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author Notechus
 */
public class Ball extends GameObject {

    private VertexArrayObject vao;

    private Vector3f position;

    private float[] vertices = {
        0.0f, 0.05f, 0f,
        0.0f, 0.0f, 0f,
        0.05f, 0.0f, 0f,
        0.05f, 0.05f, 0f
    };

    private byte[] indices = new byte[]{
        0, 1, 2,
        2, 3, 0
    };

    public Ball() {
        this.setCount(indices.length);
        this.position = new Vector3f();
        vao = new VertexArrayObject(this.vertices, this.indices);
        this.setVaoID(vao.getVaoID());
    }

    public VertexArrayObject getVao() {
        return vao;
    }

    public void setVao(VertexArrayObject vao) {
        this.vao = vao;
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public void update() {
        if (MouseInput.isKeyDown(GLFW_MOUSE_BUTTON_1)) {
            position.setY(position.getY() + 0.01f);
        }
        if (MouseInput.isKeyDown(GLFW_MOUSE_BUTTON_2)) {
            position.setY(position.getY() - 0.01f);
        }
    }

}
