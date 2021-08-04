package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.voidrelay.VoidRelay;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class SapphireStaff extends ARItem{

	public SapphireStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		TileEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
		if(te instanceof VoidRelay) {
			VoidRelay relay = (VoidRelay) te;
			if(!context.getLevel().isClientSide()) {
				context.getPlayer().sendMessage(new StringTextComponent("Connections: " + relay.getConnections().toString()), context.getPlayer().getUUID());
				context.getPlayer().sendMessage(new StringTextComponent("Void In: " + (relay.getVoidIn() == null ? "null" : relay.getVoidIn().toString())), context.getPlayer().getUUID());
				context.getPlayer().sendMessage(new StringTextComponent("Void Out: " + (relay.getVoidOut() == null ? "null" : relay.getVoidOut().toString())), context.getPlayer().getUUID());
			}
			return ActionResultType.SUCCESS;
		}else if(te instanceof ICapabilityProvider) {
			ICapabilityProvider prov = te;
			LazyOptional<IFluidHandler> opt = prov.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP);
			IFluidHandler tank = opt.orElse(null);
			if(tank != null) {
				if(tank instanceof VoidGasTank) {
					context.getPlayer().displayClientMessage(new StringTextComponent("Void Energy: " + Integer.toString(tank.getFluidInTank(0).getAmount()) + "/" + Integer.toString(tank.getTankCapacity(0))), true);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.useOn(context);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isCrouching()) {
			if(handIn == Hand.MAIN_HAND) {
				playerIn.inventory.setItem(playerIn.inventory.selected, new ItemStack(ARItem.PERIDOT_STAFF, 1));
			}else {
				playerIn.inventory.setItem(45, new ItemStack(ARItem.PERIDOT_STAFF, 1));
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}

}
