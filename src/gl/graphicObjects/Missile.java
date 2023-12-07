package gl.graphicObjects;

import com.jogamp.opengl.GL2;

public class Missile extends GraphicalObject {

    private float width;
    private float height;
    private float depth;

    public Missile(float pX, float pY, float pZ,
                       float angX, float angY, float angZ,
                       float width, float height, float depth,
                       float r, float g, float b) {
        super(pX, pY, pZ, angX, angY, angZ, 1.0f, r, g, b);
        this.width = width;
        this.height = height;
        this.depth = depth;
    }

    public void display_normalized(GL2 gl) {
        gl.glBegin(GL2.GL_QUADS);

        // Face avant
        gl.glVertex3f(-width / 2, height / 2, depth / 2);
        gl.glVertex3f(width / 2, height / 2, depth / 2);
        gl.glVertex3f(width / 2, -height / 2, depth / 2);
        gl.glVertex3f(-width / 2, -height / 2, depth / 2);

        // Face arrière
        gl.glVertex3f(-width / 2, height / 2, -depth / 2);
        gl.glVertex3f(width / 2, height / 2, -depth / 2);
        gl.glVertex3f(width / 2, -height / 2, -depth / 2);
        gl.glVertex3f(-width / 2, -height / 2, -depth / 2);

        // Face supérieure
        gl.glVertex3f(-width / 2, height / 2, -depth / 2);
        gl.glVertex3f(width / 2, height / 2, -depth / 2);
        gl.glVertex3f(width / 2, height / 2, depth / 2);
        gl.glVertex3f(-width / 2, height / 2, depth / 2);

        // Face inférieure
        gl.glVertex3f(-width / 2, -height / 2, -depth / 2);
        gl.glVertex3f(width / 2, -height / 2, -depth / 2);
        gl.glVertex3f(width / 2, -height / 2, depth / 2);
        gl.glVertex3f(-width / 2, -height / 2, depth / 2);

        // Face gauche
        gl.glVertex3f(-width / 2, -height / 2, -depth / 2);
        gl.glVertex3f(-width / 2, height / 2, -depth / 2);
        gl.glVertex3f(-width / 2, height / 2, depth / 2);
        gl.glVertex3f(-width / 2, -height / 2, depth / 2);

        // Face droite
        gl.glVertex3f(width / 2, -height / 2, -depth / 2);
        gl.glVertex3f(width / 2, height / 2, -depth / 2);
        gl.glVertex3f(width / 2, height / 2, depth / 2);
        gl.glVertex3f(width / 2, -height / 2, depth / 2);

        gl.glEnd();
    }

}
