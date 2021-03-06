package com.darkona.adventurebackpack.client.gui;

import org.lwjgl.input.Mouse;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;

import com.darkona.adventurebackpack.common.Constants.Source;
import com.darkona.adventurebackpack.config.Keybindings;
import com.darkona.adventurebackpack.init.ModNetwork;
import com.darkona.adventurebackpack.network.EquipUnequipBackWearablePacket;

/**
 * Created on 06/01/2015
 *
 * @author Darkona
 */
public abstract class GuiWithTanks extends GuiContainer
{
    protected EntityPlayer player;
    protected Source source;

    GuiWithTanks(Container container)
    {
        super(container);
    }

    int getLeft()
    {
        return guiLeft;
    }

    int getTop()
    {
        return guiTop;
    }

    float getZLevel()
    {
        return zLevel;
    }

    abstract protected GuiImageButtonNormal getEquipButton();

    abstract protected GuiImageButtonNormal getUnequipButton();

    @Override
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton)
    {
        //int sneakKey = Minecraft.getMinecraft().gameSettings.keyBindSneak.getKeyCode();
        if (source == Source.WEARING)
        {
            if (getUnequipButton().inButton(this, mouseX, mouseY))
            {
                ModNetwork.net.sendToServer(new EquipUnequipBackWearablePacket.Message(EquipUnequipBackWearablePacket.UNEQUIP_WEARABLE, false));
                player.closeScreen();
            }
        } else if (source == Source.HOLDING)
        {
            if (getEquipButton().inButton(this, mouseX, mouseY))
            {
                ModNetwork.net.sendToServer(new EquipUnequipBackWearablePacket.Message(EquipUnequipBackWearablePacket.EQUIP_WEARABLE, false));
                //ModNetwork.net.sendToServer(new EquipUnequipBackWearablePacket.Message(EquipUnequipBackWearablePacket.EQUIP_WEARABLE, Keyboard.isKeyDown(sneakKey)));
                player.closeScreen();
            }
        }

        super.mouseClicked(mouseX, mouseY, mouseButton);
    }

    @Override
    protected void keyTyped(char key, int keycode)
    {
        if (keycode == Keybindings.openInventory.getKeyCode())
        {
            player.closeScreen();
        }

        super.keyTyped(key, keycode);
    }

    @Override
    public void handleMouseInput()
    {
        if (Mouse.getEventDWheel() != 0)
        {
            return; // forbid mouseWheel, preventing glitches with Shift+Wheel on fluid containers and so on
        }

        super.handleMouseInput();
    }
}
