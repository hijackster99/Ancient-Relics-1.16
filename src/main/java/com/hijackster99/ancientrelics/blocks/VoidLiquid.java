package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.items.ARItem;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraftforge.fluids.FluidAttributes;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import net.minecraftforge.registries.ObjectHolder;

public abstract class VoidLiquid extends ForgeFlowingFluid {
	
	public VoidLiquid() {
		super(new Properties(() -> VOID_LIQUID_STILL, () -> VOID_LIQUID_FLOWING, FluidAttributes.builder(new ResourceLocation(References.MODID, "block/void_liquid_still"), new ResourceLocation(References.MODID, "block/void_liquid_flow")).density(2000).viscosity(80).temperature(0)).bucket(() -> ARItem.VOID_LIQUID_BUCKET).block(() -> ARBlock.VOID_LIQUID_BLOCK).levelDecreasePerBlock(2).tickRate(20).slopeFindDistance(2));
	}
	
	public static class VoidLiquidFlowing extends VoidLiquid {

		public VoidLiquidFlowing() {
			super();
			setRegistryName("void_liquid_flowing");
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
	
	public static class VoidLiquidStill extends VoidLiquid {

		public VoidLiquidStill() {
			super();
			setRegistryName("void_liquid_still");
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
	
	@ObjectHolder(References.MODID + ":void_liquid_still")
	public static VoidLiquid VOID_LIQUID_STILL;
	
	@ObjectHolder(References.MODID + ":void_liquid_flowing")
	public static VoidLiquid VOID_LIQUID_FLOWING;

}
