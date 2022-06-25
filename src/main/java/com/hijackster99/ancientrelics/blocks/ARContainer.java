package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.References;

import net.minecraft.block.ContainerBlock;
import net.minecraft.block.material.Material;
import net.minecraftforge.common.ToolType;

public abstract class ARContainer extends ContainerBlock {

	public ARContainer(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(Properties.of(materialIn).strength(hardnessIn, resistanceIn).harvestTool(harvestTool).harvestLevel(miningLevel).requiresCorrectToolForDrops());
		setRegistryName(References.MODID, registryName);
	}
	
	public ARContainer(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel) {
		super(Properties.of(materialIn).strength(hardnessIn, resistanceIn).harvestTool(harvestTool).harvestLevel(miningLevel));
		setRegistryName(References.MODID, registryName);
	}

}
