package gl;

import java.awt.Dimension;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.*;

import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLCanvas;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.util.Animator;
import gl.graphicObjects.Cube;
import gl.graphicObjects.GraphicalObject;
import gl.graphicObjects.Missile;
import gl.graphicObjects.Player;

public class MainGL extends GLCanvas implements GLEventListener, KeyListener
{
    private ArrayList<GraphicalObject> objects3D;
    private ArrayList<Missile> missiles;
    private ArrayList<Cube> targets;
    private float angle;
    private float level;

    private boolean isPaused = false;


    private Player player;

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

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }

    public MainGL() {
        this.addGLEventListener(this);
        this.objects3D = new ArrayList<GraphicalObject>();
        this.missiles = new ArrayList<Missile>();

        // Création des cibles
        this.targets = new ArrayList<Cube>();
        this.initTargets();

        this.angle = 0.0f;

        this.level = 1;

        // Commandes aux clavier
        this.addKeyListener(this);
        this.setFocusable(true);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL2.GL_COLOR_BUFFER_BIT | GL2.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glPushMatrix();
        gl.glTranslatef(0.0f, -4.0f, -15.0f);
        this.player.display(gl);
        gl.glPopMatrix();

        // Vérification du X des cibles
        boolean shouldMoveDown = targets.stream().anyMatch(target -> target.getX() < -7.5f || target.getX() > 7.5f);

        // Vitesse de déplacement des cibles
        float vitesse = level / 3000;
        boolean restartGame = false;

        if (targets.isEmpty()) {
            // Ajout d'un niveau et initialisation des cibles
            this.level += 1;
            this.initTargets();
        } else {
            for (Cube target : new ArrayList<>(targets)) {
                if (target.getY() < -3) {
                    int response = JOptionPane.showConfirmDialog(null, "Vous êtes mort, voulez-vous recommencer ?", "Vous êtes mort", JOptionPane.YES_NO_OPTION);
                    if (response == JOptionPane.YES_OPTION) {
                        // Redémarrage du jeu
                        restartGame = true;
                        break;
                    } else {
                        // Fermeture du jeu
                        System.exit(0);
                    }
                } else {
                    if (!isPaused) {
                        // Switch de droite à gauche suivant la valeur de Y
                        if (target.getY() % 1 == 0) {
                            target.translate(vitesse, 0, 0);
                        } else {
                            target.translate(-vitesse, 0, 0);
                        }
                    }

                    if (shouldMoveDown) {
                        // Replacement des cibles à -7,5 ou 7,5
                        if (target.getX() < -7.5f) {
                            target.setX(-7.5f);
                        } else if (target.getX() > 7.5f) {
                            target.setX(7.5f);
                        }
                        // Descente des cibles
                        target.translate(0, -0.5f, 0);
                    }

                    gl.glPushMatrix();
                    target.display(gl);
                    gl.glPopMatrix();
                }
            }
        }

        Iterator<Missile> shotIterator = missiles.iterator();
        while (shotIterator.hasNext()) {
            Missile shot = shotIterator.next();
            shot.translate(0, 0.005f, 0);
            boolean hit = false;

            Iterator<Cube> targetIterator = targets.iterator();
            while (targetIterator.hasNext()) {
                Cube target = targetIterator.next();
                if (shot.intersects(target)) {
                    targetIterator.remove();
                    hit = true;
                    break;
                }
            }

            if (hit || shot.getY() > 7) {
                // Suppression du missile
                shotIterator.remove();
            } else {
                gl.glPushMatrix();
                shot.display(gl);
                gl.glPopMatrix();
            }
        }

        // Redémarrage du jeu
        if (restartGame) {
            restartGame();
        }
    }

    private void restartGame() {
        // Remise à zero des cibles, du joueur et du niveau
        this.player.setX(0);
        this.targets.clear();
        this.level = 0;
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
        this.player = new Player(0, 0, 0, 0, 0, 0, 0.6f, 1, 1, 1);
    }

    public void initTargets() {
        float[] positionsX = {-6, -4.5f, -3, -1.5f, 0, 1.5f,  3, 4.5f, 6}; // Positions X pour les cibles
        float[] positionsY = {0, 1, 2, 3,  4}; // Positions Y pour les cibles

        for (float posY : positionsY) {
            for (float posX : positionsX) {
                this.targets.add(new Cube(posX, posY, -15, 0, 0, 0, 0.35f, 1, 1, 1));
            }
        }
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
                if ( player.getX() > -7) {
                    this.player.translate(-1, 0, 0);
                }
                break;
            case KeyEvent.VK_RIGHT: // Flèche de droite
                if ( player.getX() < 7 ) {
                    this.player.translate(+1, 0, 0);
                }
                break;
            case KeyEvent.VK_SPACE: // Barre d'espace
                Missile shotCube = new Missile(player.getX(), -4, -15, 0, 0, 0, 0.2f, 0.7f, 0.2f, 1, 1, 1);
                this.missiles.add(shotCube);
                break;
            case KeyEvent.VK_ESCAPE: // Touche Échappe
                isPaused = true;
                int response = JOptionPane.showConfirmDialog(null, "Voulez-vous vraiment quitter le jeu ?", "Quitter le jeu", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    System.exit(0);
                } else {
                    isPaused = false;
                }
                break;
        }
        this.repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    @Override
    public void keyTyped(KeyEvent e) {
    }


}