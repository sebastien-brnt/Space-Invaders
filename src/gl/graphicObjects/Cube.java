package gl.graphicObjects;

import com.jogamp.opengl.GL2;

import java.util.ArrayList;

public class Cube extends GraphicalObject {

    private ArrayList<Square> faces;

    public Cube(float posX, float posY, float posZ,
                float angleX, float angleY, float angleZ,
                float scale,
                float r, float g, float b) {
        super(posX, posY, posZ, angleX, angleY, angleZ, scale, r, g, b);
        faces = new ArrayList<Square>();

        // Front face
        faces.add(new Square(0, 0, 1, 0, 0, 0, 1, 0.8f, 0.2f, 0.2f));
        // Back face
        faces.add(new Square(0, 0, -1, 0, 0, 0, 1, 0.2f, 0.4f, 0.6f));
        // Right face
        faces.add(new Square(1, 0, 0, 0, 90, 0, 1, 0.1f, 0.8f, 0.2f));
        // Left face
        faces.add(new Square(-1, 0, 0, 0, -90, 0, 1, 0.6f, 0.5f, 0.2f));
        // Top face
        faces.add(new Square(0, 1, 0, 90, 0, 0, 1, 0.3f, 0.2f, 0.8f));
        // Left face
        faces.add(new Square(0, -1, 0, 90, 0, 0, 1, 0.3f, 0.8f, 0.7f));
    }

    public void display_normalized(GL2 gl) {
        // Affichage des faces
        for (Square face : faces) {
            face.display(gl);
        }
    }
}
