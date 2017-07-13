varying vec4 color;
varying vec3 position;
varying vec2 UV;
varying vec3 normal;

uniform sampler2D textureSampler;

void main (void) {

// For light position
vec3 lightPos = vec3(1, 0, 0);

vec3 lightNormal = normalize(lightPos - position);

gl_FragColor = (max(dot(normal, lightNormal), 0.0) + 
	(pow 
		(max 
			(dot 
				(reflect 
					(-lightNormal, normalize(normal)), 
					normalize(0.0 - position)
				), 0.0
			), 10.0
		) * 5.0
	)

	) * texture2D(textureSampler, UV);


}