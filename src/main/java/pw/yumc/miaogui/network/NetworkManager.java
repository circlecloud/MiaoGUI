package pw.yumc.miaogui.network;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;

import javax.script.ScriptContext;
import javax.script.ScriptException;
import javax.script.SimpleScriptContext;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.play.client.CPacketCustomPayload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.FMLNetworkEvent;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import pw.yumc.MiaoScript.MiaoScriptEngine;
import pw.yumc.miaogui.MiaoGUI;

/**
 * @author MiaoWoo
 */
public class NetworkManager {
    public static String CHANNEL = "MiaoGUI|Event";
    private MiaoScriptEngine engine = MiaoGUI.engine.getEngine();

    public static void sendPacket(String channel, byte[] bytes) {
        EntityPlayerSP player = Minecraft.getMinecraft().player;
        NetHandlerPlayClient connection = player.connection;
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        packetBuffer.writeByteArray(bytes);
        connection.sendPacket(new CPacketCustomPayload(channel, packetBuffer));
    }

    @SubscribeEvent
    public void onClientReceivePacket(FMLNetworkEvent.ClientCustomPacketEvent clientCustomPacketEvent) {
        FMLProxyPacket fmlProxyPacket = clientCustomPacketEvent.getPacket();
        String channel = fmlProxyPacket.channel();
        ByteBuf payload = fmlProxyPacket.payload();
        byte[] bytes = new byte[payload.readableBytes()];
        payload.readBytes(bytes);
        URL jsUrl = MiaoGUI.class.getClassLoader().getResource("scripts/event.js");
        if (jsUrl != null) {
            ScriptContext origin = engine.getContext();
            try {
                InputStream input = jsUrl.openStream();
                engine.setContext(new SimpleScriptContext());
                engine.eval(new InputStreamReader(input));
                engine.invokeFunction("event", new String(bytes));
            } catch (IOException | ScriptException | NoSuchMethodException e) {
                e.printStackTrace();
            } finally {
                engine.setContext(origin);
            }
        } else {
            MiaoGUI.logger.warn("classpath:scripts/event.js not found!");
        }
    }
}
