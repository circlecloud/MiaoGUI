package pw.yumc.miaogui.render;

import java.awt.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import pw.yumc.miaogui.interfaces.IRenderer;
import pw.yumc.miaogui.util.RenderMetadata;
import pw.yumc.miaogui.util.RenderUtil;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public class TextRenderer implements IRenderer {
    private int height;
    private int width;
    private double x;
    private double y;
    private String text;

    public TextRenderer(String text) {
        this.text = text.replace("&", "§");
        this.height = Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT;
        this.width = Minecraft.getMinecraft().fontRenderer.getStringWidth(text);
    }

    @Override
    public ScreenPosition load() {
        return ScreenPosition.fromRelativePosition(x, y);
    }

    @Override
    public void save(ScreenPosition pos) {
        x = pos.getRelativeX();
        y = pos.getRelativeY();
    }

    @Override
    public int getHeight() {
        return height;
    }

    @Override
    public int getWidth() {
        return width;
    }

    @Override
    public void render(RenderMetadata metadata) {
        String text = this.text
                .replace("%screen_width%", String.valueOf(Minecraft.getMinecraft().displayWidth))
                .replace("%screen_height%", String.valueOf(Minecraft.getMinecraft().displayHeight));
        ScreenPosition position = metadata.getPosition();
        Gui.drawRect(position.getAbsoluteX() - 5,
                     position.getAbsoluteY() - 2,
                     position.getAbsoluteX() + Minecraft.getMinecraft().fontRenderer.getStringWidth(text) + 3,
                     position.getAbsoluteY() + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 1,
                     RenderUtil.rgba2int(0, 0, 0, 50));
        Minecraft.getMinecraft().fontRenderer.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
    }

    @Override
    public void renderDummy(ScreenPosition position) {
        Minecraft.getMinecraft().fontRenderer.drawString(text, position.getAbsoluteX(), position.getAbsoluteY(), 0xFFFFFF);
    }
}
