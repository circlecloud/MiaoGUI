package pw.yumc.miaogui.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

/**
 * @author MiaoWoo
 */
public class RenderUtil {
    private static FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
    private static TextureManager textureManager = Minecraft.getMinecraft().getTextureManager();

    public static class RGBA {
        private float red;
        private float green;
        private float blue;
        private float alpine;

        public RGBA(float red, float green, float blue, float alpine) {
            this.red = red;
            this.green = green;
            this.blue = blue;
            this.alpine = alpine;
        }

        public float getRed() {
            return red;
        }

        public float getGreen() {
            return green;
        }

        public float getBlue() {
            return blue;
        }

        public float getAlpine() {
            return alpine;
        }
    }

    public static int rgba2int(int red, int blue, int green, int alpine) {
        return (alpine << 24) + (red << 16) + (green << 8) + blue;
    }

    public static RGBA int2rgba(int color) {
        float f3 = (color >> 24 & 255) / 255.0F;
        float f = (color >> 16 & 255) / 255.0F;
        float f1 = (color >> 8 & 255) / 255.0F;
        float f2 = (color & 255) / 255.0F;
        return new RGBA(f, f1, f2, f3);
    }

    public static void drawString(String text) {
        drawString(text, 0);
    }

    public static void drawString(String text, int color) {
        drawString(text, 0, 0, color);
    }

    public static void drawString(String text, int x, int y, int color) {
        fontRenderer.drawString(text, x, y, color);
    }

    public static void tryBindTexture(ResourceLocation location) {
        try {
            textureManager.bindTexture(location);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public static void tryDeleteTexture(ResourceLocation location) {
        try {
            textureManager.deleteTexture(location);
        } catch (Exception ignored) {
            ignored.printStackTrace();
        }
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height, double zLevel) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, zLevel)
                .tex((float) (textureX) * 0.00390625F, (float) (textureY + height) * 0.00390625F)
                .endVertex();
        bufferbuilder.pos(x + width, y + height, zLevel)
                .tex((float) (textureX + width) * 0.00390625F, (float) (textureY + height) * 0.00390625F)
                .endVertex();
        bufferbuilder.pos(x + width, y, zLevel)
                .tex((float) (textureX + width) * 0.00390625F, (float) (textureY) * 0.00390625F)
                .endVertex();
        bufferbuilder.pos(x, y, zLevel)
                .tex((float) (textureX) * 0.00390625F, (float) (textureY) * 0.00390625F)
                .endVertex();
        tessellator.draw();
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        drawTexturedModalRect(x, y, textureX, textureY, width, height, 0);
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int length) {
        drawTexturedModalRect(x, y, textureX, textureY, length, length);
    }

    public static void drawImage9(int x, int y, int width, int height) {
        drawImage9(x, y, width, height, 4, 1);
    }

    public static void drawImage9(int x, int y, int width, int height, int rect, int middle) {
        int rectMiddle = rect + middle;
        width -= rect;
        height -= rect;
        // 左上
        drawTexturedModalRect(x, y, 0, 0, rect);
        // 右上
        drawTexturedModalRect(x + width, y, rectMiddle, 0, rect);
        // 左下
        drawTexturedModalRect(x, y + height, 0, rectMiddle, rect);
        // 右下
        drawTexturedModalRect(x + width, y + height, rectMiddle, rectMiddle, rect);
        width -= rect;
        height -= rect;
        for (int i = 0; i < width; i++) {
            // 中上
            drawTexturedModalRect(x + rect + i, y, rect, 0, middle, rect);
            // 中下
            drawTexturedModalRect(x + rect + i, y + rect + height, rect, rectMiddle, middle, rect);
        }
        for (int i = 0; i < height; i++) {
            // 左中
            drawTexturedModalRect(x, y + rect + i, 0, rect, rect, middle);
            //            // 中中
            //            for (int j = 0; j < width; j++) {
            //                drawTexturedModalRect(x + rect + j, y + rect + i, rect, rect, middle, middle);
            //            }
            // 右中
            drawTexturedModalRect(x + rect + width, y + rect + i, rectMiddle, rect, rect, middle);
        }
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        GlStateManager.scale(100, 100, 100);
        //        drawTexturedModalRect(x + rect, y + rect, rect, rect, width, height);
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 3, 3, 1, 1, width, height);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }
}
