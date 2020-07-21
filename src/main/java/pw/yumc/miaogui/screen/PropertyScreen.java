package pw.yumc.miaogui.screen;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import pw.yumc.miaogui.MiaoGUI;
import pw.yumc.miaogui.api.HudPropertyApi;
import pw.yumc.miaogui.interfaces.IConfigExchange;
import pw.yumc.miaogui.interfaces.IRenderer;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public class PropertyScreen extends GuiScreen {
    private Minecraft mc = Minecraft.getMinecraft();
    private final HashMap<IRenderer, ScreenPosition> renderers = new HashMap<>();
    private IRenderer selectedRenderer = null;
    private int prevX, prevY;

    public PropertyScreen(HudPropertyApi api) {
        Collection<IRenderer> registeredRenderers = api.getHandlers();
        for (IRenderer ren : registeredRenderers) {
            if (!ren.isEnabled()) { continue; }
            ScreenPosition pos = ren.load();
            if (pos == null) { pos = ScreenPosition.fromRelativePosition(0.5, 0.5); }
            adjustBounds(ren, pos);
            this.renderers.put(ren, pos);
        }
    }

    @Override
    public void drawScreen(int x, int y, float partialTicks) {
        super.drawDefaultBackground();
        float zBackup = this.zLevel;
        this.zLevel = 200;
        renderers.forEach(IRenderer::renderDummy);
        this.zLevel = zBackup;
    }

    @Override
    protected void keyTyped(char c, int key) {
        MiaoGUI.logger.info(String.format("char: %s key: %s", c, key));
        if (key == 1) {
            // Save all entries
            renderers.forEach(IConfigExchange::save);
            this.mc.displayGuiScreen(null);
        }
        if (key == Keyboard.KEY_C) {
            this.renderers.clear();
            MiaoGUI.api.clear();
        }
    }

    @Override
    protected void mouseClicked(int x, int y, int button) throws IOException {
        prevX = x;
        prevY = y;
        this.selectedRenderer = renderers.keySet().stream()
                .filter(renderer -> {
                    ScreenPosition pos = renderers.get(renderer);
                    int absoluteX = pos.getAbsoluteX();
                    int absoluteY = pos.getAbsoluteY();
                    if (x >= absoluteX && x <= absoluteX + renderer.getWidth()) {
                        return y >= absoluteY && y <= absoluteY + renderer.getHeight();
                    }
                    return false;
                })
                .findFirst().orElse(null);
    }

    @Override
    protected void mouseClickMove(int x, int y, int button, long time) {
        if (selectedRenderer != null) {
            ScreenPosition position = renderers.get(selectedRenderer);
            position.setAbsolute(position.getAbsoluteX() + x - prevX, position.getAbsoluteY() + y - prevY);
            adjustBounds(selectedRenderer, position);
        }

        this.prevX = x;
        this.prevY = y;
    }

    @Override
    public void onGuiClosed() {
        renderers.forEach(IRenderer::save);
    }

    @Override
    public boolean doesGuiPauseGame() {
        return true;
    }

    private void adjustBounds(IRenderer renderer, ScreenPosition pos) {
        ScaledResolution res = new ScaledResolution(mc);

        int screenWidth = res.getScaledWidth();
        int screenHeight = res.getScaledHeight();

        int absoluteX = Math.max(0, Math.min(pos.getAbsoluteX(), Math.max(screenWidth - renderer.getWidth(), 0)));
        int absoluteY = Math.max(0, Math.min(pos.getAbsoluteY(), Math.max(screenHeight - renderer.getHeight(), 0)));

        pos.setAbsolute(absoluteX, absoluteY);
    }

}
