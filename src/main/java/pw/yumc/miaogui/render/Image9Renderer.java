package pw.yumc.miaogui.render;

import net.minecraft.util.ResourceLocation;
import pw.yumc.miaogui.MiaoGUI;
import pw.yumc.miaogui.interfaces.IRenderer;
import pw.yumc.miaogui.util.RenderMetadata;
import pw.yumc.miaogui.util.RenderUtil;
import pw.yumc.miaogui.util.ScreenPosition;

/**
 * @author MiaoWoo
 */
public class Image9Renderer implements IRenderer {
    private ResourceLocation location;
    private int x;
    private int y;

    public Image9Renderer(String location) {
        this(new ResourceLocation(MiaoGUI.MODID, location), 0, 0);
    }

    public Image9Renderer(ResourceLocation location) {
        this(location, 0, 0);
    }

    public Image9Renderer(ResourceLocation location, int x, int y) {
        this.location = location;
        this.x = x;
        this.y = y;
    }

    @Override
    public ScreenPosition load() {
        return ScreenPosition.fromAbsolutePosition(x, y);
    }

    @Override
    public void save(ScreenPosition pos) {
        this.x = pos.getAbsoluteX();
        this.y = pos.getAbsoluteY();
    }

    @Override
    public void render(RenderMetadata metadata) {
        this.renderDummy(metadata.getPosition());
    }

    @Override
    public void renderDummy(ScreenPosition metadata) {
        RenderUtil.tryBindTexture(location);
        RenderUtil.drawImage9(metadata.getAbsoluteX(), metadata.getAbsoluteY(), 180, 150);
    }
}
