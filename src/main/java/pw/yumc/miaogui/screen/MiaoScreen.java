package pw.yumc.miaogui.screen;

import net.minecraft.client.gui.GuiScreen;

/**
 * @author MiaoWoo
 */
public class MiaoScreen extends GuiScreen {
    @Override
    public void initGui() {
        super.initGui();
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        super.drawScreen(mouseX, mouseY, partialTicks);
        this.fontRenderer.drawString("123", 1, 1, 1);
    }
}
