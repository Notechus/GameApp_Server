package gameApp;

import gameEngine.*;
import input.*;
import networking.*;
import utils.*;

import org.lwjgl.Sys;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;

import java.nio.ByteBuffer;

import static org.lwjgl.glfw.Callbacks.*;
import static org.lwjgl.system.MemoryUtil.*;
import static org.lwjgl.glfw.GLFW.*;

import static org.lwjgl.opengl.GL11.*;

public class Game {

    // We need to strongly reference callback instances.
    private GLFWErrorCallback errorCallback;
    private GLFWKeyCallback keyCallback;
    private GLFWMouseButtonCallback mouseButtonCallback;
    private GLFWCursorPosCallback cursorPosCallback;

    // The windowID handle
    private long windowID;
    private static boolean running;

    //Input for networking packet
    private Input input;
    private UDPServer server;

    //Timer class
    //private Timer timer;
    //Game Objects
    private Level level1;

    public void init() {

        // Setup an error callback. The default implementation
        // will print the error message in System.err.
        glfwSetErrorCallback(errorCallback = errorCallbackPrint(System.err));
        //Run UDP Client
        server = new UDPServer();
        server.start();
        //init input which will be sent
        input = new Input();

        System.out.println("Hello LWJGL " + Sys.getVersion() + "!");

        //size of window
        int WIDTH = 800;
        int HEIGHT = 600;

        //init the GLFW
        if (glfwInit() != GL_TRUE) {
            System.err.println("Error initializing GLFW");
            System.exit(1);
        }

        // Window Hints for OpenGL context
        glfwWindowHint(GLFW_SAMPLES, 4);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
        glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 3);
        glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GL_TRUE);
        glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
        glfwWindowHint(GLFW_RESIZABLE, GL_FALSE);

        windowID = glfwCreateWindow(WIDTH, HEIGHT, "Game Server", NULL, NULL);

        //we check if window was created properly
        if (windowID == NULL) {
            System.err.println("Error creating a window");
            System.exit(1);
        }

        // Setup a key callback. It will be called every time a key is pressed, repeated or released.
        //glfwSetKeyCallback(windowID, keyCallback = GLFWKeyCallback(this::glfwKeyCallback));
        glfwSetKeyCallback(windowID, keyCallback = new KeyboardInput());

        //Set mouse callback
        //glfwSetMouseButtonCallback(windowID, mouseButtonCallback = GLFWMouseButtonCallback(this::glfwMouseCallback));
        glfwSetMouseButtonCallback(windowID, mouseButtonCallback = new MouseInput());

        //set cursor callback
        glfwSetCursorPosCallback(windowID, cursorPosCallback = new MousePosInput());

        // Make the OpenGL context current
        glfwMakeContextCurrent(windowID);

        // LWJGL detects the context that is current in the current thread,
        // creates the ContextCapabilities instance and makes the OpenGL
        // bindings available for use.
        GLContext.createFromCurrent();
        System.out.println("OpenGL: " + glGetString(GL_VERSION));

        // enable vertical synchronization
        glfwSwapInterval(1);
        // Initialize GLFW. Most GLFW functions will not work before doing this.
        if (glfwInit() != GL11.GL_TRUE) {
            throw new IllegalStateException("Unable to initialize GLFW");
        }

        // Get the resolution of the primary monitor
        ByteBuffer vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());
        // Center our window
        glfwSetWindowPos(windowID,
                (GLFWvidmode.width(vidmode) - WIDTH) / 2,
                (GLFWvidmode.height(vidmode) - HEIGHT) / 2
        );

        //init game object
        level1 = new Level();
    }

    public void input() {
        input = server.getInput();
        if (input.isDown()) {
            System.out.println("down we go");
        }
        level1.setInput(input);
    }

    public void render() {
        // Clear the screen
        glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
        //glClearColor(1.0f, 1.0f, 1.0f, 1.0f); //white
        glClearColor(0.39f, 0.58f, 0.92f, 1.0f); //blue
        //draw paddle object
        level1.draw();

        glfwSwapBuffers(windowID);
    }

    public void update() {
        // Poll for window events. The key callback above will only be
        // invoked during this call.
        glfwPollEvents();

        level1.update();

        /*if (KeyboardInput.isKeyDown(GLFW_KEY_SPACE)) {
         System.out.println("you jumped");
         input.setSpace(true);
         }*/
    }

    public void loop() {

        long lastTime = System.nanoTime();
        double delta = 0.0;
        double ns = 1000000000.0 / 60.0;
        long timer = System.currentTimeMillis();
        int updates = 0;
        int frames = 0;

        while (glfwWindowShouldClose(windowID) == GL_FALSE) {
            long now = System.nanoTime();
            delta += (now - lastTime) / ns;
            lastTime = now;

            if (delta >= 1.0) {
                input();
                update();
                updates++;
                delta--;

            }
            render();
            frames++;

            if (System.currentTimeMillis() - timer > 1000) {
                timer += 1000;
                //System.out.println(updates + " UPS, " + frames + " FPS");
                updates = 0;
                frames = 0;
            }
        }
    }

    public void dispose() {
        //Stop UDP Client
        server.stop();
    }

    public void run() {

        //initialise the game
        try {
            init();
            running = true;
            loop();

            //dispose 
            dispose();

            // Release window and window callbacks
            glfwDestroyWindow(windowID);
            keyCallback.release();
            mouseButtonCallback.release();
        } finally {
            // Terminate GLFW and release the GLFWerror function
            glfwTerminate();
            errorCallback.release();
        }
    }

    //CALLBACK FUNCTIONS
    //keyboard callback
    public void glfwKeyCallback(long window, int key, int scancode, int action, int mods) {
        // End on escape
        if (key == GLFW_KEY_ESCAPE && action == GLFW_RELEASE) {
            glfwSetWindowShouldClose(window, GL_TRUE); // We will detect this in our rendering loop
        }
        //jump
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_PRESS) {
            System.out.println("you jumped");
            input.setSpace(true);
        }
        if (glfwGetKey(window, GLFW_KEY_SPACE) == GLFW_RELEASE && input.isSpace()) {
            System.out.println("you stopped jumping");
            input.setSpace(false);
        } //movement keys
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_W) == GLFW_PRESS) {
            //move up
            System.out.println("you moved up");
            input.setUp(true);
        }
        if (glfwGetKey(window, GLFW_KEY_UP) == GLFW_RELEASE || glfwGetKey(window, GLFW_KEY_W) == GLFW_RELEASE) {
            input.setUp(false);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_S) == GLFW_PRESS) {
            //move down
            System.out.println("you moved down");
            input.setDown(true);
        }
        if (glfwGetKey(window, GLFW_KEY_DOWN) == GLFW_RELEASE || glfwGetKey(window, GLFW_KEY_S) == GLFW_RELEASE) {
            input.setDown(false);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_A) == GLFW_PRESS) {
            //move left
            System.out.println("you moved left");
            input.setLeft(true);
        }
        if (glfwGetKey(window, GLFW_KEY_LEFT) == GLFW_RELEASE || glfwGetKey(window, GLFW_KEY_A) == GLFW_RELEASE) {
            input.setLeft(false);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_PRESS || glfwGetKey(window, GLFW_KEY_D) == GLFW_PRESS) {
            //move right
            System.out.println("you moved right");
            input.setRight(true);
        }
        if (glfwGetKey(window, GLFW_KEY_RIGHT) == GLFW_RELEASE || glfwGetKey(window, GLFW_KEY_D) == GLFW_RELEASE) {
            input.setRight(false);
        } //reload-R
        if (glfwGetKey(window, GLFW_KEY_R) == GLFW_PRESS) {
            System.out.println("you reloaded");
            input.setR(true);
        }
        if (glfwGetKey(window, GLFW_KEY_R) == GLFW_RELEASE) {
            input.setR(false);
        }
    }

    //mouse callback
    public void glfwMouseCallback(long window, int key, int scancode, int action) {
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_1) == GLFW_PRESS) {
            // Left Mouse button is down!!
            System.out.println("You shoot a bullet!");
        }
        if (glfwGetMouseButton(window, GLFW_MOUSE_BUTTON_2) == GLFW_PRESS) {
            //Right mouse button is down!!
            System.out.println("You pressed right button");
        }
    }
}
