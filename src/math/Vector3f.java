package math;

public class Vector3f {

    //add cross product, dot product
    private float x;
    private float y;
    private float z;

    public Vector3f() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;
    }

    public Vector3f(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //getters and setters
    public float getX() {
        return x;
    }

    public void setX(float x) {
        this.x = x;
    }

    public float getY() {
        return y;
    }

    public void setY(float y) {
        this.y = y;
    }

    public float getZ() {
        return z;
    }

    public void setZ(float z) {
        this.z = z;
    }

    public float length() {
        return (float) Math.sqrt(this.x * this.x + this.y * this.y + this.z * this.z);
    }

    public Vector3f negate() {
        return new Vector3f(-this.x, -this.y, -this.z);
    }

    public Vector3f add(Vector3f vec) {
        this.x += vec.x;
        this.y += vec.y;
        this.z += vec.z;

        return this;
    }

    public Vector3f subtract(Vector3f vec) {
        this.x -= vec.x;
        this.y -= vec.y;
        this.z -= vec.z;

        return this;
    }

    public Vector3f normalise() {
        float length = this.length();
        this.x /= length;
        this.y /= length;
        this.z /= length;

        return this;
    }

    public Vector3f multiply(float a) {
        this.x *= a;
        this.y *= a;
        this.z *= a;

        return this;
    }

    public Vector3f zero() {
        this.x = 0.0f;
        this.y = 0.0f;
        this.z = 0.0f;

        return this;
    }
}
