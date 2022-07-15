package com.hijackster99.ancientrelics.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class ChargerBlock extends ARBlock{

	public ChargerBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, requiresTool);
	}
	
	@Override
	public void neighborChanged(BlockState state, Level worldIn, BlockPos pos, Block blockIn, BlockPos fromPos, boolean isMoving) {
		if(worldIn.getBlockState(pos.below()).getBlock() != ARBlock.PEDESTAL) worldIn.destroyBlock(pos, true);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
		VoxelShape shape1 = Shapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875);
		VoxelShape shape2 = Shapes.box(0.375, 0.0625, 0.375, 0.625, 0.125, 0.625);
		VoxelShape shape3 = Shapes.box(0.4375, 0.125, 0.4375, 0.5625, 0.625, 0.5625);
		VoxelShape shape4 = Shapes.box(0.125, 0.625, 0.125, 0.875, 0.6875, 0.875);
		VoxelShape shape5 = Shapes.box(0.125, 0.6875, 0.125, 0.1875, 0.8125, 0.1875);
		VoxelShape shape6 = Shapes.box(0.1875, 0.6875, 0.125, 0.25, 0.75, 0.1875);
		VoxelShape shape7 = Shapes.box(0.125, 0.6875, 0.1875, 0.1875, 0.75, 0.25);
		VoxelShape shape8 = Shapes.box(0.125, 0.6875, 0.8125, 0.1875, 0.8125, 0.875);
		VoxelShape shape9 = Shapes.box(0.1875, 0.6875, 0.8125, 0.25, 0.75, 0.875);
		VoxelShape shape10 = Shapes.box(0.125, 0.6875, 0.75, 0.1875, 0.75, 0.8125);
		VoxelShape shape11 = Shapes.box(0.8125, 0.6875, 0.8125, 0.875, 0.8125, 0.875);
		VoxelShape shape12 = Shapes.box(0.75, 0.6875, 0.8125, 0.8125, 0.75, 0.875);
		VoxelShape shape13 = Shapes.box(0.8125, 0.6875, 0.75, 0.875, 0.75, 0.8125);
		VoxelShape shape14 = Shapes.box(0.8125, 0.6875, 0.125, 0.875, 0.8125, 0.1875);
		VoxelShape shape15 = Shapes.box(0.75, 0.6875, 0.125, 0.8125, 0.75, 0.1875);
		VoxelShape shape16 = Shapes.box(0.8125, 0.6875, 0.1875, 0.875, 0.75, 0.25);
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
		shape = Shapes.join(shape, shape12, BooleanOp.OR);
		shape = Shapes.join(shape, shape13, BooleanOp.OR);
		shape = Shapes.join(shape, shape14, BooleanOp.OR);
		shape = Shapes.join(shape, shape15, BooleanOp.OR);
		shape = Shapes.join(shape, shape16, BooleanOp.OR);
		
		return shape;
	}

}
