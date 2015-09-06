package gameEngine;

import graphicEngine.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

/**
 *
 * @author Notechus
 */
public class GameObject {

    private int vaoID;
    private int count;
    private float SIZE = 1.0f;

    public GameObject() {

    }

    public void draw() {
        glBindVertexArray(this.vaoID);
        glEnableVertexAttribArray(0);
        glDrawElements(GL_TRIANGLES, count, GL_UNSIGNED_BYTE, 0);
        glDisableVertexAttribArray(0);
        glBindVertexArray(0);
    }

    public int getVaoID() {
        return vaoID;
    }

    public void setVaoID(int vaoID) {
        this.vaoID = vaoID;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public float getSIZE() {
        return SIZE;
    }

    public void setSIZE(float SIZE) {
        this.SIZE = SIZE;
    }

}
