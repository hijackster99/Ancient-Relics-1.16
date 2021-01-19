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

public class RelicAnvilBlock extends ARBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH);
	
	public RelicAnvilBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
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
		return getDefaultState().with(property, context.getPlacementHorizontalFacing());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape1 = VoxelShapes.create(0.125, 0, 0.125, 0.875, 0.25, 0.875);
		VoxelShape shape2 = VoxelShapes.create(0.25, 0.25, 0.1875, 0.75, 0.3125, 0.8125);
		VoxelShape shape3 = VoxelShapes.create(0.375, 0.3125, 0.25, 0.625, 0.625, 0.75);
		VoxelShape shape4 = VoxelShapes.create(0.1875, 0.625, 0, 0.8125, 1, 1);
		shape2 = Util.rotateShape(Direction.EAST, state.get(property), shape2);
		shape3 = Util.rotateShape(Direction.EAST, state.get(property), shape3);
		shape4 = Util.rotateShape(Direction.EAST, state.get(property), shape4);
		VoxelShape shape = VoxelShapes.combineAndSimplify(shape1, shape2, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape3, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape4, IBooleanFunction.OR);
		return shape;
	}

}
