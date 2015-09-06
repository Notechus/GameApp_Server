package networking;

import java.io.Serializable;

/**
 *
 * @author notechus
 */
public class Packet implements Serializable {

    private static final long serialVersionUID = 1L;
    private String type;
    private Input input;

    public Packet() {
        this("no type", new Input());
    }

    public Packet(String type_, Input input_) {
        this.type = type_;
        this.input = input_;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Input getInput() {
        return input;
    }

    public void setInput(Input input) {
        this.input = input;
    }

    @Override
    public String toString() {
        return "Type = " + getType() + " Input = " + getInput();
    }
}
