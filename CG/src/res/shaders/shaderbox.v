varying vec3 position;
varying vec2 uv;
varying vec4 color;

void main(void) {
	
	color = gl_Color;
	gl_Position = gl_ModelViewProjectionMatrix * gl_Vertex;
	uv = gl_MultiTexCoord0.st;
	position = vec3(gl_ModelViewMatrix * gl_Vertex);
	
	
	
	}