package gameEngine;

import graphicEngine.VertexArrayObject;
import input.KeyboardInput;
import math.Vector3f;
import networking.Input;
import static org.lwjgl.glfw.GLFW.*;

/**
 *
 * @author Notechus
 */
public class Paddle extends GameObject {

    private VertexArrayObject vao;

    private Vector3f position;

    //server-side
    private Input input;

    private float[] vertices = {
        0.0f, 0.25f, 0f,
        0.0f, 0.0f, 0f,
        0.05f, 0.0f, 0f,
        0.05f, 0.25f, 0f
    };

    private byte[] indices = new byte[]{
        0, 1, 2,
        2, 3, 0
    };

    public Paddle() {
        this.setCount(indices.length);
        this.position = new Vector3f();
        vao = new VertexArrayObject(this.vertices, this.indices);
        this.setVaoID(vao.getVaoID());
        this.input = new Input();
    }

    public void update() {

        if (input.isUp() && position.getY() <= 0.73f) {
            position.setY(position.getY() + 0.01f);
        }
        if (input.isDown() && position.getY() >= -0.99f) {
            position.setY(position.getY() - 0.01f);
        }
        if (input.isRight() && position.getX() <= 0.99f) {
            position.setX(position.getY() + 0.01f);
        }
        if (input.isLeft() && position.getX() >= -0.99f) {
            position.setX(position.getX() - 0.01f);
        }
    }

    public Vector3f getPosition() {
        return position;
    }

    public void setPosition(Vector3f position) {
        this.position = position;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }
}
