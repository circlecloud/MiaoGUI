package pw.yumc.miaogui.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.util.text.TextComponentString;

/**
 * @author MiaoWoo
 */
public class MiaoContainer extends Container {
    private EntityPlayer player;

    public MiaoContainer(EntityPlayer player) {
        this.player = player;
    }

    @Override
    public boolean canInteractWith(EntityPlayer entityPlayer) {
        return true;
    }

    @Override
    public void onContainerClosed(EntityPlayer entityPlayer) {
        super.onContainerClosed(entityPlayer);
        entityPlayer.sendMessage(new TextComponentString("你关闭了GUI!"));
    }
}
