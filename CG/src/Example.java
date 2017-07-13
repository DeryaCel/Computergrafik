import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL20.*;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL20;
import org.lwjgl.util.vector.Matrix4f;
import org.lwjgl.util.vector.Vector3f;

import lenz.opengl.AbstractSimpleBase;
import lenz.opengl.utils.ShaderProgram;
import lenz.opengl.utils.Texture;

public class Example extends AbstractSimpleBase {

	// my 
	float rotate = 0.0f ;
	//float rotation = 0.0f;
	
	float rotateY = 0f;
	float translateZ = -6f;
	
	long time = System.nanoTime();
	float c;

	float x = 0;
	float y = 0;
	
	Matrix4f projection;
	Matrix4f view;
	float[] corners;
	
	MatrixDeri matrixderi;
	
	ShaderProgram shader;				// Shader

	
	public static void main(String[] args) {
		new Example().start();
	}

	private void getTime() {
		long t = System.nanoTime();
		float differenz = time - t;
		c = 0.5f * differenz / 1000f;
		time = t;
		}
	
	@Override
	protected void initOpenGL() {
		glMatrixMode(GL_PROJECTION);
		glFrustum(-1.6, 1.6, -1.2, 1.2, 1.5, 6.5);
		glMatrixMode(GL_MODELVIEW);
		
		glHint(GL_PERSPECTIVE_CORRECTION_HINT, GL_NICEST);  // Nice perspective corrections
		
		glEnable(GL_CULL_FACE); // Rueckseiten werden nicht gezeichnet
		glEnable(GL_DEPTH_TEST); // Tiefentest, welche Flaeche hinter welcher liegt
		
		getTime();
		
		// Shader
		shader = new ShaderProgram("shaderbox");
		glBindAttribLocation(shader.getId(), 0, "corners");
		glLinkProgram(shader.getId());
		
		glEnable(GL_CULL_FACE);
		glShadeModel(GL_SMOOTH); // vorher ...(GL_FLAT);
		
		// Texture
		new Texture("floor.jpg");
		
		loadAssets();
	}	
	
	private void loadAssets() {
		matrixderi = new MatrixDeri(3);
		matrixderi.mvp = new Matrix4f().translate(new Vector3f(10.0f, 5.0f, 0.0f));
	}
	
	
	
	@Override
	protected void render() {
		glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
		
		rotate += 0.5f;
		
		glEnable(GL_TEXTURE_2D);	// Texture
		
		// my 3D
		
		glLoadIdentity();			// Ueberschreibt die Identitaets-Matrix
		glTranslatef(x, y, -5.0f);
		glRotatef(rotate, 1.0f, 1.0f, 1.0f);
		
		key(); // Keyboard
		
		GL20.glUseProgram(shader.getId());  // Shader Aktivierung
		
		// Cube
		glBegin(GL_QUADS);
		glTexCoord2f(1.0f, 1.0f);
		//glColor3f(1.0f, 1.0f, 1.0f);
		glVertex3f( 1.0f, 1.0f, -1.0f);          // Top Right Of The Quad (Top)
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-1.0f, 1.0f, -1.0f);          // Top Left Of The Quad (Top)
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-1.0f, 1.0f, 1.0f);          // Bottom Left Of The Quad (Top)
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f( 1.0f, 1.0f, 1.0f);          // Bottom Right Of The Quad (Top)
		
		glTexCoord2f(1.0f, 1.0f);
		//glColor3f(0f, 1f, 0f);
		glVertex3f( 1.0f,-1.0f, 1.0f);          // Top Right Of The Quad (Bottom)
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-1.0f, -1.0f, 1.0f);          // Top Left Of The Quad (Bottom)
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-1.0f, -1.0f,-1.0f);          // Bottom Left Of The Quad (Bottom)
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f( 1.0f,-1.0f,-1.0f);          // Bottom Right Of The Quad (Bottom)
		
		glTexCoord2f(1.0f, 1.0f);
		//glColor3f(0f, 0f, 1f);
		glVertex3f(1.0f, 1.0f, 1.0f);          // Top Right Of The Quad (Front)
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-1.0f, 1.0f, 1.0f);          // Top Left Of The Quad (Front)
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-1.0f,-1.0f, 1.0f);          // Bottom Left Of The Quad (Front)
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f( 1.0f,-1.0f, 1.0f);          // Bottom Right Of The Quad (Front)
		
		glTexCoord2f(0.0f, 1.0f);
		//glColor3f(0f, 1f, 1f);
		glVertex3f( 1.0f,-1.0f,-1.0f);          // Bottom Left Of The Quad (Back)
		glTexCoord2f(1.0f, 1.0f);
		glVertex3f(-1.0f,-1.0f,-1.0f);          // Bottom Right Of The Quad (Back)
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(-1.0f, 1.0f,-1.0f);          // Top Right Of The Quad (Back)
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f( 1.0f, 1.0f,-1.0f);          // Top Left Of The Quad (Back
		
		glTexCoord2f(1.0f, 1.0f);
		//glColor3f(0.5f, 0.5f, 0.5f);
		glVertex3f(-1.0f, 1.0f, 1.0f);          // Top Right Of The Quad (Left)
		glTexCoord2f(0.0f, 1.0f);
		glVertex3f(-1.0f, 1.0f,-1.0f);          // Top Left Of The Quad (Left)
		glTexCoord2f(0.0f, 0.0f);
		glVertex3f(-1.0f,-1.0f,-1.0f);          // Bottom Left Of The Quad (Left)
		glTexCoord2f(1.0f, 0.0f);
		glVertex3f(-1.0f,-1.0f, 1.0f);          // Bottom Right Of The Quad (Left)
		
		glTexCoord2f(1.0f, 1.0f);
		//glColor3f(1.0f, 0f, 0f);
		glVertex3f(1.0f, 1.0f,-1.0f);          // Top Right Of The Quad (Right)
	    glTexCoord2f(0.0f, 1.0f);
		glVertex3f(1.0f, 1.0f, 1.0f);          // Top Left Of The Quad (Right)
	    glTexCoord2f(0.0f, 0.0f);
		glVertex3f(1.0f,-1.0f, 1.0f);          // Bottom Left Of The Quad (Right)
	    glTexCoord2f(1.0f, 0.0f);
		glVertex3f(1.0f,-1.0f,-1.0f);          // Bottom Right Of The Quad (Right)
		
		glEnd();
		
		
		// Pyramid
		
		 glLoadIdentity();                  	// Reset the model-view matrix
		  glTranslatef(-2.5f, 0.0f, -5.0f);  	// Move left and into the screen
		  glRotatef(rotate, 0.0f, 1.0f, 0.0f);
		   
		  glBegin(GL_TRIANGLES);           		
		      // Front
		      glTexCoord2f(1.0f, 1.0f);    	
		      glVertex3f( 0.0f, 1.0f, 0.0f);
		      glTexCoord2f(0.0f, 1.0f);     	
		      glVertex3f(-1.0f, -1.0f, 1.0f);
		      glTexCoord2f(0.0f, 0.0f);     	
		      glVertex3f(1.0f, -1.0f, 1.0f);
		 
		      // Right
		      glTexCoord2f(1.0f, 1.0f);    	
		      glVertex3f(0.0f, 1.0f, 0.0f);
		      glTexCoord2f(0.0f, 1.0f);    	
		      glVertex3f(1.0f, -1.0f, 1.0f);
		      glTexCoord2f(0.0f, 0.0f);     	
		      glVertex3f(1.0f, -1.0f, -1.0f);
		 
		      // Back
		      glTexCoord2f(1.0f, 1.0f);  	
		      glVertex3f(0.0f, 1.0f, 0.0f);
		      glTexCoord2f(0.0f, 1.0f);    	
		      glVertex3f(1.0f, -1.0f, -1.0f);
		      glTexCoord2f(0.0f, 0.0f);     	
		      glVertex3f(-1.0f, -1.0f, -1.0f);
		 
		      // Left
		      glTexCoord2f(1.0f, 1.0f);       	
		      glVertex3f( 0.0f, 1.0f, 0.0f);
		      glTexCoord2f(0.0f, 1.0f);       	
		      glVertex3f(-1.0f,-1.0f,-1.0f);
		      glTexCoord2f(0.0f, 0.0f);      	
		      glVertex3f(-1.0f,-1.0f, 1.0f);
		   
		      glEnd();   						
		
	}
	
	public void  key() {
		
		float step = 0.03f;
		
		if (Keyboard.isKeyDown(Keyboard.KEY_A))
		{
			x -= step;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_D))
		{
			x += step;
		}

		if (Keyboard.isKeyDown(Keyboard.KEY_W))
		{
			y += step;

		}

		if (Keyboard.isKeyDown(Keyboard.KEY_S))
		{
			y -= step;
		}
	}
	
	
	
}

/** Quellen:
 * Studenten: Julia Katzsch, Felix Malchert, Marcel Sansür, Artur Maurer, Paula Haertel, Silja Woelk
 * Folien von der Vorlesung
 * http://www.raywenderlich.com/70208/opengl-es-pixel-shaders-tutorial
 * http://www.mathematik.uni-marburg.de/~thormae/lectures/graphics1/graphics_11_1_ger_web.html#12
 * http://www.cburch.com/cs/490/sched/feb8/
 * http://nehe.gamedev.net/tutorial/3d_shapes/10035/
 * https://www.opengl.org/wiki/Rendering_Pipeline_Overview
 * http://schabby.de/opengl-shader-example/
 * http://en.wikibooks.org/wiki/OpenGL_Programming/Modern_OpenGL_Tutorial_06
 * https://www.opengl.org/wiki/Fragment_Shader
 * http://wiki.lwjgl.org/wiki/GLSL_Tutorial:_Texturing
 * http://www.felixgers.de/teaching/jogl/lightProg.html
 * http://nehe.gamedev.net/tutorial/lessons_01__05/22004/
 * http://www.land-of-kain.de/docs/jogl/
 * https://www.youtube.com/watch?v=9h-1QWSQv50
 * http://graphics.stanford.edu/courses/cs248-00/helpsession/opengl/code_example.html
 * http://www.ntu.edu.sg/home/ehchua/programming/opengl/CG_Examples.html
 * http://www.google.de/imgres?imgurl=http%3A%2F%2Fstore.inspiremebaby.com%2Fwp-content%2Fuploads%2F2012%2F05%2Fpine-wood-floor.jpg&imgrefurl=http%3A%2F%2Fstore.inspiremebaby.com%2Fshop%2Fwarm-pine-floor-mat-photo-prop%2F&h=561&w=800&tbnid=FNzgu772vTL2oM%3A&zoom=1&docid=w9OqfZtkrFpfkM&ei=6Xi5VO_3GonfOOWJgJAK&tbm=isch&iact=rc&uact=3&dur=310&page=2&start=15&ndsp=21&ved=0CIoBEK0DMB8
 */
