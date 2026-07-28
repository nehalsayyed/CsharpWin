use three_d::*;
use wasm_bindgen::prelude::*;

#[wasm_bindgen(start)]
pub fn start() {
    // Set console logging and panic hooks for debugging in browser console
    #[cfg(target_arch = "wasm32")]
    console_error_panic_hook::set_once();

    // Create default render window targeting the HTML <canvas id="canvas">
    let window = Window::new(WindowSettings {
        title: "Rust WASM 3D Graphics".to_string(),
        canvas_id: Some("canvas".to_string()),
        ..Default::default()
    })
    .unwrap();

    let context = window.gl();

    // Set up 3D Camera looking at the origin (0, 0, 0)
    let mut camera = Camera::new_perspective(
        window.viewport(),
        vec3(0.0, 2.0, 4.0),
        vec3(0.0, 0.0, 0.0),
        vec3(0.0, 1.0, 0.0),
        degrees(45.0),
        0.1,
        10.0,
    );

    // Create a 3D Cube model and material
    let mut model = Gm::new(
        Mesh::new(&context, &CpuMesh::cube()),
        PhysicalMaterial::new(
            &context,
            &CpuMaterial {
                albedo: Srgba::new(0, 180, 216, 255), // Cyan tint
                roughness: 0.3,
                metallic: 0.8,
                ..Default::default()
            },
        ),
    );

    // Directional and ambient lights
    let ambient_light = AmbientLight::new(&context, 0.4, Srgba::WHITE);
    let dir_light = DirectionalLight::new(&context, 2.0, Srgba::WHITE, &vec3(-1.0, -2.0, -1.0));

    // Render Loop (runs each frame inside WASM)
    window.render_loop(move |mut frame_output| {
        // Handle canvas resize
        camera.set_viewport(frame_output.viewport);

        // Rotate cube over time
        model.set_transformation(Mat4::from_angle_y(radians(
            (frame_output.accumulated_time * 0.001) as f32,
        )));

        // Render scene
        frame_output
            .screen()
            .clear(ClearState::color_and_depth(0.1, 0.1, 0.12, 1.0, 1.0))
            .render(&camera, &model, &[&ambient_light, &dir_light]);

        FrameOutput::default()
    });
}
