package pw.yumc.miaogui.interfaces;

import pw.yumc.miaogui.util.RenderMetadata;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public interface IRenderer extends IConfigExchange {

    /**
     * Returns the height of the currently rendered HUD.
     *
     * @return The height in pixel.
     */
    default int getHeight() {
        return 0;
    }

    /**
     * Returns the width of the currently rendered HUD.
     *
     * @return The width in pixel.
     */
    default int getWidth() {
        return 0;
    }

    /**
     * Render the HUD at the given position.
     */
    void render(RenderMetadata metadata);

    /**
     * Render the HUD at the given position,
     * used to for the configuration screen
     * where you can move it around.
     */
    void renderDummy(ScreenPosition position);

    /**
     * Can be used to disable the renderer
     * more conveniently than unregistering
     * it from the API.
     */
    public default boolean isEnabled() {
        return true;
    }
}
