package com.darkona.adventurebackpack.network;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayerMP;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

import com.darkona.adventurebackpack.common.ServerActions;

/**
 * Created by Darkona on 12/10/2014.
 *
 * @
 */
public class CycleToolPacket implements IMessageHandler<CycleToolPacket.CycleToolMessage, IMessage>
{
    public static final byte TOGGLE_HOSE_TANK = 0;
    public static final byte SWITCH_HOSE_ACTION = 1;
    public static final byte CYCLE_TOOL_ACTION = 2;

    @Override
    public IMessage onMessage(CycleToolMessage message, MessageContext ctx)
    {
        if (ctx.side.isServer())
        {
            //LogHelper.info("Received CycleToolMessage with values: " + message.directionOfCycle + " " + message.typeOfAction + " " + message.slot);
            EntityPlayerMP player = ctx.getServerHandler().playerEntity;
            switch (message.typeOfAction)
            {
                case CYCLE_TOOL_ACTION:
                    ServerActions.cycleTool(player, message.directionOfCycle, message.slot);
                    break;
                case TOGGLE_HOSE_TANK:
                    ServerActions.switchHose(player, message.directionOfCycle, ServerActions.HOSE_TOGGLE);
                    break;
                case SWITCH_HOSE_ACTION:
                    ServerActions.switchHose(player, message.directionOfCycle, ServerActions.HOSE_SWITCH);
                    break;
            }
        }
        return null;
    }

    public static class CycleToolMessage implements IMessage
    {
        private int directionOfCycle;
        private int slot;
        private byte typeOfAction;

        public CycleToolMessage()
        {

        }

        public CycleToolMessage(int directionOfCycle, int slot, byte typeOfAction)
        {
            this.typeOfAction = typeOfAction;
            this.directionOfCycle = directionOfCycle;
            this.slot = slot;
        }

        @Override
        public void fromBytes(ByteBuf buf)
        {
            this.typeOfAction = buf.readByte();
            this.directionOfCycle = buf.readInt();
            this.slot = buf.readInt();
        }

        @Override
        public void toBytes(ByteBuf buf)
        {
            buf.writeByte(typeOfAction);
            buf.writeInt(directionOfCycle);
            buf.writeInt(slot);
        }
    }
}
