# Project Overview 📖
In collaboration, we engineered a comprehensive 3D image rendering system using pair programming. This encompassed simulated cameras, 3D objects, emission color integration, distinct light sources, shadows, reflections, refractions, and advanced features like Depth of Field (DOF).

We remained committed to design best practices including SOLID principles, RDD, OOP, and key design patterns like Template and Builder. Ensuring code excellence, we embraced Test-Driven Design (TDD), creating thorough unit and integration tests, covering Equivalence classes and boundary scenarios.

For runtime enhancements, I introduced multi-threading and adaptive supersampling. This bolstered performance and visual quality while preserving code integrity.

# Generating Pictures 🖼️
## Setup ⚙️
In order to generate a picture, first define a scene with its parameters:
- Name
- Camera (location and directions)
- Image Writer object
- View Plane and Resolution parameters
- 3D objects with their colors, transparency and reflection
- Background color (defualt=BLACK)
- Light sources
- DOF focal length and aperture size
- Number of threads
- RayTracer
- and more

## Running ▶️
- After setting everything up, you can generate the picture using this code: \
camera.setImageWriter(imageWriter) \
                .setRayTracer(new RayTracerBasic(scene)) \
                .renderImage() \
                .writeToImage();
- You can define every object using Builder pattern chaining
- The picture will be found in your 'images' directory
</h2>

Note: Multiple examples are found in our Tests in our tests

# Team Members 👨🏻‍💻
- Uriel Dolev
- Natan Weis
