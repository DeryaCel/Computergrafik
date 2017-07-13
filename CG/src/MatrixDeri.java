import java.nio.FloatBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL20;
import org.lwjgl.opengl.GL30;
import org.lwjgl.util.vector.Matrix4f;

import lenz.opengl.utils.ShaderProgram;
import lenz.opengl.utils.Texture;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;



public class MatrixDeri {
	
	
	private Texture diamondTexture;
	private ShaderProgram shader;

	int vaoId, vboId, vboIdT;
	
	public Matrix4f mvp;
	public float corner = 3f;
	private FloatBuffer cornersBuffer;
	
	
	public MatrixDeri(float corner) {
		this.corner = corner;
		init();
	}
	
	private void init() {
		mvp = new Matrix4f();
		//matrixArray();
		diamondTexture = new Texture("diamond.jpg");
		shader = new ShaderProgram("shader");
		glUseProgram(shader.getId());
		glBindAttribLocation(shader.getId(), 0, "corner");
		glBindAttribLocation(shader.getId(), 1, "vertexUv");
		glUseProgram(0);
	}
	
	
	private void cubeArray() {
		float[] corners = new float[] {
				
				corner, corner, corner,
				-corner, corner, corner,
				-corner, -corner, corner,
				corner, -corner, corner,
				
				corner, corner, -corner,
				corner, corner, corner,
				corner, -corner, corner,
				corner, -corner, -corner,
				
				-corner, -corner, -corner,
				-corner, corner, -corner,
				corner, corner, -corner,
				corner, -corner, -corner,
				
				-corner, corner, corner,
				-corner, corner, -corner,
				-corner, -corner, -corner,
				-corner, -corner, corner,
				
				-corner, corner, -corner,
				-corner, corner, -corner,
				corner, corner, corner,
				corner, corner, -corner,
				
				-corner, -corner, corner,
				-corner, -corner, -corner,
				corner, -corner, -corner,
				corner, -corner, corner,
				
		};
		
		FloatBuffer edgeBuffer = BufferUtils.createFloatBuffer(corners.length);
		edgeBuffer.put(corners);
		edgeBuffer.flip();
		
		float[] texture = new float[] {
				1,1,
				0,1,
				0,0,
				1,0,
				
				1,0,
				1,1,
				0,1,
				0,0,
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				1,1,
				1,0,
				0,0,
				0,1,
				
				0,0,
				0,1,
				1,1,
				1,0,
				
				0,1,
				0,0,
				1,0,
				1,1	
		};
		
		
		FloatBuffer mvpBuffer = BufferUtils.createFloatBuffer(texture.length);
		mvp.store(mvpBuffer);
		mvpBuffer.flip();
		glUniformMatrix4(
		glGetUniformLocation(shader.getId(), "mvp"),false, mvpBuffer);
		
		
		vaoId = glGenVertexArrays();
		glBindVertexArray(vaoId);
		vboId = GL15.glGenBuffers();
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, vboId);
		GL15.glBufferData(GL15.GL_ARRAY_BUFFER,cornersBuffer, GL15.GL_STATIC_DRAW);
		glVertexAttribPointer(0, 3, GL11.GL_FLOAT,false, 0, 0);
		glEnableVertexAttribArray(0);
		GL15.glBindBuffer(GL15.GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
		
	}
	
}
