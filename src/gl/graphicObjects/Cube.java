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
        faces.add(new Square(0, 0, 1, 0, 0, 0, 1, 0.12f, 0.25f, 0.69f));
        // Back face
        faces.add(new Square(0, 0, -1, 0, 0, 0, 1, 0.32f, 0.39f, 0.6f));
        // Right face
        faces.add(new Square(1, 0, 0, 0, 90, 0, 1, 0.05f, 0.16f, 0.53f));
        // Left face
        faces.add(new Square(-1, 0, 0, 0, -90, 0, 1, 0.41f, 0.47f, 0.69f));
        // Top face
        faces.add(new Square(0, 1, 0, 90, 0, 0, 1, 0.14f, 0.2f, 0.43f));
        // Left face
        faces.add(new Square(0, -1, 0, 90, 0, 0, 1, 0.57f, 0.63f, 0.83f));
    }

    public void display_normalized(GL2 gl) {
        // Affichage des faces
        for (Square face : faces) {
            face.display(gl);
        }
    }
}
