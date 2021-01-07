package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.PushReaction;
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
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class ShardBlock extends ARBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.values());
	
	public ShardBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel);
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
		VoxelShape shape1 = VoxelShapes.create(0.375, 0, 0.4375, 0.625, 0.125, 0.5625);
		VoxelShape shape2 = VoxelShapes.create(0.4375, 0, 0.375, 0.5625, 0.125, 0.4375);
		VoxelShape shape3 = VoxelShapes.create(0.4375, 0, 0.5625, 0.5625, 0.125, 0.625);
		VoxelShape shape4 = VoxelShapes.create(0.375, 0, 0.375, 0.625, 0.0625, 0.625);
		VoxelShape shape5 = VoxelShapes.create(0.4375, 0, 0.3125, 0.5625, 0.0625, 0.375);
		VoxelShape shape6 = VoxelShapes.create(0.4375, 0, 0.625, 0.5625, 0.0625, 0.6875);
		VoxelShape shape7 = VoxelShapes.create(0.3125, 0, 0.4375, 0.375, 0.0625, 0.5625);
		VoxelShape shape8 = VoxelShapes.create(0.625, 0, 0.4375, 0.6875, 0.0625, 0.5625);
		shape1 = Util.rotateShape(Direction.UP, state.get(property), shape1);
		shape2 = Util.rotateShape(Direction.UP, state.get(property), shape2);
		shape3 = Util.rotateShape(Direction.UP, state.get(property), shape3);
		shape4 = Util.rotateShape(Direction.UP, state.get(property), shape4);
		shape5 = Util.rotateShape(Direction.UP, state.get(property), shape5);
		shape6 = Util.rotateShape(Direction.UP, state.get(property), shape6);
		shape7 = Util.rotateShape(Direction.UP, state.get(property), shape7);
		shape8 = Util.rotateShape(Direction.UP, state.get(property), shape8);
		VoxelShape shape = VoxelShapes.combineAndSimplify(shape1, shape2, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape3, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape4, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape5, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape6, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape7, IBooleanFunction.OR);
		shape = VoxelShapes.combineAndSimplify(shape, shape8, IBooleanFunction.OR);
		return shape;
	}
	@Override
	public void neighborChanged(BlockState state, World worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(!worldIn.getBlockState(pos.subtract(state.get(property).getDirectionVec())).isSolidSide(worldIn.getBlockReader(worldIn.getChunk(pos).getPos().x, worldIn.getChunk(pos).getPos().z), pos, state.get(property))) {
			worldIn.destroyBlock(pos, true);
		}
	}
	
	
	
	@Override
	public PushReaction getPushReaction(BlockState state) {
		return PushReaction.DESTROY;
	}
	
}
