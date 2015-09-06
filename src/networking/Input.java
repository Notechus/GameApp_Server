package networking;

import java.io.Serializable;

/**
 *
 * @author Notechus
 */
public class Input implements Serializable {

    private static final long serialVersionUID = 2L;
    private boolean up = false;
    private boolean down = false;
    private boolean left = false;
    private boolean right = false;
    private boolean space = false;
    private boolean r = false;
    private boolean mousebtn1 = false;
    private boolean mousebtn2 = false;

    public Input() {

    }

    public Input(boolean[] inp) {
        up = inp[0];
        down = inp[1];
        left = inp[2];
        right = inp[3];
        space = inp[4];
        r = inp[5];
        mousebtn1 = inp[6];
        mousebtn2 = inp[7];
    }

    public boolean isUp() {
        return up;
    }

    public void setUp(boolean up) {
        this.up = up;
    }

    public boolean isDown() {
        return down;
    }

    public void setDown(boolean down) {
        this.down = down;
    }

    public boolean isLeft() {
        return left;
    }

    public void setLeft(boolean left) {
        this.left = left;
    }

    public boolean isRight() {
        return right;
    }

    public void setRight(boolean right) {
        this.right = right;
    }

    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    public boolean isR() {
        return r;
    }

    public void setR(boolean r) {
        this.r = r;
    }

    public boolean isMousebtn1() {
        return mousebtn1;
    }

    public void setMousebtn1(boolean mousebtn1) {
        this.mousebtn1 = mousebtn1;
    }

    public boolean isMousebtn2() {
        return mousebtn2;
    }

    public void setMousebtn2(boolean mousebtn2) {
        this.mousebtn2 = mousebtn2;
    }

}
