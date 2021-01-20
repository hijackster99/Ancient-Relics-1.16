package com.hijackster99.ancientrelics.blocks;

import net.minecraft.fluid.FluidState;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;

public class VoidGas extends ForgeFlowingFluid {

	public VoidGas() {
		super(new Properties(VoidGasStill::new, VoidGasFlowing::new, FluidAttributes.builder(new ResourceLocation(""), new ResourceLocation(""))));
	}

	@Override
	public boolean isSource(FluidState state) {
		return state.isSource();
	}

	@Override
	public int getLevel(FluidState state) {
		return state.isSource() ? 8 : state.get(LEVEL_1_8);
	} 
	
	public static class VoidGasFlowing extends VoidGas {

		protected VoidGasFlowing() {
			super();
		}
	}
	
	public static class VoidGasStill extends VoidGas {

		public VoidGasStill() {
			super();
		}
		
	}

}
