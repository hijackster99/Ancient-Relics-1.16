package com.hijackster99.ancientrelics.tileentity.ritual.wrappers;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.ritual.TileEntityWrapper;

import net.minecraft.block.BlockState;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class StorageWrapper extends TileEntityWrapper {

	private VoidGasTank tank;
	
	@Override
	public void init(World worldIn, BlockPos pos) {
		//VCModifier = type.contains(ARBlock.RITUAL_STONE_1_RUBY) || type.contains(ARBlock.RITUAL_STONE_6) ? 0.5 : 1;
		//VPTModifier = type.contains(ARBlock.RITUAL_STONE_1_PERIDOT) || type.contains(ARBlock.RITUAL_STONE_6) ? 40 : 20;
		tank = new VoidGasTank(type.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) || type.contains(ARBlock.RITUAL_STONE_6) ? 10000000 : 5000000);
	}
	
	@CapabilityInject(IFluidHandler.class)
	private static Capability<IFluidHandler> FLUID_CAP = null;
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return FLUID_CAP.orEmpty(cap, LazyOptional.of(() -> tank));
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		
		tank.readFromNBT(nbt);
	}
	
	@Override
	public CompoundNBT write(CompoundNBT nbt) {
		tank.writeToNBT(nbt);
		
		return super.write(nbt);
	}
	
}
