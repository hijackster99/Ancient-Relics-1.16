package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.core.VoidGasTank;

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
	public ActionResultType onItemUse(ItemUseContext context) {
		TileEntity te = context.getWorld().getTileEntity(context.getPos());
		if(te instanceof ICapabilityProvider) {
			ICapabilityProvider prov = te;
			LazyOptional<IFluidHandler> opt = prov.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, Direction.UP);
			IFluidHandler tank = opt.orElse(null);
			if(tank != null) {
				if(tank instanceof VoidGasTank) {
					context.getPlayer().sendStatusMessage(new StringTextComponent("Void Energy: " + Integer.toString(tank.getFluidInTank(0).getAmount())), true);
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.onItemUse(context);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isSneaking()) {
			if(handIn == Hand.MAIN_HAND) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(ARItem.PERIDOT_STAFF, 1));
			}else {
				playerIn.inventory.setInventorySlotContents(45, new ItemStack(ARItem.PERIDOT_STAFF, 1));
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
