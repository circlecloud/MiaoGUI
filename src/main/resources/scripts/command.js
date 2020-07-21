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

function execute(minecraftServer, iCommandSender, args) {
    args = Java.from(args);
    if (!args[0]) {
        return iCommandSender.sendMessage(new TextComponentString("命令测试!"));
    }
    var sub = args.shift();
    switch (sub) {
        case "run":
            var result = eval(args.join(' '));
            return iCommandSender.sendMessage(new TextComponentString(result + ''));
        case "open":
            new DelayedTask(new Runnable(function () {
                player.openGui(MiaoGUI.INSTANCE, args[0] ? MiaoHandler.MiaoScriptGUIID : MiaoHandler.MiaoGUIID, player.world, 0, 0, 0);
            }), 1);
            break;
        case "clear":
            MiaoGUI.api.clear();
            break;
        case "add":
            var type = args.shift();
            switch (type) {
                case "text":
                    MiaoGUI.api.register(new TextRenderer(args.join(' ')));
                    break;
                case "image":
                    MiaoGUI.api.register(new Image9Renderer(args[0]));
                    break;
                case "Broadcast":
                    MiaoGUI.api.register(new BroadcastRenderer());
                    break;
                default:
                    iCommandSender.sendMessage(new TextComponentString("Unknown render type " + type));
            }
            break;
        case "config":
            new DelayedTask(new Runnable(function () {
                MiaoGUI.api.openConfigScreen();
            }), 1);
            break;
        case "reload":
            break;
        default:
    }
}
