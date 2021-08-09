package com.hijackster99.ancientrelics.tileentity.ritual.wrappers;

import java.util.Optional;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.crafting.InfuseCraftInv;
import com.hijackster99.ancientrelics.crafting.InfuseRecipe;
import com.hijackster99.ancientrelics.tileentity.ritual.TileEntityWrapper;

import net.minecraft.tags.BlockTags;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class InfuseWrapper extends TileEntityWrapper {
	
	double VCModifier;
	int VPTModifier;
	
	private VoidGasTank tank;
	
	InfuseRecipe recipe;
	int voidEnergy;
	
	@CapabilityInject(IFluidHandler.class)
	private static Capability<IFluidHandler> FLUID_CAP = null;
	
	@Override
	public void init(World worldIn, BlockPos pos) {
		VCModifier = type.contains(ARBlock.RITUAL_STONE_1_RUBY) || type.contains(ARBlock.RITUAL_STONE_6) ? 0.5 : 1;
		VPTModifier = type.contains(ARBlock.RITUAL_STONE_1_PERIDOT) || type.contains(ARBlock.RITUAL_STONE_6) ? 40 : 20;
		tank = new VoidGasTank(type.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) || type.contains(ARBlock.RITUAL_STONE_6) ? 20000 : 10000);
		recipe = null;
		voidEnergy = 0;
	}
	
	@Override
	public void tick(World worldObj, BlockPos pos) {
		
		InfuseCraftInv inv = new InfuseCraftInv(1, Integer.valueOf(BlockTags.getAllTags().getId(ritual.getTier()).getPath().charAt(BlockTags.getAllTags().getId(ritual.getTier()).getPath().length() - 1)));
		if(recipe == null) {
			Optional<InfuseRecipe> r = worldObj.getRecipeManager().getRecipeFor(InfuseRecipe.INFUSE_RECIPE, inv, worldObj);
			if(r.isPresent()) {
				recipe = r.get();
				voidEnergy = recipe.getVoidCost();
			}
		}else {
			if(voidEnergy <= 0) {
				craft();
				recipe = null;
			}else {
				if(tank.getFluidInTank(0).getAmount() < VPTModifier) {
					if(voidEnergy < tank.getFluidInTank(0).getAmount()) {
						tank.drain(voidEnergy, FluidAction.EXECUTE);
						voidEnergy = 0;
					}else {
						voidEnergy -= tank.getFluidInTank(0).getAmount();
						tank.drain(tank.getFluidInTank(0).getAmount(), FluidAction.EXECUTE);
					}
				}else {
					if(voidEnergy < VPTModifier) {
						tank.drain(voidEnergy, FluidAction.EXECUTE);
						voidEnergy = 0;
					}else {
						voidEnergy -= VPTModifier;
						tank.drain(VPTModifier, FluidAction.EXECUTE);
					}
				}
			}
		}
	}
	
	private void craft() {
		
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return FLUID_CAP.orEmpty(cap, LazyOptional.of(() -> tank));
	}
	
}
