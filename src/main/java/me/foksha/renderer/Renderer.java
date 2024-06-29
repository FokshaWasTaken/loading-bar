package me.foksha.renderer;

import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.util.Identifier;

import java.util.Random;

public class Renderer {
    /*? if <=1.20.6 {*/
    static Identifier emptyBar = new Identifier("og-progress-bar", "textures/emptybar.png");
    static Identifier fullBar = new Identifier("og-progress-bar", "textures/fullbar.png");
    /*?} else {*/
    /*static Identifier emptyBar = Identifier.of("og-progress-bar", "textures/emptybar.png");
    static Identifier fullBar = Identifier.of("og-progress-bar", "textures/fullbar.png");
    *//*?}*/

    static String[] loadingStrings = new String[]{"Raising..", "Soiling..", "Eroding..", "Building terrain"};

    public static void renderBar(DrawContext context, int percentage, int x, int y) {
        context.drawTexture(emptyBar, x, y, 0, 0, 128, 4, 128, 4);

        int fullBarWidth = (percentage * 128) / 100;

        if (fullBarWidth > 0) {
            context.drawTexture(fullBar, x, y, 0, 0, fullBarWidth, 4, 128, 4);
        }
    }

    public static void renderText(DrawContext context, TextRenderer textRenderer, int x, int y, int percentage) {
        String text;
        if (percentage < 30) {
            text = loadingStrings[0];
        } else if (percentage < 60) {
            Random random = new Random();
            text = loadingStrings[1 + random.nextInt(2)];
        } else {
            text = loadingStrings[3];
        }
        context.drawCenteredTextWithShadow(textRenderer, "Loading level", x, y - 9 / 2 - 50, 16777215);

        context.drawCenteredTextWithShadow(textRenderer, text, x, y - 9 / 2 - 25, 16777215);
    }
}
