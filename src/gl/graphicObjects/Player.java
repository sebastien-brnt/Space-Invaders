package gl.graphicObjects;

import com.jogamp.opengl.GL2;

public class Player extends GraphicalObject {

    private float width;
    private float height;
    private float depth;

    public Player(float posX, float posY, float posZ,
                float angleX, float angleY, float angleZ,
                float width, float height, float depth,
                float scale,
                float r, float g, float b) {
        super(posX, posY, posZ, angleX, angleY, angleZ, scale, r, g, b);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public void display_normalized(GL2 gl) {
        gl.glBegin(GL2.GL_TRIANGLES);

        // Sommets de la base de la pyramide
        float baseHalfWidth = width / 2;
        float baseHalfDepth = depth / 2;

        // Sommet de la pyramide
        float apexX = 0;
        float apexY = height;
        float apexZ = 0;

        // Face avant
        gl.glColor3f(0.87f, 0.51f, 0.07f);;
        gl.glVertex3f(-baseHalfWidth, 0, baseHalfDepth);
        gl.glVertex3f(baseHalfWidth, 0, baseHalfDepth);
        gl.glVertex3f(apexX, apexY, apexZ);

        // Face arrière
        gl.glColor3f(0.95f, 0.64f, 0.24f);
        gl.glVertex3f(baseHalfWidth, 0, -baseHalfDepth);
        gl.glVertex3f(-baseHalfWidth, 0, -baseHalfDepth);
        gl.glVertex3f(apexX, apexY, apexZ);

        // Face gauche
        gl.glColor3f(0.56f, 0.43f, 0.26f);;
        gl.glVertex3f(-baseHalfWidth, 0, -baseHalfDepth);
        gl.glVertex3f(-baseHalfWidth, 0, baseHalfDepth);
        gl.glVertex3f(apexX, apexY, apexZ);

        // Face droite
        gl.glColor3f(0.67f, 0.39f, 0.04f);
        gl.glVertex3f(baseHalfWidth, 0, baseHalfDepth);
        gl.glVertex3f(baseHalfWidth, 0, -baseHalfDepth);
        gl.glVertex3f(apexX, apexY, apexZ);

        gl.glEnd();

        // Dessiner la base de la pyramide
        gl.glBegin(GL2.GL_QUADS);
        gl.glColor3f(0.37f, 0.31f, 0.24f);;
        gl.glVertex3f(-baseHalfWidth, 0, baseHalfDepth);
        gl.glVertex3f(baseHalfWidth, 0, baseHalfDepth);
        gl.glVertex3f(baseHalfWidth, 0, -baseHalfDepth);
        gl.glVertex3f(-baseHalfWidth, 0, -baseHalfDepth);
        gl.glEnd();
    }


}
