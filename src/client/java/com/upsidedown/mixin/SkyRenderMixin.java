package com.upsidedown.mixin;

import com.upsidedown.client.UpsideDownSkyRenderer;
import net.minecraft.client.render.WorldRenderer;
import net.minecraft.client.util.math.MatrixStack;
import org.joml.Matrix4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Mixin to inject custom sky rendering for The Upside Down dimension.
 */
@Mixin(WorldRenderer.class)
public class SkyRenderMixin {

    @Inject(method = "renderSky", at = @At("HEAD"), cancellable = true)
    private void upsidedown$renderCustomSky(Matrix4f matrix4f, Matrix4f projectionMatrix,
            float tickDelta, Camera camera, boolean thickFog, Runnable fogCallback,
            CallbackInfo ci) {

        if (UpsideDownSkyRenderer.shouldRenderCustomSky()) {
            // Create a new MatrixStack for our rendering
            MatrixStack matrices = new MatrixStack();
            matrices.multiplyPositionMatrix(matrix4f);

            // Render custom sky
            UpsideDownSkyRenderer.render(matrices, tickDelta);

            // Cancel vanilla sky rendering
            ci.cancel();
        }
    }
}
