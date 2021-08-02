package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class RelicBlock extends ShardBlock{

	public RelicBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape1 = VoxelShapes.box(0.375, 0, 0.4375, 0.625, 0.125, 0.5625);
		VoxelShape shape2 = VoxelShapes.box(0.4375, 0, 0.375, 0.5625, 0.125, 0.4375);
		VoxelShape shape3 = VoxelShapes.box(0.4375, 0, 0.5625, 0.5625, 0.125, 0.625);
		VoxelShape shape4 = VoxelShapes.box(0.3125, 0, 0.375, 0.6875, 0.0625, 0.625);
		VoxelShape shape5 = VoxelShapes.box(0.375, 0, 0.3125, 0.625, 0.0625, 0.6875);
		VoxelShape shape6 = VoxelShapes.box(0.25, 0, 0.4375, 0.75, 0.0625, 0.5625);
		VoxelShape shape7 = VoxelShapes.box(0.4375, 0, 0.25, 0.5625, 0.0625, 0.75);
		shape1 = Util.rotateShape(Direction.UP, state.getValue(property), shape1);
		shape2 = Util.rotateShape(Direction.UP, state.getValue(property), shape2);
		shape3 = Util.rotateShape(Direction.UP, state.getValue(property), shape3);
		shape4 = Util.rotateShape(Direction.UP, state.getValue(property), shape4);
		shape5 = Util.rotateShape(Direction.UP, state.getValue(property), shape5);
		shape6 = Util.rotateShape(Direction.UP, state.getValue(property), shape6);
		shape7 = Util.rotateShape(Direction.UP, state.getValue(property), shape7);
		VoxelShape shape = VoxelShapes.join(shape1, shape2, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape3, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape4, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape5, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape6, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape7, IBooleanFunction.OR);
		return shape;
	}

}
