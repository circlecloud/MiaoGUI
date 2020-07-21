var TextComponentString = Java.type("net.minecraft.util.text.TextComponentString");
var Runnable = Java.type("java.lang.Runnable");

var Minecraft = Java.type('net.minecraft.client.Minecraft');
var MiaoGUI = Java.type('pw.yumc.miaogui.MiaoGUI');
var MiaoHandler = Java.type('pw.yumc.miaogui.handler.MiaoHandler');
var DelayedTask = Java.type('pw.yumc.miaogui.task.DelayedTask');
var TextRenderer = Java.type('pw.yumc.miaogui.render.TextRenderer');
var Image9Renderer = Java.type('pw.yumc.miaogui.render.Image9Renderer');
var BroadcastRenderer = Java.type('pw.yumc.miaogui.render.BroadcastRenderer');
var NetworkManager = Java.type('pw.yumc.miaogui.network.NetworkManager');

var mc = Minecraft.getMinecraft();
var player = mc.player;

function event(string) {
    var event = JSON.parse(string);
    var data = event.data;
    switch (event.type) {
        case "run":
            var result = eval(data.cmd);
            return player.sendMessage(new TextComponentString(result + ''));
        case "open":
            new DelayedTask(new Runnable(function () {
                player.openGui(MiaoGUI.INSTANCE, data.name, player.world, 0, 0, 0);
            }), 1);
            break;
        case "clear":
            MiaoGUI.api.clear();
            break;
        case "add":
            switch (data.type) {
                case "text":
                    MiaoGUI.api.register(new TextRenderer(data.param.join ? data.param.join(' ') : data.param));
                    break;
                case "image":
                    MiaoGUI.api.register(new Image9Renderer(data.param[0]));
                    break;
                case "broadcast":
                    MiaoGUI.api.register(new BroadcastRenderer());
                    break;
                default:
                    player.sendMessage(new TextComponentString("Unknown render type " + type));
            }
            break;
        case "config":
            new DelayedTask(new Runnable(function () {
                MiaoGUI.api.openConfigScreen();
            }), 1);
            break;
        default:
    }
}
