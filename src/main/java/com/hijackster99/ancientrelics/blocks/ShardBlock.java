package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ShardBlock extends ARBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.values());
	
	public ShardBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
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
		return defaultBlockState().setValue(property, context.getClickedFace());
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape shape1 = Shapes.box(0.375, 0, 0.4375, 0.625, 0.125, 0.5625);
		VoxelShape shape2 = Shapes.box(0.4375, 0, 0.375, 0.5625, 0.125, 0.4375);
		VoxelShape shape3 = Shapes.box(0.4375, 0, 0.5625, 0.5625, 0.125, 0.625);
		VoxelShape shape4 = Shapes.box(0.375, 0, 0.375, 0.625, 0.0625, 0.625);
		VoxelShape shape5 = Shapes.box(0.4375, 0, 0.3125, 0.5625, 0.0625, 0.375);
		VoxelShape shape6 = Shapes.box(0.4375, 0, 0.625, 0.5625, 0.0625, 0.6875);
		VoxelShape shape7 = Shapes.box(0.3125, 0, 0.4375, 0.375, 0.0625, 0.5625);
		VoxelShape shape8 = Shapes.box(0.625, 0, 0.4375, 0.6875, 0.0625, 0.5625);
		shape1 = Util.rotateAABB(Direction.UP, state.getValue(property), shape1);
		shape2 = Util.rotateAABB(Direction.UP, state.getValue(property), shape2);
		shape3 = Util.rotateAABB(Direction.UP, state.getValue(property), shape3);
		shape4 = Util.rotateAABB(Direction.UP, state.getValue(property), shape4);
		shape5 = Util.rotateAABB(Direction.UP, state.getValue(property), shape5);
		shape6 = Util.rotateAABB(Direction.UP, state.getValue(property), shape6);
		shape7 = Util.rotateAABB(Direction.UP, state.getValue(property), shape7);
		shape8 = Util.rotateAABB(Direction.UP, state.getValue(property), shape8);
		VoxelShape shape = Shapes.join(shape1, shape2, BooleanOp.OR);
		shape = Shapes.join(shape, shape3, BooleanOp.OR);
		shape = Shapes.join(shape, shape4, BooleanOp.OR);
		shape = Shapes.join(shape, shape5, BooleanOp.OR);
		shape = Shapes.join(shape, shape6, BooleanOp.OR);
		shape = Shapes.join(shape, shape7, BooleanOp.OR);
		shape = Shapes.join(shape, shape8, BooleanOp.OR);
		return shape;
	}
	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(!worldIn.getBlockState(pos.subtract(state.getValue(property).getNormal())).isFaceSturdy(worldIn.getChunkForCollisions(worldIn.getChunk(pos).getPos().x, worldIn.getChunk(pos).getPos().z), pos, state.getValue(property))) {
			worldIn.destroyBlock(pos, true);
		}
	}
	
	@Override
	public PushReaction getPistonPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}
	
}
