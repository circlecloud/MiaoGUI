package pw.yumc.miaogui.container;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.inventory.Container;
import net.minecraft.util.ResourceLocation;
import pw.yumc.miaogui.MiaoGUI;
import pw.yumc.miaogui.network.NetworkManager;
import pw.yumc.miaogui.util.RenderUtil;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public class MiaoGuiContainer extends GuiContainer {
    private final ResourceLocation TEXTURE9 = new ResourceLocation(MiaoGUI.MODID, "textures/miaogui9.png");

    private GuiButton test;

    public MiaoGuiContainer(Container container) {
        super(container);
    }

    @Override
    public void initGui() {
        super.initGui();
        ScreenPosition point = ScreenPosition.fromRelativePosition(0.5, 0.5);
        test = new GuiButton(0, point.getAbsoluteX() - 20, point.getAbsoluteY() - 10, 40, 20, "确定");
        this.addButton(test);
    }

    @Override
    protected void mouseReleased(int mouseX, int mouseY, int state) {
        super.mouseReleased(mouseX, mouseY, state);
        if (test.isMouseOver()) {
            Minecraft.getMinecraft().displayGuiScreen(null);
        }
    }

    @Override
    protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
        String title = I18n.format("container.miaogui.title");
        fontRenderer.drawString(title, (getXSize() - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
        String mouseInfo = String.format("X: %s Y: %s", mouseX, mouseY);
        fontRenderer.drawString(mouseInfo, (getXSize() - fontRenderer.getStringWidth(mouseInfo)) / 2, 12, 0x404040);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
        GlStateManager.color(1, 1, 1);
        RenderUtil.tryBindTexture(TEXTURE9);
        RenderUtil.drawImage9((this.width - this.xSize) / 2, (this.height - this.ySize) / 2, 180, 150);
    }

    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        NetworkManager.sendPacket("MiaoGUI", "CloseGUI".getBytes());
    }
}
