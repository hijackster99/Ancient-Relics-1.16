package com.hijackster99.ancientrelics.tileentity.ritual.wrappers;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.ritual.TileEntityWrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.level.Level;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class StorageWrapper extends TileEntityWrapper {

	private VoidGasTank tank;
	
	@Override
	public void init(Level worldIn, BlockPos pos) {
		//VCModifier = type.contains(ARBlock.RITUAL_STONE_1_RUBY) || type.contains(ARBlock.RITUAL_STONE_6) ? 0.5 : 1;
		//VPTModifier = type.contains(ARBlock.RITUAL_STONE_1_PERIDOT) || type.contains(ARBlock.RITUAL_STONE_6) ? 40 : 20;
		tank = new VoidGasTank(type.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) || type.contains(ARBlock.RITUAL_STONE_6) ? 10000000 : 5000000);
	}
	
	private static Capability<IFluidHandler> FLUID_CAP = CapabilityManager.get(new CapabilityToken<>() {});
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return FLUID_CAP.orEmpty(cap, LazyOptional.of(() -> tank));
	}
	
	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);
		
		tank.readFromNBT(nbt);
	}
	
	@Override
	public CompoundTag write(CompoundTag nbt) {
		tank.writeToNBT(nbt);
		
		return super.write(nbt);
	}
	
}
