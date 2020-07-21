package pw.yumc.miaogui.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;
import pw.yumc.miaogui.interfaces.IRenderer;
import pw.yumc.miaogui.util.RenderMetadata;
import pw.yumc.miaogui.util.RenderUtil;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public class BroadcastRenderer implements IRenderer {

    @Override
    public ScreenPosition load() {
        return ScreenPosition.fromRelativePosition(0.5, 0.5);
    }

    @Override
    public void save(ScreenPosition pos) {
    }

    @Override
    public void render(RenderMetadata metadata) {
        ScreenPosition position = metadata.getPosition();
        position.setAbsolute(30, position.getAbsoluteY());
        Gui.drawRect(0, position.getAbsoluteY() - 2,
                     Minecraft.getMinecraft().displayWidth, position.getAbsoluteY() + Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT + 1,
                     RenderUtil.rgba2int(0, 0, 0, 50));
        Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(metadata.getEvent().getPartialTicks()),
                                                         position.getAbsoluteX(),
                                                         position.getAbsoluteY(),
                                                         0xFFFFFF);
    }

    @Override
    public void renderDummy(ScreenPosition position) {

    }
}
