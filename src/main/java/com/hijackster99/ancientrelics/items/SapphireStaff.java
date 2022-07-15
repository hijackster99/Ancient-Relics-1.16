package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.voidrelay.VoidRelay;

import net.minecraft.core.Direction;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class SapphireStaff extends ARItem{

	public SapphireStaff(String registryName, int maxStack, CreativeModeTab tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
		if(te instanceof VoidRelay) {
			VoidRelay relay = (VoidRelay) te;
			if(!context.getLevel().isClientSide()) {
				context.getPlayer().sendMessage(new TextComponent("Connections: " + relay.getConnections().toString()), context.getPlayer().getUUID());
				context.getPlayer().sendMessage(new TextComponent("Void In: " + (relay.getVoidIn() == null ? "null" : relay.getVoidIn().toString())), context.getPlayer().getUUID());
				context.getPlayer().sendMessage(new TextComponent("Void Out: " + (relay.getVoidOut() == null ? "null" : relay.getVoidOut().toString())), context.getPlayer().getUUID());
			}
			return InteractionResult.SUCCESS;
		}else if(te instanceof ICapabilityProvider) {
			ICapabilityProvider prov = te;
			LazyOptional<IFluidHandler> opt = prov.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP);
			IFluidHandler tank = opt.orElse(null);
			if(tank != null) {
				if(tank instanceof VoidGasTank) {
					context.getPlayer().displayClientMessage(new TextComponent("Void Energy: " + Integer.toString(tank.getFluidInTank(0).getAmount()) + "/" + Integer.toString(tank.getTankCapacity(0))), true);
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.useOn(context);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if(playerIn.isCrouching()) {
			if(handIn == InteractionHand.MAIN_HAND) {
				playerIn.getInventory().setItem(playerIn.getInventory().selected, new ItemStack(ARItem.PERIDOT_STAFF, 1));
			}else {
				playerIn.getInventory().setItem(45, new ItemStack(ARItem.PERIDOT_STAFF, 1));
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}
