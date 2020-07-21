package pw.yumc.miaogui.command;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.annotation.Nonnull;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import net.minecraft.command.CommandBase;
import net.minecraft.command.CommandException;
import net.minecraft.command.ICommandSender;
import net.minecraft.server.MinecraftServer;
import pw.yumc.MiaoScript.MiaoScriptEngine;
import pw.yumc.miaogui.MiaoGUI;

public class MainCommand extends CommandBase {
    private static MiaoScriptEngine engine = MiaoGUI.engine.getEngine();

    @Nonnull
    @Override
    public String getName() {
        return "MiaoGUI";
    }

    @Nonnull
    @Override
    public String getUsage(@Nonnull ICommandSender iCommandSender) {
        return "/MiaoGUI open name";
    }

    @Override
    public void execute(@Nonnull MinecraftServer minecraftServer, @Nonnull ICommandSender iCommandSender, @Nonnull String[] strings) throws CommandException {
        URL jsUrl = MiaoGUI.class.getClassLoader().getResource("scripts/command.js");
        if (jsUrl != null) {
            ScriptContext origin = engine.getContext();
            try {
                InputStream input = jsUrl.openStream();
                engine.setContext(new SimpleScriptContext());
                engine.eval(new InputStreamReader(input));
                engine.invokeFunction("execute", minecraftServer, iCommandSender, strings);
            } catch (IOException | ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                engine.setContext(origin);
            }
        } else {
            MiaoGUI.logger.warn("classpath:scripts/command.js not found!");
        }
    }
}
