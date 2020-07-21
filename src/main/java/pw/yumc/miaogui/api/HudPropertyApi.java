package pw.yumc.miaogui.api;

import java.util.Collection;
import java.util.Collections;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Sets;
import net.minecraft.client.Minecraft;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import pw.yumc.miaogui.interfaces.IRenderer;
import pw.yumc.miaogui.screen.PropertyScreen;
import pw.yumc.miaogui.util.RenderMetadata;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public final class HudPropertyApi {

    public static HudPropertyApi newInstance() {
        HudPropertyApi api = new HudPropertyApi();
        MinecraftForge.EVENT_BUS.register(api);
        return api;
    }

    private Set<IRenderer> registeredRenderers = Sets.newHashSet();
    private Minecraft mc = Minecraft.getMinecraft();

    private boolean renderOutlines = true;

    private HudPropertyApi() {}

    public void register(IRenderer... renderers) {
        Collections.addAll(this.registeredRenderers, renderers);
    }

    public void unregister(IRenderer... renderers) {
        for (IRenderer renderer : renderers) {
            this.registeredRenderers.remove(renderer);
        }
    }

    public void clear() {
        this.registeredRenderers.clear();
    }

    public Collection<IRenderer> getHandlers() {
        return Sets.newHashSet(registeredRenderers);
    }

    public boolean getRenderOutlines() {
        return renderOutlines;
    }

    public void setRenderOutlines(boolean renderOutlines) {
        this.renderOutlines = renderOutlines;
    }

    public void openConfigScreen() {
        mc.displayGuiScreen(new PropertyScreen(this));
    }

    @SubscribeEvent
    public void onRender(RenderGameOverlayEvent event) {
        if (event.getType() == ElementType.TEXT) {
            if (!(mc.currentScreen instanceof PropertyScreen)) {
                registeredRenderers.stream()
                        .filter(IRenderer::isEnabled)
                        .forEach(iRenderer -> iRenderer
                                .render(new RenderMetadata(event, Optional.of(iRenderer.load())
                                        .orElse(ScreenPosition.fromRelativePosition(0.5, 0.5)))));
            }
        }
    }
}
