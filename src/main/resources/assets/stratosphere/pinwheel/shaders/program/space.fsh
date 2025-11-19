#include veil:space_helper

uniform sampler2D DiffuseSampler0;
uniform sampler2D DiffuseDepthSampler;

uniform vec3 Position;
uniform float Radius;
uniform float Time;

uniform float Density;
uniform float Power;
uniform vec3 Color;

in vec2 texCoord;
out vec4 fragColor;

#define STAR_CHANCE 0.5
#define BASE_SIZE 0.03

float hash1(float n) {
    return fract(sin(n) * 43758.5453);
}

float hash2(vec2 p) {
    return fract(sin(dot(p, vec2(127.1, 311.7))) * 43758.5453);
}

float hash3(vec3 p) {
    return fract(sin(dot(p, vec3(127.1, 311.7, 74.7))) * 43758.5453123);
}

float noise(vec2 p) {
    vec2 i = floor(p);
    vec2 f = fract(p);
    float a = hash2(i);
    float b = hash2(i + vec2(1.0, 0.0));
    float c = hash2(i + vec2(0.0, 1.0));
    float d = hash2(i + vec2(1.0, 1.0));
    vec2 u = f*f*(3.0-2.0*f);
    return mix(a, b, u.x) +
           (c - a)* u.y * (1.0 - u.x) +
           (d - b) * u.x * u.y;
}

float noise3(vec3 p) {
    vec3 i = floor(p);
    vec3 f = fract(p);

    float n000 = hash3(i + vec3(0,0,0));
    float n100 = hash3(i + vec3(1,0,0));
    float n010 = hash3(i + vec3(0,1,0));
    float n110 = hash3(i + vec3(1,1,0));
    float n001 = hash3(i + vec3(0,0,1));
    float n101 = hash3(i + vec3(1,0,1));
    float n011 = hash3(i + vec3(0,1,1));
    float n111 = hash3(i + vec3(1,1,1));

    vec3 u = f*f*(3.0-2.0*f);

    return mix(
        mix(mix(n000, n100, u.x), mix(n010, n110, u.x), u.y),
        mix(mix(n001, n101, u.x), mix(n011, n111, u.x), u.y),
        u.z
    );
}

bool iSphere(in vec3 ro, in vec3 rd, in vec3 center, in float radius, out float t) {
    vec3 oc = ro - center;
    float b = dot(oc, rd);
    float c = dot(oc, oc) - radius * radius;
    float h = b*b - c;
    if(h < 0.0) return false;
    h = sqrt(h);
    t = -b - h;
    if(t < 0.0) t = -b + h;
    if(t < 0.0) return false;
    return true;
}

float fbm(vec2 p) {
    float v = 0.0;
    float a = 0.5;
    for(int i = 0; i < 5; i++) {
        v += a * noise(p);
        p *= 2.0;
        a *= 0.5;
    }
    return v;
}

float fbm3(vec3 p) {
    float v = 0.0;
    float a = 0.5;
    for(int i=0; i<5; i++) {
        v += a * noise3(p);
        p *= 2.0;
        a *= 0.5;
    }
    return v;
}

bool raytraceSphere(in vec3 ro, in vec3 rd, out float dist, out vec4 color) {
    float t;
    if(!iSphere(ro, rd, Position, Radius, t)) return false;

    dist = t;
    vec3 hitPos = ro + rd * dist;
    vec3 normal = normalize(hitPos - Position);

    // ----- APPLY RING TILT -------------------------------------------------
    float cx = cos(50); float sx = sin(50);
    float cy = cos(0); float sy = sin(0);
    float cz = cos(10); float sz = sin(10);

    // Combined rotation (Z * Y * X)
    mat3 rotZ = mat3(
        cz, -sz, 0.0,
        sz,  cz, 0.0,
        0.0, 0.0, 1.0
    );

    mat3 rotY = mat3(
        cy, 0.0, sy,
        0.0, 1.0, 0.0,
       -sy, 0.0, cy
    );

    mat3 rotX = mat3(
        1.0, 0.0, 0.0,
        0.0, cx, -sx,
        0.0, sx,  cx
    );

    // final rotated normal
    vec3 tnormal = rotZ * rotY * rotX * normal;

    // --- Base nebula gas -----------------------------------------------
    float nebula = fbm3(normal * 3.0 + vec3(Time * 0.005, Time * 0.0005, Time * 0.0005));
    nebula = pow(nebula, 2.2);
    vec3 gasColor = mix(vec3(0.05, 0.07, 0.1), Color, nebula);
    vec3 backdrop = gasColor * 0.25;

    // --- NEW: Galactic gas ring with rotation and dark overlay -----------

    // Equator mask: stronger at equator, thinner towards poles
    float equator = tnormal.y;
    float ringMask = 1.0 - abs(equator);
    ringMask = pow(ringMask, 10.0);  // adjust thickness

    // --- 1. Apply rotation to the ring coordinates -----------------------
    // Build rotation matrix for XZ
    float angle = Time * 0.001;    // <-- rotation speed (adjust!)
    float ca = cos(angle);
    float sa = sin(angle);

    vec2 rotXZ = vec2(
        tnormal.x * ca - tnormal.z * sa,
        tnormal.x * sa + tnormal.z * ca
    );

    rotXZ += vec2(0, 0);

    // Ring procedural detail using rotated coords
    float ringNoise = fbm(rotXZ * 1.2);
    ringNoise = pow(ringNoise, 1.1);

    // Base glow
    float ringGlow = ringMask * (0.5 + 0.5 * ringNoise);

    // --- 2. Add darker gas overlay ---------------------------------------
    // Two-layer FBM noise to produce soft dark gas shadows
    float dark1 = fbm(rotXZ * 0.8 + vec2(Time * 0.00002, 0.0));
    float dark2 = fbm(rotXZ * 3.5 + vec2(0.0, Time * 0.0003));

    // Combine and sharpen dark mix
    float darkGas = pow((dark1 * 0.6 + dark2 * 0.4), 2.9);

    // Strength of darkening over the ring
    float darkMask = ringMask * darkGas * 1.7;   // <-- adjust density

    // Final ring color
    vec3 ringColor = Color * 1.5 * ringGlow;

    // Apply darkening (but keep the glow intact)
    ringColor *= (1.0 - darkMask);

    // --- 3. Add single bright diamond spot along the ring -----------------

    // Pick a fixed spot in ring space (rotXZ coordinates scaled to [0,1])
    vec2 diamondPos = vec2(0.1, 1.0); // arbitrary fixed position on ring

    // Distance from this spot
    float dDiamond = distance(rotXZ, diamondPos);

    // Diamond size and glow
    float diamondRadius = 1.5; // size of diamond
    float diamondGlow = smoothstep(diamondRadius, 0.0, dDiamond); // sharp bright center

    // Diamond color (bright white)
    vec3 diamondColor = vec3(1.5, 1.5, 2.8);

    // Add it on top of the ring
    ringColor += diamondColor * diamondGlow * 0.2;

    // Add ring to nebula background
    backdrop += ringColor;

    // -------------------------------------------------------------------
    // --- Starfield (unchanged) -----------------------------------------
    float u = 0.5 + atan(normal.z, normal.x) / (2.0 * 3.14159265);
    float v = 0.5 - asin(normal.y) / 3.14159265;

    vec2 st = vec2(u, v) * Density;
    vec2 cell = floor(st);
    vec2 f    = fract(st);

    float rnd = hash2(cell);
    if(rnd < STAR_CHANCE) {
        color = vec4(backdrop, 1.0);
        return true;
    }

    vec2 starCenter = vec2(hash2(cell+11.0), hash2(cell+37.0));
    float d = distance(f, starCenter);

    float sizeRand = mix(0.5, 1.5, hash1(rnd*91.2));
    float radius = BASE_SIZE * sizeRand;
    float glow = smoothstep(radius, radius*0.5, d);

    float baseBrightness = mix(0.6, 1.0, hash1(rnd*53.7));
    float twinkleSpeed = mix(1.0, 4.0, hash1(rnd*21.9));
    float twinklePhase = hash1(rnd*77.3) * 6.2831;
    float twinkle = 0.5 + 0.5 * sin((Time * 0.1) * twinkleSpeed + twinklePhase);
    float brightness = baseBrightness * mix(0.7, 1.2, twinkle);

    float cPick = hash1(rnd*13.7);

    vec3 starColor = vec3(1.0);
    if(cPick < 0.07)      starColor = vec3(0.7, 0.8, 1.0);
    else if(cPick < 0.14) starColor = vec3(1.0, 0.7, 0.5);

    vec3 col = starColor * brightness * glow;

    color = vec4(backdrop + col, 1.0);
    return true;
}

void main() {
    vec4 sceneColor = texture(DiffuseSampler0, texCoord);

    vec3 camera = VeilCamera.CameraPosition ;
    vec3 rd = normalize(viewDirFromUv(texCoord));

    vec4 sphereColor;
    float sphereDist;
    if(raytraceSphere(camera, rd, sphereDist, sphereColor)) {
        fragColor = mix(sceneColor, sphereColor, sphereColor.a);
    } else {
        fragColor = sceneColor;
    }

    gl_FragDepth = 1.0;
}








