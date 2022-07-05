package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class RelicBlock extends ShardBlock{

	public RelicBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, requiresTool);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape shape1 = Shapes.box(0.375, 0, 0.4375, 0.625, 0.125, 0.5625);
		VoxelShape shape2 = Shapes.box(0.4375, 0, 0.375, 0.5625, 0.125, 0.4375);
		VoxelShape shape3 = Shapes.box(0.4375, 0, 0.5625, 0.5625, 0.125, 0.625);
		VoxelShape shape4 = Shapes.box(0.3125, 0, 0.375, 0.6875, 0.0625, 0.625);
		VoxelShape shape5 = Shapes.box(0.375, 0, 0.3125, 0.625, 0.0625, 0.6875);
		VoxelShape shape6 = Shapes.box(0.25, 0, 0.4375, 0.75, 0.0625, 0.5625);
		VoxelShape shape7 = Shapes.box(0.4375, 0, 0.25, 0.5625, 0.0625, 0.75);
		shape1 = Util.rotateAABB(Direction.UP, state.getValue(property), shape1);
		shape2 = Util.rotateAABB(Direction.UP, state.getValue(property), shape2);
		shape3 = Util.rotateAABB(Direction.UP, state.getValue(property), shape3);
		shape4 = Util.rotateAABB(Direction.UP, state.getValue(property), shape4);
		shape5 = Util.rotateAABB(Direction.UP, state.getValue(property), shape5);
		shape6 = Util.rotateAABB(Direction.UP, state.getValue(property), shape6);
		shape7 = Util.rotateAABB(Direction.UP, state.getValue(property), shape7);
		VoxelShape shape = Shapes.join(shape1, shape2, BooleanOp.OR);
		shape = Shapes.join(shape, shape3, BooleanOp.OR);
		shape = Shapes.join(shape, shape4, BooleanOp.OR);
		shape = Shapes.join(shape, shape5, BooleanOp.OR);
		shape = Shapes.join(shape, shape6, BooleanOp.OR);
		shape = Shapes.join(shape, shape7, BooleanOp.OR);
		return shape;
	}

}
