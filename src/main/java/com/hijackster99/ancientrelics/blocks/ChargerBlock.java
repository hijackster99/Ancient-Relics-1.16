package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ChargerBlock extends ARBlock{

	public ChargerBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
	}
	
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(worldIn.getBlockState(pos.down()).getBlock() != ARBlock.PEDESTAL) worldIn.destroyBlock(pos, true);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape1 = VoxelShapes.create(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875);
		VoxelShape shape2 = VoxelShapes.create(0.375, 0.0625, 0.375, 0.625, 0.125, 0.625);
		VoxelShape shape3 = VoxelShapes.create(0.4375, 0.125, 0.4375, 0.5625, 0.625, 0.5625);
		VoxelShape shape4 = VoxelShapes.create(0.125, 0.625, 0.125, 0.875, 0.6875, 0.875);
		VoxelShape shape5 = VoxelShapes.create(0.125, 0.6875, 0.125, 0.1875, 0.8125, 0.1875);
		VoxelShape shape6 = VoxelShapes.create(0.1875, 0.6875, 0.125, 0.25, 0.75, 0.1875);
		VoxelShape shape7 = VoxelShapes.create(0.125, 0.6875, 0.1875, 0.1875, 0.75, 0.25);
		VoxelShape shape8 = Util.rotateShape(Direction.NORTH, Direction.EAST, shape5);
		VoxelShape shape9 = Util.rotateShape(Direction.NORTH, Direction.EAST, shape6);
		VoxelShape shape10 = Util.rotateShape(Direction.NORTH, Direction.EAST, shape7);
		VoxelShape shape11 = Util.rotateShape(Direction.NORTH, Direction.SOUTH, shape5);
		VoxelShape shape12 = Util.rotateShape(Direction.NORTH, Direction.SOUTH, shape6);
		VoxelShape shape13 = Util.rotateShape(Direction.NORTH, Direction.SOUTH, shape7);
		VoxelShape shape14 = Util.rotateShape(Direction.NORTH, Direction.WEST, shape5);
		VoxelShape shape15 = Util.rotateShape(Direction.NORTH, Direction.WEST, shape6);
		VoxelShape shape16 = Util.rotateShape(Direction.NORTH, Direction.WEST, shape7);
		VoxelShape shape = VoxelShapes.combineAndSimplify(shape1, shape2, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape3, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape4, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape5, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape6, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape7, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape8, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape9, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape10, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape11, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape12, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape13, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape14, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape15, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape16, IBooleanFunction.OR);
		return shape;
	}

}
