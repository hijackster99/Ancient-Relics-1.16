package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.voidrelay.VoidRelay;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUseContext;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PeridotStaff extends ARItem{
	
	public PeridotStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResult<ItemStack> use(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isCrouching()) {
			if(playerIn.getItemInHand(handIn).hasTag()) {
				playerIn.getItemInHand(handIn).getTag().remove("block");
			}else {
				if(handIn == Hand.MAIN_HAND) {
					playerIn.inventory.setItem(playerIn.inventory.selected, new ItemStack(ARItem.RUBY_STAFF, 1));
				}else {
					playerIn.inventory.setItem(45, new ItemStack(ARItem.RUBY_STAFF, 1));
				}
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}
	
	@Override
	public ActionResultType useOn(ItemUseContext context) {
		TileEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
		if(te != null) {
			if(te instanceof VoidRelay) {
				if(context.getPlayer().isCrouching()) {
					CompoundNBT tag = new CompoundNBT();
					tag.putString("dimension", context.getLevel().dimension().location().toString());
					tag.putString("type", "relay");
					tag.putInt("x", context.getClickedPos().getX());
					tag.putInt("y", context.getClickedPos().getY());
					tag.putInt("z", context.getClickedPos().getZ());
					context.getItemInHand().getOrCreateTag().put("block", tag);
				}else {
					if(context.getItemInHand().hasTag() && context.getItemInHand().getTag().contains("block")) {
						CompoundNBT tag = context.getItemInHand().getTag().getCompound("block");
						if(context.getLevel().dimension().location().toString().equals(tag.getString("dimension"))) {
							if(tag.getString("type").equals("relay")) {
								TileEntity te2 = context.getLevel().getBlockEntity(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
								if(te2 instanceof VoidRelay && !context.getLevel().isClientSide()) {
									if(((VoidRelay) te2).getConnections().contains(te.getBlockPos()))
										((VoidRelay) te).breakConnection((VoidRelay) te2);
									else
										((VoidRelay) te).addToNet((VoidRelay) te2, context.getPlayer());
								}
							}else if(tag.getString("type").equals("void_energy")) {
								TileEntity te2 = context.getLevel().getBlockEntity(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
								if(te2 instanceof ICapabilityProvider) {
									IFluidHandler tank = te2.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
									if(tank != null && tank instanceof VoidGasTank && !context.getLevel().isClientSide()) {
										if(((VoidRelay) te).getVoidIn() == te2) 
											((VoidRelay) te).removeVoidIn();
										else
											((VoidRelay) te).setNetVoidIn(te2);
									}
								}
							}
						}
					}
				}
				return ActionResultType.SUCCESS;
			}else if(te instanceof ICapabilityProvider) {
				if(context.getPlayer().isCrouching()) {
					IFluidHandler tank = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
					if(tank != null && tank instanceof VoidGasTank) {
						CompoundNBT tag = new CompoundNBT();
						tag.putString("dimension", context.getLevel().dimension().location().toString());
						tag.putString("type", "void_energy");
						tag.putInt("x", context.getClickedPos().getX());
						tag.putInt("y", context.getClickedPos().getY());
						tag.putInt("z", context.getClickedPos().getZ());
						context.getItemInHand().getOrCreateTag().put("block", tag);
					}else {
						if(context.getItemInHand().hasTag()) {
							context.getItemInHand().getTag().remove("block");
						}
					}
				}else {
					IFluidHandler tank = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
					if(tank != null && tank instanceof VoidGasTank) {
						if(context.getItemInHand().hasTag() && context.getItemInHand().getTag().contains("block")) {
							CompoundNBT tag = context.getItemInHand().getTag().getCompound("block");
							if(context.getLevel().dimension().location().toString().equals(tag.getString("dimension")) && tag.getString("type").equals("relay")) {
								TileEntity te2 = context.getLevel().getBlockEntity(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
								if(te2 instanceof VoidRelay && !context.getLevel().isClientSide()) {
									if(((VoidRelay) te2).getVoidOut() == te) 
										((VoidRelay) te2).removeVoidOut();
									else
										((VoidRelay) te2).setNetVoidOut(te);
								}
							}
						}
					}
				}
				return ActionResultType.SUCCESS;
			}else {
				if(context.getPlayer().isCrouching()) {
					if(context.getItemInHand().hasTag()) {
						context.getItemInHand().getTag().remove("block");
						return ActionResultType.SUCCESS;
					}
				}
			}
		}else {
			if(context.getPlayer().isCrouching()) {
				if(context.getItemInHand().hasTag()) {
					context.getItemInHand().getTag().remove("block");
					return ActionResultType.SUCCESS;
				}
			}
		}
		return super.useOn(context);
	}

}
