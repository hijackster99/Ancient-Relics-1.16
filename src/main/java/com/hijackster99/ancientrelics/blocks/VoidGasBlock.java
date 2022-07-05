package com.hijackster99.ancientrelics.blocks;

import java.util.function.Supplier;

import net.minecraft.world.level.block.LiquidBlock;
import net.minecraft.world.level.material.FlowingFluid;
import net.minecraft.world.level.material.Material;

public class VoidGasBlock extends LiquidBlock {

	public VoidGasBlock(Supplier<? extends FlowingFluid> supplier, String registryName, Material materialIn, float hardnessIn, float resistanceIn) {
		super(supplier, Properties.of(materialIn).strength(hardnessIn, resistanceIn).noCollission().noDrops());
		setRegistryName(registryName);
	}
	
}
