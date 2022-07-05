package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.Util;
import com.hijackster99.ancientrelics.tileentity.voidrelay.VoidRelay;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.DirectionProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class VoidRelayBlock extends ARBlock implements EntityBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.values());
	
	public VoidRelayBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, requiresTool);
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		return new VoidRelay();
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
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity te = worldIn.getBlockEntity(pos);
		if(te instanceof IInteractable) {
			return ((IInteractable) te).onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape shape1 = Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875);
		VoxelShape shape2 = Shapes.box(0.4375, 0.0625, 0.4375, 0.5625, 0.125, 0.5625);
		VoxelShape shape3 = Shapes.box(0.1875, 0.125, 0.4375, 0.8125, 0.1875, 0.5625);
		VoxelShape shape4 = Shapes.box(0.4375, 0.125, 0.1875, 0.5625, 0.1875, 0.8125);
		VoxelShape shape5 = Shapes.box(0.125, 0.1875, 0.4375, 0.1875, 0.8125, 0.5625);
		VoxelShape shape6 = Shapes.box(0.4375, 0.1875, 0.125, 0.5625, 0.8125, 0.1875);
		VoxelShape shape7 = Shapes.box(0.8125, 0.1875, 0.4375, 0.875, 0.8125, 0.5625);
		VoxelShape shape8 = Shapes.box(0.4375, 0.1875, 0.8125, 0.5625, 0.8125, 0.875);
		VoxelShape shape9 = Shapes.box(0.1875, 0.8125, 0.4375, 0.8125, 0.875, 0.5625);
		VoxelShape shape10 = Shapes.box(0.4375, 0.8125, 0.1875, 0.5625, 0.875, 0.8125);
		VoxelShape shape11 = Shapes.box(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
		shape1 = Util.rotateAABB(Direction.UP, state.getValue(property), shape1);
		shape2 = Util.rotateAABB(Direction.UP, state.getValue(property), shape2);
		shape3 = Util.rotateAABB(Direction.UP, state.getValue(property), shape3);
		shape4 = Util.rotateAABB(Direction.UP, state.getValue(property), shape4);
		shape5 = Util.rotateAABB(Direction.UP, state.getValue(property), shape5);
		shape6 = Util.rotateAABB(Direction.UP, state.getValue(property), shape6);
		shape7 = Util.rotateAABB(Direction.UP, state.getValue(property), shape7);
		shape8 = Util.rotateAABB(Direction.UP, state.getValue(property), shape8);
		shape9 = Util.rotateAABB(Direction.UP, state.getValue(property), shape9);
		shape10 = Util.rotateAABB(Direction.UP, state.getValue(property), shape10);
		VoxelShape shape = Shapes.join(shape1, shape2, BooleanOp.OR);
		shape = Shapes.join(shape, shape3, BooleanOp.OR);
		shape = Shapes.join(shape, shape4, BooleanOp.OR);
		shape = Shapes.join(shape, shape5, BooleanOp.OR);
		shape = Shapes.join(shape, shape6, BooleanOp.OR);
		shape = Shapes.join(shape, shape7, BooleanOp.OR);
		shape = Shapes.join(shape, shape8, BooleanOp.OR);
		shape = Shapes.join(shape, shape9, BooleanOp.OR);
		shape = Shapes.join(shape, shape10, BooleanOp.OR);
		shape = Shapes.join(shape, shape11, BooleanOp.OR);
		return shape;
	}
	
}
