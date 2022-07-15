package com.hijackster99.ancientrelics.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RelicAnvilBlock extends ARBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.EAST, Direction.WEST, Direction.NORTH, Direction.SOUTH);
	
	public RelicAnvilBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, requiresTool);
	}
	
	@Override
	protected void createBlockStateDefinition(Builder<Block, BlockState> builder) {
		super.createBlockStateDefinition(builder);
		builder.add(property);
	}
	
	public BlockState getState(Direction dir) {
		return defaultBlockState().setValue(property, dir);
	}
	
	@Override
	public BlockState getStateForPlacement(BlockPlaceContext context) {
		return defaultBlockState().setValue(property, context.getHorizontalDirection());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape shape1 = Shapes.box(0.125, 0, 0.125, 0.875, 0.25, 0.875);
		VoxelShape shape2, shape3, shape4;
		if(state.getValue(property).equals(Direction.EAST))
		{
			shape2 = Shapes.box(0.25, 0.25, 0.1875, 0.75, 0.3125, 0.8125);
			shape3 = Shapes.box(0.375, 0.3125, 0.25, 0.625, 0.625, 0.75);
			shape4 = Shapes.box(0.1875, 0.625, 0, 0.8125, 1, 1);
		}else
		{
			shape2 = Shapes.box(0.25, 0.25, 0.1875, 0.75, 0.3125, 0.8125);
			shape3 = Shapes.box(0.375, 0.3125, 0.25, 0.625, 0.625, 0.75);
			shape4 = Shapes.box(0.1875, 0.625, 0, 0.8125, 1, 1);
		}
		VoxelShape shape = Shapes.join(shape1, shape2, BooleanOp.OR);
		shape = Shapes.join(shape, shape3, BooleanOp.OR);
		shape = Shapes.join(shape, shape4, BooleanOp.OR);
		return shape;
	}

}
