varying vec2 uv;
varying vec3 position;
varying vec4 color;
 
 uniform sampler2D textureSampler;
 
 void main(void) {
 		float wave = fract(sin(16.*2.*3.14*(position.x+position.y)));
 		gl_FragColor = color * wave * texture2D(textureSampler, uv);
 		
 	
 }