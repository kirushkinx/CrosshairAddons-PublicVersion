package walksy.crosshairaddons.mixin;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.manager.ConfigManager;
import walksy.crosshairaddons.manager.CrosshairRendererManager;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {
    @Inject(method = "onGameStateChange", at = @At("HEAD"))
    private void onGameStateChange(GameStateChangeS2CPacket packet, CallbackInfo ci)
    {
        if (!ConfigManager.modEnabled) return;
        if (packet.getReason() == GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER)
        {
            CrosshairRendererManager.INSTANCE.onArrowHit();
        }
    }
}
