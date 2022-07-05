package com.hijackster99.ancientrelics.blocks;

import java.awt.Color;

import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.items.ARItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ObjectHolder;

public abstract class VoidGas extends ForgeFlowingFluid {
	
	public VoidGas() {
		super(new Properties(() -> VOID_GAS_STILL, () -> VOID_GAS_FLOWING, FluidAttributes.builder(new ResourceLocation(References.MODID, "block/void_gas_still"), new ResourceLocation(References.MODID, "block/void_gas_flow")).density(500).viscosity(20).temperature(0).gaseous().color(new Color(255, 255, 255, 191).getRGB())).bucket(() -> ARItem.VOID_GAS_BUCKET).block(() -> ARBlock.VOID_GAS_BLOCK));
	}
	
	public static class VoidGasFlowing extends VoidGas {

		public VoidGasFlowing() {
			super();
			setRegistryName("void_gas_flowing");
			registerDefaultState(getStateDefinition().any().setValue(LEVEL, 7));
		}
		
		@Override
		protected void createFluidStateDefinition(Builder<Fluid, FluidState> builder) {
			super.createFluidStateDefinition(builder);
			builder.add(LEVEL);
		}
		
		@Override
		public boolean isSource(FluidState state) {
			return false;
		}

		@Override
		public int getAmount(FluidState state) {
			return state.getValue(LEVEL);
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
		public int getAmount(FluidState state) {
			return 8;
		}
		
	}
	
	@ObjectHolder(References.MODID + ":void_gas_still")
	public static VoidGas VOID_GAS_STILL;
	
	@ObjectHolder(References.MODID + ":void_gas_flowing")
	public static VoidGas VOID_GAS_FLOWING;

}
