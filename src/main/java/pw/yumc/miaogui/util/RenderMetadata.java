package pw.yumc.miaogui.util;

import net.minecraftforge.client.event.RenderGameOverlayEvent;

public class RenderMetadata {
    private RenderGameOverlayEvent event;
    private ScreenPosition position;

    public RenderMetadata(RenderGameOverlayEvent event, ScreenPosition position) {
        this.event = event;
        this.position = position;
    }

    public RenderGameOverlayEvent getEvent() {
        return event;
    }

    public ScreenPosition getPosition() {
        return position;
    }
}
