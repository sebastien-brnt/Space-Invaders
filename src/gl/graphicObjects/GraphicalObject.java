package gl.graphicObjects;

import com.jogamp.opengl.GL2;

public abstract class GraphicalObject
{
    private float posX, posY, posZ;
    private float angX, angY, angZ;
    private float r, g, b;
    private float scale;

    public GraphicalObject(float pX, float pY, float pZ,
                           float angX, float angY, float angZ,
                           float scale,
                           float r, float g, float b)
    {
        this.posX = pX;
        this.posY = pY;
        this.posZ = pZ;
        this.angX = angX;
        this.angY = angY;
        this.angZ = angZ;
        this.scale = scale;
        this.r = r;
        this.g = g;
        this.b = b;
    }

    public abstract void display_normalized(GL2 gl);

    public void display(GL2 gl)
    {
        gl.glPushMatrix();
        gl.glTranslatef(this.posX, this.posY, this.posZ);
        gl.glRotatef(this.angX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(this.angY, 0.0f, 1.0f, 0.0f);
        gl.glRotatef(this.angZ, 0.0f, 0.0f, 1.0f);
        gl.glScalef(this.scale, this.scale, this.scale);
        gl.glColor3f(this.r, this.g, this.b);
        this.display_normalized(gl);
        gl.glPopMatrix();
    }

    public void rotate(float aX,float aY,float aZ)
    {
        this.angX += aX;
        this.angY += aY;
        this.angZ += aZ;
    }

    public void translate(float pX,float pY,float pZ)
    {
        this.posX += pX;
        this.posY += pY;
        this.posZ += pZ;
    }

    public float getX() {
        return posX;
    }

    public float getY() {
        return posY;
    }

    public float getZ() {
        return posZ;
    }

    public void setX(float posX) {
        this.posX = posX;
    }

    public void setY(float posY) {
        this.posY = posY;
    }

    public void setZ(float posZ) {
        this.posZ = posZ;
    }

    // Méthode pour vérifier si ce cube intersecte avec un autre cube
    public boolean intersects(GraphicalObject other) {
        float baseSize = 1.0f; // Taille de base pour un objet non échelonné

        // Calculez la demi-taille pour chaque objet
        float halfSizeThis = baseSize * this.scale / 2.0f;
        float halfSizeOther = baseSize * other.scale / 2.0f;

        // Vérifier la collision sur chaque axe
        boolean collisionX = Math.abs(this.posX - other.posX) < (halfSizeThis + halfSizeOther);
        boolean collisionY = Math.abs(this.posY - other.posY) < (halfSizeThis + halfSizeOther);
        boolean collisionZ = Math.abs(this.posZ - other.posZ) < (halfSizeThis + halfSizeOther);

        // Si les trois collisions sont vraies, alors il y a une intersection
        return collisionX && collisionY && collisionZ;
    }
}