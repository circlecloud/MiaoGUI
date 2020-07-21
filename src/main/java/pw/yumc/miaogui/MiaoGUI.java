package pw.yumc.miaogui;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.ClientCommandHandler;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStartingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import pw.yumc.MiaoScript.ScriptEngine;
import pw.yumc.miaogui.api.HudPropertyApi;
import pw.yumc.miaogui.command.MainCommand;
import pw.yumc.miaogui.handler.MiaoHandler;
import pw.yumc.miaogui.key.KeyLoader;
import pw.yumc.miaogui.network.NetworkManager;

/**
 * @author MiaoWoo
 */
@Mod(modid = MiaoGUI.MODID, name = MiaoGUI.NAME, version = MiaoGUI.VERSION)
public class MiaoGUI {
    public static final String MODID = "miaogui";
    public static final String NAME = "MiaoGUI";
    public static final String VERSION = "1.0";

    public static HudPropertyApi api;
    public static Logger logger;
    public static ScriptEngine engine;

    @Mod.Instance(MiaoGUI.MODID)
    public static MiaoGUI INSTANCE;

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        logger = event.getModLog();
        api = HudPropertyApi.newInstance();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        logger.info(MODID + " Loading...");
        engine = new ScriptEngine("", logger, this);
        engine.createEngine();
        new KeyLoader();
        NetworkManager networkManager = new NetworkManager();
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(networkManager);
        NetworkRegistry.INSTANCE.registerGuiHandler(MiaoGUI.INSTANCE, new MiaoHandler());
        NetworkRegistry.INSTANCE.newEventDrivenChannel(NAME).register(networkManager);
    }

    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void onKeyInput(InputEvent.KeyInputEvent event) {
        if (KeyLoader.openGUI.isPressed()) {
            EntityPlayer player = Minecraft.getMinecraft().player;
            player.openGui(this, MiaoHandler.MiaoGUIID, player.world, 0, 0, 0);
        } else if (KeyLoader.configGUI.isPressed()) {
            api.openConfigScreen();
        }
    }

    @Mod.EventHandler
    public void start(FMLServerStartingEvent event) {
        ClientCommandHandler.instance.registerCommand(new MainCommand());
    }
}
