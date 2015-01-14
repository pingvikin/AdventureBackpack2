package com.darkona.adventurebackpack.handlers;

import com.darkona.adventurebackpack.block.TileAdventureBackpack;
import com.darkona.adventurebackpack.client.gui.GuiAdvBackpack;
import com.darkona.adventurebackpack.client.gui.GuiCopterPack;
import com.darkona.adventurebackpack.inventory.BackpackContainer;
import com.darkona.adventurebackpack.inventory.CopterContainer;
import com.darkona.adventurebackpack.inventory.InventoryBackpack;
import com.darkona.adventurebackpack.inventory.InventoryCopterPack;
import com.darkona.adventurebackpack.util.Wearing;
import cpw.mods.fml.common.network.IGuiHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

/**
 * Created on 12/10/2014
 *
 * @author Darkona
 */
public class GuiHandler implements IGuiHandler
{
    public static final byte COPTER_WEARING = 4;
    public static final byte COPTER_HOLDING = 3;
    public static final byte BACKPACK_HOLDING = 2;
    public static final byte BACKPACK_WEARING = 1;
    public static final byte BACKPACK_TILE = 0;


    public GuiHandler()
    {
    }

    @Override
    public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        InventoryBackpack inv;
        InventoryCopterPack inv2;
        TileEntity te;

        switch (ID)
        {
            case BACKPACK_TILE:
                te = world.getTileEntity(x, y, z);
                if (te != null && te instanceof TileAdventureBackpack)
                {
                    return new BackpackContainer(player, (TileAdventureBackpack) te, BackpackContainer.SOURCE_TILE);
                }
                break;
            case BACKPACK_WEARING:
                inv = Wearing.getBackpackInv(player, true);
                if (inv.getParentItemStack() != null)
                {
                    return new BackpackContainer(player, inv, BackpackContainer.SOURCE_WEARING);
                }
                break;
            case BACKPACK_HOLDING:
                inv = Wearing.getBackpackInv(player, false);
                if (inv.getParentItemStack() != null)
                {
                    return new BackpackContainer(player, inv, BackpackContainer.SOURCE_HOLDING);
                }
                break;
            case COPTER_HOLDING:
                inv2 = new InventoryCopterPack(Wearing.getHoldingCopter(player));
                if (inv2.getParentItemStack() != null)
                {
                    return new CopterContainer(player, inv2);
                }
                break;
            case COPTER_WEARING:
                inv2 = new InventoryCopterPack(Wearing.getWearingCopter(player));
                if (inv2.getParentItemStack() != null)
                {
                    return new CopterContainer(player, inv2);
                }
                break;
            default:
                player.closeScreen();
                break;
        }

        return null;

    }

    @Override
    public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z)
    {
        InventoryBackpack inv;
        InventoryCopterPack inv2;
        TileEntity te;
        switch (ID)
        {
            case BACKPACK_TILE:
                te = world.getTileEntity(x, y, z);
                if (te != null && te instanceof TileAdventureBackpack)
                {
                    return new GuiAdvBackpack(player, (TileAdventureBackpack) te);
                }
                break;
            case BACKPACK_WEARING:
                inv = Wearing.getBackpackInv(player, true);
                if (inv.getParentItemStack() != null)
                {
                    return new GuiAdvBackpack(player, inv, true);
                }
                break;
            case BACKPACK_HOLDING:
                inv = Wearing.getBackpackInv(player, false);
                if (inv.getParentItemStack() != null)
                {
                    return new GuiAdvBackpack(player, inv, false);
                }
                break;
            case COPTER_HOLDING:
                inv2 = new InventoryCopterPack(Wearing.getHoldingCopter(player));
                if (inv2.getParentItemStack() != null)
                {
                    return new GuiCopterPack(player, inv2,false);
                }
                break;
            case COPTER_WEARING:
                inv2 = new InventoryCopterPack(Wearing.getWearingCopter(player));
                if (inv2.getParentItemStack() != null)
                {
                    return new GuiCopterPack(player, inv2,true);
                }
                break;
            default:
                player.closeScreen();
                break;
        }
        return null;
    }
}
