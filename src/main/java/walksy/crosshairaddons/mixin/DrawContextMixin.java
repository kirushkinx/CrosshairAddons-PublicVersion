package walksy.crosshairaddons.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.texture.GuiAtlasManager;
import net.minecraft.client.texture.Scaling;
import net.minecraft.client.texture.Sprite;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import walksy.crosshairaddons.manager.ConfigManager;
import walksy.crosshairaddons.manager.CrosshairRendererManager;

@Mixin(DrawContext.class)
public abstract class DrawContextMixin {

    @Shadow
    public abstract int getScaledWindowHeight();

    @Shadow
    @Final
    private GuiAtlasManager guiAtlasManager;

    @Shadow
    protected abstract void drawSprite(Sprite sprite, int x, int y, int z, int width, int height);

    @Shadow
    protected abstract void drawSprite(Sprite sprite, Scaling.NineSlice nineSlice, int x, int y, int z, int width, int height);

    @Shadow
    protected abstract void drawSpriteTiled(Sprite sprite, int x, int y, int z, int width, int height, int i, int j, int tileWidth, int tileHeight, int k, int l);

    @Shadow
    protected abstract void drawSprite(Sprite sprite, int i, int j, int k, int l, int x, int y, int z, int width, int height);

    @Inject(method = "drawGuiTexture(Lnet/minecraft/util/Identifier;IIIIIIIII)V", at = @At("HEAD"), cancellable = true)
    private void drawCrosshairAttackIndicator(Identifier texture, int i, int j, int k, int l, int x, int y, int z, int width, int height, CallbackInfo ci) {
        if (!ConfigManager.modEnabled) return;
        if (texture.getPath().contains("hud/crosshair_attack")) {
            ci.cancel();
            int offsetY = calculateOffsetY();
            int h = this.getScaledWindowHeight() / 2 - 7 + offsetY;
            this.overrideDrawTexture1(texture, i, j, k, l, x, h, z, width, height);
        }
    }

    @Inject(method = "drawGuiTexture(Lnet/minecraft/util/Identifier;IIIII)V", at = @At("HEAD"), cancellable = true)
    private void drawCrosshairAttackIndicator(Identifier texture, int x, int y, int z, int width, int height, CallbackInfo ci) {
        if (!ConfigManager.modEnabled) return;
        if (texture.getPath().contains("hud/crosshair_attack")) {
            ci.cancel();
            int offsetY = calculateOffsetY(); // Calculate the offset based on mace indicator
            int h = this.getScaledWindowHeight() / 2 - 7 + offsetY;
            this.overrideDrawTexture2(texture, x, h, z, width, height);
        }
    }

    @Unique
    private void overrideDrawTexture1(Identifier texture, int i, int j, int k, int l, int x, int y, int z, int width, int height) {
        Sprite sprite = this.guiAtlasManager.getSprite(texture);
        Scaling scaling = this.guiAtlasManager.getScaling(sprite);
        if (scaling instanceof Scaling.Stretch) {
            this.drawSprite(sprite, i, j, k, l, x, y, z, width, height);
        } else {
            this.drawSprite(sprite, x, y, z, width, height);
        }
    }

    @Unique
    private void overrideDrawTexture2(Identifier texture, int x, int y, int z, int width, int height) {
        Sprite sprite = this.guiAtlasManager.getSprite(texture);
        Scaling scaling = this.guiAtlasManager.getScaling(sprite);
        if (scaling instanceof Scaling.Stretch) {
            this.drawSprite(sprite, x, y, z, width, height);
        } else if (scaling instanceof Scaling.Tile tile) {
            this.drawSpriteTiled(sprite, x, y, z, width, height, 0, 0, tile.width(), tile.height(), tile.width(), tile.height());
        } else if (scaling instanceof Scaling.NineSlice nineSlice) {
            this.drawSprite(sprite, nineSlice, x, y, z, width, height);
        }
    }

    @Unique
    private int calculateOffsetY() {
        if (ConfigManager.maceIndicator && ((CrosshairRendererManager.INSTANCE.recentMaceDamage > 5 && ConfigManager.showHitDamage) || CrosshairRendererManager.INSTANCE.isHoldingMace())) {
            return (CrosshairRendererManager.INSTANCE.isWearingElytra() && ConfigManager.elytraIndicator) ? 34 : 24;
        }
        return (CrosshairRendererManager.INSTANCE.isWearingElytra() && ConfigManager.elytraIndicator) ? 24 : 16;
    }
}
