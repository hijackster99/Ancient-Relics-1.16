package com.hijackster99.ancientrelics.Blocks;

import com.hijackster99.ancientrelics.core.EnumRitualType;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class RitualBlock extends ARBlock{

	int tier;
	EnumRitualType type;
	
	public RitualBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool, int tier, EnumRitualType type) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
		this.tier = tier;
		this.type = type;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return ActionResultType.FAIL;
	}

}
