varying vec4 color;
varying vec3 position;
varying vec3 normal;
varying vec2 UV;


uniform mat4 frustMatrix;

void main(void) {
	color = gl_Color;
	
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	position = vec3(gl_ModelViewMatrix * gl_Vertex);

	UV = gl_MultiTexCoord0.st;
	
	normal = gl_NormalMatrix *gl_Normal; // Normalenvektor
	
	}