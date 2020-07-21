package pw.yumc.miaogui.handler;

import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.annotation.Nullable;
import javax.script.CompiledScript;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;
import pw.yumc.MiaoScript.MiaoScriptEngine;
import pw.yumc.miaogui.MiaoGUI;
import pw.yumc.miaogui.container.MiaoContainer;
import pw.yumc.miaogui.container.MiaoGuiContainer;

/**
 * @author MiaoWoo
 */
public class MiaoHandler implements IGuiHandler {
    public static int MiaoGUIID = 325;
    public static int MiaoScriptGUIID = 825;
    public static MiaoScriptEngine engine = MiaoGUI.engine.getEngine();

    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return null;
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        entityPlayer.sendMessage(new TextComponentString(
                String.format("你打开了GUI! ID: %s world: %s x: %s y: %s z: %s",
                              i,
                              world.getWorldType().getName(),
                              i1,
                              i2,
                              i3
                )));
        if (i == MiaoGUIID) {
            return new MiaoGuiContainer(new MiaoContainer(entityPlayer));
        }
        if (i == MiaoScriptGUIID) {
            ScriptContext origin = engine.getContext();
            try {
                URL jsUrl = MiaoGUI.class.getClassLoader().getResource("scripts/render.js");
                if (jsUrl != null) {
                    engine.setContext(new SimpleScriptContext());
                    engine.eval(new InputStreamReader(jsUrl.openStream()));
                    return engine.invokeFunction("createGuiContainer", new MiaoContainer(entityPlayer));
                } else {
                    MiaoGUI.logger.warn("classpath:scripts/render.js not found!");
                }
            } catch (ScriptException | NoSuchMethodException | IOException e) {
                e.printStackTrace();
            } finally {
                engine.setContext(origin);
            }
        }
        return null;
    }
}
