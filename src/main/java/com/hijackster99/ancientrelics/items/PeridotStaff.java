package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.voidrelay.VoidRelay;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class PeridotStaff extends ARItem{
	
	public PeridotStaff(String registryName, int maxStack, CreativeModeTab tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public InteractionResultHolder<ItemStack> use(Level worldIn, Player playerIn, InteractionHand handIn) {
		if(playerIn.isCrouching()) {
			if(playerIn.getItemInHand(handIn).hasTag()) {
				playerIn.getItemInHand(handIn).getTag().remove("block");
			}else {
				if(handIn == InteractionHand.MAIN_HAND) {
					playerIn.getInventory().setItem(playerIn.getInventory().selected, new ItemStack(ARItem.RUBY_STAFF, 1));
				}else {
					playerIn.getInventory().setItem(45, new ItemStack(ARItem.RUBY_STAFF, 1));
				}
			}
		}
		return super.use(worldIn, playerIn, handIn);
	}
	
	@Override
	public InteractionResult useOn(UseOnContext context) {
		BlockEntity te = context.getLevel().getBlockEntity(context.getClickedPos());
		if(te != null) {
			if(te instanceof VoidRelay) {
				if(context.getPlayer().isCrouching()) {
					CompoundTag tag = new CompoundTag();
					tag.putString("dimension", context.getLevel().dimension().location().toString());
					tag.putString("type", "relay");
					tag.putInt("x", context.getClickedPos().getX());
					tag.putInt("y", context.getClickedPos().getY());
					tag.putInt("z", context.getClickedPos().getZ());
					context.getItemInHand().getOrCreateTag().put("block", tag);
				}else {
					if(context.getItemInHand().hasTag() && context.getItemInHand().getTag().contains("block")) {
						CompoundTag tag = context.getItemInHand().getTag().getCompound("block");
						if(context.getLevel().dimension().location().toString().equals(tag.getString("dimension"))) {
							if(tag.getString("type").equals("relay")) {
								BlockEntity te2 = context.getLevel().getBlockEntity(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
								if(te2 instanceof VoidRelay && !context.getLevel().isClientSide()) {
									if(((VoidRelay) te2).getConnections().contains(te.getBlockPos()))
										((VoidRelay) te).breakConnection((VoidRelay) te2);
									else
										((VoidRelay) te).addToNet((VoidRelay) te2, context.getPlayer());
								}
							}else if(tag.getString("type").equals("void_energy")) {
								BlockEntity te2 = context.getLevel().getBlockEntity(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
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
				return InteractionResult.SUCCESS;
			}else if(te instanceof ICapabilityProvider) {
				if(context.getPlayer().isCrouching()) {
					IFluidHandler tank = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
					if(tank != null && tank instanceof VoidGasTank) {
						CompoundTag tag = new CompoundTag();
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
							CompoundTag tag = context.getItemInHand().getTag().getCompound("block");
							if(context.getLevel().dimension().location().toString().equals(tag.getString("dimension")) && tag.getString("type").equals("relay")) {
								BlockEntity te2 = context.getLevel().getBlockEntity(new BlockPos(tag.getInt("x"), tag.getInt("y"), tag.getInt("z")));
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
				return InteractionResult.SUCCESS;
			}else {
				if(context.getPlayer().isCrouching()) {
					if(context.getItemInHand().hasTag()) {
						context.getItemInHand().getTag().remove("block");
						return InteractionResult.SUCCESS;
					}
				}
			}
		}else {
			if(context.getPlayer().isCrouching()) {
				if(context.getItemInHand().hasTag()) {
					context.getItemInHand().getTag().remove("block");
					return InteractionResult.SUCCESS;
				}
			}
		}
		return super.useOn(context);
	}

}
