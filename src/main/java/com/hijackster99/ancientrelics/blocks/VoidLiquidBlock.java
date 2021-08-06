package com.hijackster99.ancientrelics.blocks;

import java.util.function.Supplier;

import net.minecraft.block.FlowingFluidBlock;
import net.minecraft.block.material.Material;
import net.minecraft.fluid.FlowingFluid;

public class VoidLiquidBlock extends FlowingFluidBlock {

	public VoidLiquidBlock(Supplier<? extends FlowingFluid> supplier, String registryName, Material materialIn, float hardnessIn, float resistanceIn) {
		super(supplier, Properties.of(materialIn).strength(hardnessIn, resistanceIn).noCollission().noDrops());
		setRegistryName(registryName);
	}
	
}
