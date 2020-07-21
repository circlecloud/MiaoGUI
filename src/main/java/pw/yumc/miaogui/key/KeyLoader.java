package pw.yumc.miaogui.key;

import org.lwjgl.input.Keyboard;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.client.registry.ClientRegistry;

/**
 * @author MiaoWoo
 */
public class KeyLoader {
    public static KeyBinding openGUI;
    public static KeyBinding configGUI;

    public KeyLoader() {
        KeyLoader.openGUI = new KeyBinding("key.miaogui.open", Keyboard.KEY_R, "key.categories.miaogui");
        ClientRegistry.registerKeyBinding(KeyLoader.openGUI);
        KeyLoader.configGUI = new KeyBinding("key.miaogui.config", Keyboard.KEY_C, "key.categories.miaogui");
        ClientRegistry.registerKeyBinding(KeyLoader.configGUI);
    }
}
