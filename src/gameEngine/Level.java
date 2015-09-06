package gameEngine;

import graphicEngine.ShaderManager;
import networking.Input;

/**
 *
 * @author Notechus
 */
public class Level {

    private Paddle player1;
    private Paddle player2;

    private Ball ball1;

    private ShaderManager shaderManager;

    public Level() {
        shaderManager = new ShaderManager();
        shaderManager.loadAll();
        this.ball1 = new Ball();
        this.player1 = new Paddle();
        this.player2 = new Paddle();

        //set player pos
        player1.getPosition().setX(-0.5f);
        player1.getPosition().setY(0.0f);

        player2.getPosition().setX(0.5f);
        player2.getPosition().setY(0.0f);

        //set ball pos
        ball1.getPosition().setX(0.0f);
        ball1.getPosition().setY(0.0f);
    }

    public void update() {
        ball1.update();
        player1.update();
        player2.update();
    }

    public void draw() {
        shaderManager.getShader1().start();
        shaderManager.getShader1().setUniform3f("pos", ball1.getPosition());
        ball1.draw();
        shaderManager.getShader1().stop();

        shaderManager.getShader1().start();
        shaderManager.getShader1().setUniform3f("pos", player1.getPosition());
        player1.draw();
        shaderManager.getShader1().stop();

        shaderManager.getShader1().start();
        shaderManager.getShader1().setUniform3f("pos", player2.getPosition());
        player2.draw();
        shaderManager.getShader1().stop();
    }

    public void setInput(Input input) {
        player1.setInput(input);
        player2.setInput(input);
    }

    public Input getInput(int number) {
        if (number == 1) {
            return player1.getInput();
        } else {
            return player2.getInput();
        }
    }
}
