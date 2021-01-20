package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.References;

import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ObjectHolder;

public abstract class VoidGas extends ForgeFlowingFluid {

	public VoidGas() {
		super(new Properties(VoidGasStill::new, VoidGasFlowing::new, FluidAttributes.builder(new ResourceLocation("ancientrelics:void_gas_still"), new ResourceLocation("ancientrelics:void_gas_flowing")).density(1).viscosity(300).temperature(40).gaseous()));
	}
	
	public static class VoidGasFlowing extends VoidGas {

		public VoidGasFlowing() {
			super();
			setRegistryName("void_gas_flowing");
			setDefaultState(stateContainer.getBaseState().with(LEVEL_1_8, 7));
		}
		
		@Override
		protected void fillStateContainer(Builder<Fluid, FluidState> builder) {
			super.fillStateContainer(builder);
			builder.add(LEVEL_1_8);
		}

		@Override
		public boolean isSource(FluidState state) {
			return false;
		}

		@Override
		public int getLevel(FluidState state) {
			return state.get(LEVEL_1_8);
		}
	}
	
	public static class VoidGasStill extends VoidGas {

		public VoidGasStill() {
			super();
			setRegistryName("void_gas_still");
		}

		@Override
		public boolean isSource(FluidState state) {
			return true;
		}

		@Override
		public int getLevel(FluidState state) {
			return 8;
		}
		
	}
	
	@ObjectHolder(References.MODID + ":void_gas_still")
	public static Fluid VOID_GAS_STILL;
	
	@ObjectHolder(References.MODID + ":void_gas_flowing")
	public static Fluid VOID_GAS_FLOWING;

}
