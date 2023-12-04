package gl;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;

import javax.swing.JFrame;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import gl.graphicObjects.Cube;
import gl.graphicObjects.GraphicalObject;

public class MainGL extends GLCanvas implements GLEventListener, KeyListener
{
    private ArrayList<GraphicalObject> objects3D;
    private ArrayList<Cube> shotCubes;
    private float angle;


    private Cube player;

    public static void main(String[] args)
    {
        GLCanvas canvas = new MainGL();
        canvas.setPreferredSize(new Dimension(800, 600));
        final JFrame frame = new JFrame();
        frame.getContentPane().add(canvas);
        frame.setTitle("Space Invaders");
        frame.pack();
        frame.setVisible(true);
        Animator animator = new Animator(canvas);
        animator.start();
    }

    public MainGL() {
        this.addGLEventListener(this);
        this.objects3D = new ArrayList<GraphicalObject>();
        this.shotCubes = new ArrayList<Cube>();
        this.angle = 0.0f;

        this.addKeyListener(this);
        this.setFocusable(true);
        // this.requestFocusInWindow();
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT );
        gl.glLoadIdentity();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -4.0f, -15.0f);
        this.player.display(gl);
        gl.glPopMatrix();

        // Cubes tirés
        for (Cube cube : shotCubes) {
            cube.translate(0, 0.005f, 0);
            gl.glPushMatrix();
            cube.display(gl);
            gl.glPopMatrix();
        }
    }

    @Override
    public void dispose(GLAutoDrawable arg0) {
        // TODO Auto-generated method stub
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        // Color background
        gl.glClearColor(0.0f, 0.0f, 0.0f, 0.0f);
        gl.glClearDepth(1.0f);
        gl.glEnable(GL2.GL_DEPTH_TEST);
        gl.glDepthFunc(GL2.GL_LEQUAL);
        gl.glHint(GL2.GL_PERSPECTIVE_CORRECTION_HINT, GL2.GL_NICEST);

        // TODO : Initialize all graphical objects
        this.player = new Cube(0, 0, 0, 0, 0, 0, 0.6f, 1, 1, 1);
    }

    @Override
    public void reshape(GLAutoDrawable drawable,
                        int x, int y, int width, int height) {
        // TODO Auto-generated method stub
        GL2 gl = drawable.getGL().getGL2();
        // Set the view area
        gl.glViewport(0, 0, width, height);
        // Setup perspective projection
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        GLU glu = new GLU();
        glu.gluPerspective(45.0, (float)width/height,
                0.1, 100.0);
        // Enable the model view
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_LEFT: // Flèche de gauche
                if ( player.getX() > -6 ) {
                    this.player.translate(-1, 0, 0);
                }
                break;
            case KeyEvent.VK_RIGHT: // Flèche de droite
                if ( player.getX() < 6 ) {
                    this.player.translate(+1, 0, 0);
                }
                break;
            case KeyEvent.VK_SPACE: // Barre d'espace
                Cube shotCube = new Cube(player.getX(), -4, -15, 0, 0, 0, 0.2f, 1, 1, 1);
                this.shotCubes.add(shotCube);
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
        // Gérer ici si nécessaire
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Gérer ici si nécessaire
    }


}