package com.hijackster99.ancientrelics.blocks;

import com.hijackster99.ancientrelics.core.Util;
import com.hijackster99.ancientrelics.tileentity.TEPedestal;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
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

public class PedestalBlock extends ARContainer{

	public PedestalBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
	}
	
	@Override
	public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
		VoxelShape shape1 = VoxelShapes.box(0.3125, 0, 0.3125, 0.6875, 0.0625, 0.6875);
		VoxelShape shape2 = VoxelShapes.box(0.375, 0.0625, 0.375, 0.625, 0.125, 0.625);
		VoxelShape shape3 = VoxelShapes.box(0.4375, 0.125, 0.4375, 0.5625, 0.625, 0.5625);
		VoxelShape shape4 = VoxelShapes.box(0.125, 0.625, 0.125, 0.875, 0.6875, 0.875);
		VoxelShape shape5 = VoxelShapes.box(0.125, 0.6875, 0.125, 0.1875, 0.8125, 0.1875);
		VoxelShape shape6 = VoxelShapes.box(0.1875, 0.6875, 0.125, 0.25, 0.75, 0.1875);
		VoxelShape shape7 = VoxelShapes.box(0.125, 0.6875, 0.1875, 0.1875, 0.75, 0.25);
		VoxelShape shape8 = Util.rotateShape(Direction.NORTH, Direction.EAST, shape5);
		VoxelShape shape9 = Util.rotateShape(Direction.NORTH, Direction.EAST, shape6);
		VoxelShape shape10 = Util.rotateShape(Direction.NORTH, Direction.EAST, shape7);
		VoxelShape shape11 = Util.rotateShape(Direction.NORTH, Direction.SOUTH, shape5);
		VoxelShape shape12 = Util.rotateShape(Direction.NORTH, Direction.SOUTH, shape6);
		VoxelShape shape13 = Util.rotateShape(Direction.NORTH, Direction.SOUTH, shape7);
		VoxelShape shape14 = Util.rotateShape(Direction.NORTH, Direction.WEST, shape5);
		VoxelShape shape15 = Util.rotateShape(Direction.NORTH, Direction.WEST, shape6);
		VoxelShape shape16 = Util.rotateShape(Direction.NORTH, Direction.WEST, shape7);
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
		shape = VoxelShapes.join(shape, shape12, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape13, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape14, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape15, IBooleanFunction.OR);
		shape = VoxelShapes.join(shape, shape16, IBooleanFunction.OR);
		return shape;
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public ActionResultType use(BlockState stateIn, World worldIn, BlockPos posIn, PlayerEntity playerIn, Hand handIn, BlockRayTraceResult rayIn) {
		return super.use(stateIn, worldIn, posIn, playerIn, handIn, rayIn);
	}

	@Override
	public TileEntity newBlockEntity(IBlockReader worldIn) {
		return new TEPedestal();
	}
	
	@Override
	public void setPlacedBy(World worldIn, BlockPos posIn, BlockState stateIn, LivingEntity entityIn, ItemStack stackIn) {
		if(stackIn.hasCustomHoverName()) {
			TileEntity te = worldIn.getBlockEntity(posIn);
			if(te instanceof TEPedestal) {
				((TEPedestal) te).setCustomName(stackIn.getHoverName());
			}
		}
	}
}
