package me.foksha.mixin;

import me.foksha.renderer.Renderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelLoadingScreen.class)
public abstract class LevelLoadingScreenMixin {
	@Shadow protected abstract String getPercentage();

	@Shadow @Final private WorldGenerationProgressTracker progressProvider;

	@Unique
	MinecraftClient client = MinecraftClient.getInstance();
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/gui/WorldGenerationProgressTracker;IIII)V"), method = "render")
	private void removeChunkMap(DrawContext context, WorldGenerationProgressTracker progressProvider, int centerX, int centerY, int pixelSize, int pixelMargin) {
	}

	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "render")
	private void removePercentage(DrawContext instance, TextRenderer textRenderer, String text, int centerX, int y, int color) {
	}

	@Inject(method = "render", at = @At("TAIL"))
	private void renderBar(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		int x = client.currentScreen.width / 2;
		int y = client.currentScreen.height / 2;
		int percentage = this.progressProvider.getProgressPercentage();
		Renderer.renderBar(context, percentage, x - 64, y);
		Renderer.renderText(context, client.textRenderer, x, y + 15, percentage);
	}
}