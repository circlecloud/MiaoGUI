var ImageIO = Java.type('javax.imageio.ImageIO');
var File = Java.type('java.io.File');
var I18n = Java.type('net.minecraft.client.resources.I18n');
var GlStateManager = Java.type('net.minecraft.client.renderer.GlStateManager');
var Minecraft = Java.type('net.minecraft.client.Minecraft');
var UUID = Java.type('java.util.UUID');
var DynamicTexture = Java.type('net.minecraft.client.renderer.texture.DynamicTexture');
var ResourceLocation = Java.type('net.minecraft.util.ResourceLocation');
var textureManager = Minecraft.getMinecraft().getTextureManager();
var fontRenderer = Minecraft.getMinecraft().fontRenderer;
var texture;

var GuiScreen = Java.type('net.minecraft.client.gui.GuiScreen');
var GuiContainer = Java.type('net.minecraft.client.gui.inventory.GuiContainer');
var GuiButton = Java.type('net.minecraft.client.gui.GuiButton');

var NetworkManager = Java.type('pw.yumc.miaogui.network.NetworkManager');
var ScreenPosition = Java.type('pw.yumc.miaogui.util.ScreenPosition');
//     @Override
// protected void mouseReleased(int mouseX, int mouseY, int state) {
//     super.mouseReleased(mouseX, mouseY, state);
//     if (test.isMouseOver()) {
//         Minecraft.getMinecraft().displayGuiScreen(null);
//     }
// }

function createGuiContainer(container) {
    texture = new ResourceLocation("miaogui", 'textures/miaogui.png');
    var point = ScreenPosition.fromRelativePosition(0.5, 0.5);
    var button = new GuiButton(0, point.getAbsoluteX() - 20, point.getAbsoluteY() - 10, 40, 20, "确定");
    var MiaoGuiContainer = Java.extend(GuiContainer, {
        initGui: function () {
            Java.super(guiContainer).initGui();
            Java.super(guiContainer).addButton(button)
        },
        mouseReleased: function (mouseX, mouseY, state) {
            Java.super(guiContainer).mouseReleased(mouseX, mouseY, state);
            if (button.isMouseOver()) {
                Minecraft.getMinecraft().displayGuiScreen(null);
            }
        },
        drawGuiContainerForegroundLayer: function (mouseX, mouseY) {
            try {
                var title = I18n.format("container.miaogui.title");
                fontRenderer.drawString(title, (guiContainer.getXSize() - fontRenderer.getStringWidth(title)) / 2, 6, 0x404040);
                var mouseInfo = java.lang.String.format("X: %s Y: %s", mouseX, mouseY);
                fontRenderer.drawString(mouseInfo, (guiContainer.getXSize() - fontRenderer.getStringWidth(mouseInfo)) / 2, 12, 0x404040);
            } catch (e) {
                e.printStackTrace()
            }
        },
        drawGuiContainerBackgroundLayer: function (partialTicks, mouseX, mouseY) {
            try {
                GlStateManager.color(1, 1, 1);
                textureManager.bindTexture(texture);
                guiContainer.drawTexturedModalRect((guiContainer.width - guiContainer.getXSize()) / 2, (guiContainer.height - guiContainer.getYSize()) / 2, 0, 0, guiContainer.getXSize(), guiContainer.getYSize());
            } catch (e) {
                e.printStackTrace()
            }
        },
        onGuiClosed: function () {
            Java.super(guiContainer).onGuiClosed();
            textureManager.deleteTexture(texture);
            NetworkManager.sendPacket("MiaoGUI", "CloseGUI".getBytes());
        }
    });
    var guiContainer = new MiaoGuiContainer(container);
    return guiContainer
}
