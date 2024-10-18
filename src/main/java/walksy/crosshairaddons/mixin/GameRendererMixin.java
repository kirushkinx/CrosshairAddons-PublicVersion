package walksy.crosshairaddons.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.render.BufferBuilderStorage;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.client.render.RenderTickCounter;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;
import walksy.crosshairaddons.manager.ConfigManager;
import walksy.crosshairaddons.manager.CrosshairRendererManager;

@Mixin(GameRenderer.class)
public abstract class GameRendererMixin {

    @Shadow
    @Final
    MinecraftClient client;

    @Shadow
    @Final
    private BufferBuilderStorage buffers;

    @Inject(method = "render", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/profiler/Profiler;swap(Ljava/lang/String;)V"))
    public void onRender(RenderTickCounter tickCounter, boolean tick, CallbackInfo ci)
    {
        if (!ConfigManager.modEnabled) return;
        DrawContext drawContext = new DrawContext(this.client, this.buffers.getEntityVertexConsumers());
        CrosshairRendererManager.INSTANCE.renderCrosshair(drawContext);
    }
}
