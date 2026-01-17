package com.upsidedown.client;

import com.upsidedown.UpsideDownMod;
import com.upsidedown.dimension.ModDimensions;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.util.math.RotationAxis;
import org.joml.Matrix4f;

/**
 * Custom sky renderer for The Upside Down dimension.
 * 
 * Features:
 * - No sun or moon (perpetual dark twilight)
 * - Dark red to purple gradient sky
 * - Heavy atmospheric fog
 */
@Environment(EnvType.CLIENT)
public class UpsideDownSkyRenderer {

    // Sky colors (RGBA)
    private static final float[] HORIZON_COLOR = { 0.3f, 0.05f, 0.05f, 1.0f }; // Deep red
    private static final float[] MID_COLOR = { 0.15f, 0.02f, 0.15f, 1.0f }; // Dark purple
    private static final float[] ZENITH_COLOR = { 0.02f, 0.01f, 0.03f, 1.0f }; // Near black

    // Fog colors
    public static final float FOG_RED = 0.1f;
    public static final float FOG_GREEN = 0.02f;
    public static final float FOG_BLUE = 0.08f;

    private static boolean initialized = false;

    /**
     * Register the sky renderer.
     */
    public static void register() {
        initialized = true;
        UpsideDownMod.LOGGER.info("Upside Down sky renderer registered");
    }

    /**
     * Check if we should render the custom sky.
     */
    public static boolean shouldRenderCustomSky() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null)
            return false;
        return ModDimensions.isUpsideDown(client.world);
    }

    /**
     * Render the custom Upside Down sky.
     */
    public static void render(MatrixStack matrices, float tickDelta) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null)
            return;

        // Set up rendering
        BufferBuilder bufferBuilder = Tessellator.getInstance().begin(
                VertexFormat.DrawMode.TRIANGLE_FAN,
                VertexFormats.POSITION_COLOR);

        Matrix4f matrix = matrices.peek().getPositionMatrix();

        // Render sky dome with gradient
        renderSkyGradient(bufferBuilder, matrix);

        // Draw the buffer
        BufferRenderer.drawWithGlobalProgram(bufferBuilder.end());
    }

    private static void renderSkyGradient(BufferBuilder buffer, Matrix4f matrix) {
        // Center point (zenith - straight up)
        buffer.vertex(matrix, 0, 100, 0)
                .color(ZENITH_COLOR[0], ZENITH_COLOR[1], ZENITH_COLOR[2], ZENITH_COLOR[3]);

        // Create a circle around the player for the horizon
        int segments = 16;
        float radius = 100.0f;

        for (int i = 0; i <= segments; i++) {
            float angle = (float) (i * 2 * Math.PI / segments);
            float x = (float) Math.cos(angle) * radius;
            float z = (float) Math.sin(angle) * radius;

            // Horizon level
            buffer.vertex(matrix, x, 0, z)
                    .color(HORIZON_COLOR[0], HORIZON_COLOR[1], HORIZON_COLOR[2], HORIZON_COLOR[3]);
        }
    }

    /**
     * Get the fog color for The Upside Down.
     */
    public static float[] getFogColor() {
        return new float[] { FOG_RED, FOG_GREEN, FOG_BLUE };
    }
}
