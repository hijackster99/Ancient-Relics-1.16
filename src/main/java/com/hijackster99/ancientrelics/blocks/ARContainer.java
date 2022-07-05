package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.References;

import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.material.Material;

public abstract class ARContainer extends BaseEntityBlock {

	public ARContainer(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
		super(requiresTool ? Properties.of(materialIn).strength(hardnessIn, resistanceIn).requiresCorrectToolForDrops() : Properties.of(materialIn).strength(hardnessIn, resistanceIn));
		setRegistryName(References.MODID, registryName);
	}
	
	protected ARContainer(Properties prop) {
		super(prop);
	}

}
