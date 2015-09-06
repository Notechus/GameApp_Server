package math;

public class Matrix4f {

    public static final int SIZE = 4 * 4;

    public float matrix[] = new float[SIZE];

    public Matrix4f() {

    }

    public static Matrix4f identity() {
        // The identity matrix:
        // [ 1 , 0 , 0 , 0 ]
        // [ 0 , 1 , 0 , 0 ]
        // [ 0 , 0 , 1 , 0 ]
        // [ 0 , 0 , 0 , 1 ]

        Matrix4f returnMatrix = new Matrix4f();

        returnMatrix.matrix[0 + 0 * 4] = 1.0f;
        returnMatrix.matrix[1 + 1 * 4] = 1.0f;
        returnMatrix.matrix[2 + 2 * 4] = 1.0f;
        returnMatrix.matrix[3 + 3 * 4] = 1.0f;

        return returnMatrix;
    }

    public static Matrix4f translate(Vector3f vector) {
        Matrix4f returnMatrix = identity();
        returnMatrix.matrix[0 + 3 * 4] = vector.getX();
        returnMatrix.matrix[1 + 3 * 4] = vector.getY();
        returnMatrix.matrix[2 + 3 * 4] = vector.getZ();

        return returnMatrix;
    }

    public static Matrix4f rotate(float angle) {
        Matrix4f returnMatrix = identity();
        float r = (float) Math.toRadians(angle);
        float cos = (float) Math.cos(r);
        float sin = (float) Math.sin(r);

        returnMatrix.matrix[0 + 0 * 4] = cos;
        returnMatrix.matrix[1 + 0 * 4] = cos;
        returnMatrix.matrix[0 + 1 * 4] = cos;
        returnMatrix.matrix[1 + 1 * 4] = cos;

        return returnMatrix;
    }

    public Matrix4f multiply(Matrix4f mat4f) {
        Matrix4f resultMatrix = new Matrix4f();
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                float sum = 0.0f;
                for (int k = 0; k < 4; ++k) {
                    sum += this.matrix[i + k * 4] * mat4f.matrix[k + j * 4];
                }
                resultMatrix.matrix[i + j * 4] = sum;
            }
        }
        return resultMatrix;
    }
}
