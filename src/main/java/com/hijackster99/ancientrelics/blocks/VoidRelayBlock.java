package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.Util;
import com.hijackster99.ancientrelics.tileentity.voidrelay.VoidRelay;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.state.DirectionProperty;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.IBooleanFunction;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraftforge.common.ToolType;

public class VoidRelayBlock extends ARBlock{

	static DirectionProperty property = DirectionProperty.create("facing", Direction.values());
	
	public VoidRelayBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return true;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
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
	public BlockState getStateForPlacement(BlockItemUseContext context) {
		return defaultBlockState().setValue(property, context.getClickedFace());
	}
	
	@Override
	public ActionResultType use(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity te = worldIn.getBlockEntity(pos);
		if(te instanceof IInteractable) {
			return ((IInteractable) te).onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape1 = VoxelShapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875);
		VoxelShape shape2 = VoxelShapes.box(0.4375, 0.0625, 0.4375, 0.5625, 0.125, 0.5625);
		VoxelShape shape3 = VoxelShapes.box(0.1875, 0.125, 0.4375, 0.8125, 0.1875, 0.5625);
		VoxelShape shape4 = VoxelShapes.box(0.4375, 0.125, 0.1875, 0.5625, 0.1875, 0.8125);
		VoxelShape shape5 = VoxelShapes.box(0.125, 0.1875, 0.4375, 0.1875, 0.8125, 0.5625);
		VoxelShape shape6 = VoxelShapes.box(0.4375, 0.1875, 0.125, 0.5625, 0.8125, 0.1875);
		VoxelShape shape7 = VoxelShapes.box(0.8125, 0.1875, 0.4375, 0.875, 0.8125, 0.5625);
		VoxelShape shape8 = VoxelShapes.box(0.4375, 0.1875, 0.8125, 0.5625, 0.8125, 0.875);
		VoxelShape shape9 = VoxelShapes.box(0.1875, 0.8125, 0.4375, 0.8125, 0.875, 0.5625);
		VoxelShape shape10 = VoxelShapes.box(0.4375, 0.8125, 0.1875, 0.5625, 0.875, 0.8125);
		VoxelShape shape11 = VoxelShapes.box(0.375, 0.375, 0.375, 0.625, 0.625, 0.625);
		shape1 = Util.rotateShape(Direction.UP, state.getValue(property), shape1);
		shape2 = Util.rotateShape(Direction.UP, state.getValue(property), shape2);
		shape3 = Util.rotateShape(Direction.UP, state.getValue(property), shape3);
		shape4 = Util.rotateShape(Direction.UP, state.getValue(property), shape4);
		shape5 = Util.rotateShape(Direction.UP, state.getValue(property), shape5);
		shape6 = Util.rotateShape(Direction.UP, state.getValue(property), shape6);
		shape7 = Util.rotateShape(Direction.UP, state.getValue(property), shape7);
		shape8 = Util.rotateShape(Direction.UP, state.getValue(property), shape8);
		shape9 = Util.rotateShape(Direction.UP, state.getValue(property), shape9);
		shape10 = Util.rotateShape(Direction.UP, state.getValue(property), shape10);
		VoxelShape shape = VoxelShapes.join(shape1, shape2, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape3, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape4, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape5, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape6, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape7, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape8, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape9, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape10, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape11, IBooleanFunction.OR);
		return shape;
	}
	
}
