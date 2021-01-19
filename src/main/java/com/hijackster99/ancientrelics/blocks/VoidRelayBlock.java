package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

public class VoidRelayBlock extends ARBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.values());
	
	public VoidRelayBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
	}
	
	@Override
	protected void fillStateContainer(Builder<Block, BlockState> builder) {
		super.fillStateContainer(builder);
		builder.add(property);
	}
	
	public BlockState getState(Direction dir) {
		return getDefaultState().with(property, dir);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return getDefaultState().with(property, context.getFace());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape1 = VoxelShapes.create(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875);
		VoxelShape shape2 = VoxelShapes.create(0.4375, 0.0625, 0.4375, 0.5625, 0.125, 0.5625);
		VoxelShape shape3 = VoxelShapes.create(0.1875, 0.125, 0.4375, 0.8125, 0.1875, 0.5625);
		VoxelShape shape4 = VoxelShapes.create(0.4375, 0.125, 0.1875, 0.5625, 0.1875, 0.8125);
		VoxelShape shape5 = VoxelShapes.create(0.125, 0.1875, 0.4375, 0.1875, 0.8125, 0.5625);
		VoxelShape shape6 = VoxelShapes.create(0.4375, 0.1875, 0.125, 0.5625, 0.8125, 0.1875);
		VoxelShape shape7 = VoxelShapes.create(0.8125, 0.1875, 0.4375, 0.875, 0.8125, 0.5625);
		VoxelShape shape8 = VoxelShapes.create(0.4375, 0.1875, 0.8125, 0.5625, 0.8125, 0.875);
		VoxelShape shape9 = VoxelShapes.create(0.1875, 0.8125, 0.4375, 0.8125, 0.875, 0.5625);
		VoxelShape shape10 = VoxelShapes.create(0.4375, 0.8125, 0.1875, 0.5625, 0.875, 0.8125);
		VoxelShape shape11 = VoxelShapes.create(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
		shape1 = Util.rotateShape(Direction.UP, state.get(property), shape1);
		shape2 = Util.rotateShape(Direction.UP, state.get(property), shape2);
		shape3 = Util.rotateShape(Direction.UP, state.get(property), shape3);
		shape4 = Util.rotateShape(Direction.UP, state.get(property), shape4);
		shape5 = Util.rotateShape(Direction.UP, state.get(property), shape5);
		shape6 = Util.rotateShape(Direction.UP, state.get(property), shape6);
		shape7 = Util.rotateShape(Direction.UP, state.get(property), shape7);
		shape8 = Util.rotateShape(Direction.UP, state.get(property), shape8);
		shape9 = Util.rotateShape(Direction.UP, state.get(property), shape9);
		shape10 = Util.rotateShape(Direction.UP, state.get(property), shape10);
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
		return shape;
	}
	
}
