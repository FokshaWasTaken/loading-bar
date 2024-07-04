package me.foksha.mixin;

import me.foksha.renderer.Renderer;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
/*? if <=1.20.2 {*/
import net.minecraft.client.gui.screen.LevelLoadingScreen;
import net.minecraft.client.gui.WorldGenerationProgressTracker;
/*?} else {*/
/*import net.minecraft.server.WorldGenerationProgressTracker;
import net.minecraft.client.gui.screen.world.LevelLoadingScreen;
	*//*?}*/
import net.minecraft.text.Text;
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

	@Shadow @Final private WorldGenerationProgressTracker progressProvider;

	@Unique
	MinecraftClient client = MinecraftClient.getInstance();
	/*? if <=1.20.2 {*/
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/client/gui/WorldGenerationProgressTracker;IIII)V"), method = "render")
	private void removeChunkMap(DrawContext context, WorldGenerationProgressTracker progressProvider, int centerX, int centerY, int pixelSize, int pixelMargin) {
	}
	/*?} else {*/
	/*@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/world/LevelLoadingScreen;drawChunkMap(Lnet/minecraft/client/gui/DrawContext;Lnet/minecraft/server/WorldGenerationProgressTracker;IIII)V"), method = "render")
	private void removeChunkMap(DrawContext context, WorldGenerationProgressTracker progressProvider, int centerX, int centerY, int pixelSize, int pixelMargin) {
	}
	*//*?}*/

	/*? if <=1.20.1 {*/
	@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Ljava/lang/String;III)V"), method = "render")
	private void removePercentage(DrawContext instance, TextRenderer textRenderer, String text, int centerX, int y, int color) {
	}
	/*?} else {*/
	/*@Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/DrawContext;drawCenteredTextWithShadow(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;III)V"), method = "render")
	private void removePercentage(DrawContext instance, TextRenderer textRenderer, Text text, int centerX, int y, int color) {
	}
 	*//*?}*/
	@Inject(method = "render", at = @At("TAIL"))
	private void renderBar(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
		if (client.currentScreen != null) {
			int x = client.currentScreen.width / 2;
			int y = client.currentScreen.height / 2;
			int percentage = this.progressProvider.getProgressPercentage();
			Renderer.renderBar(context, percentage, x - 48, y);
			Renderer.renderText(context, client.textRenderer, x, y + 15, percentage);
		}
	}
}
