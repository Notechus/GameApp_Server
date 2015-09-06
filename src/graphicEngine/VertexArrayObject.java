package graphicEngine;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;

import static utils.Utilities.*;

/**
 *
 * @author Notechus
 */
public class VertexArrayObject {

    private int vaoID;
    private int vboID;
    private int iboID;

    public static final int VERTEX_ATTRIB = 0;
    public static final int TCOORD_ATTRIB = 1;

    /**
     *
     * @param vertices
     * @param indices
     */
    public VertexArrayObject(float[] vertices, byte[] indices) {
        createArrayObject(vertices, indices);
    }

    public void createArrayObject(float[] vertices, byte[] indices) {
        vaoID = glGenVertexArrays();
        glBindVertexArray(vaoID);

        createVerticesBuffer(vertices);
        createIndicesBuffer(indices);

        glBindVertexArray(0);
    }

    private void createVerticesBuffer(float[] vertices) {
        vboID = glGenBuffers();
        glBindBuffer(GL_ARRAY_BUFFER, vboID);
        glBufferData(GL_ARRAY_BUFFER, createFloatBuffer(vertices), GL_STATIC_DRAW);
        glVertexAttribPointer(VERTEX_ATTRIB, 3, GL_FLOAT, false, 0, 0);
        glBindBuffer(GL_ARRAY_BUFFER, 0);
    }

    private void createIndicesBuffer(byte[] indices) {
        iboID = glGenBuffers();
        glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, iboID);
        glBufferData(GL_ELEMENT_ARRAY_BUFFER, createByteBuffer(indices), GL_STATIC_DRAW);
    }

    public int getVaoID() {
        return this.vaoID;
    }

}
