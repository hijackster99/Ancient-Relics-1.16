package com.hijackster99.ancientrelics.blocks;

import java.util.Random;

import com.hijackster99.ancientrelics.core.EnumRitualType;
import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualStone;

import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class RitualBlock extends ARBlock{

	int tier;
	EnumRitualType type;

	boolean active;
	
	public RitualBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool, int tier, EnumRitualType type, boolean active) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
		this.tier = tier;
		this.type = type;
		this.active = active;
	}
	
	@Override
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof IInteractable) {
			IInteractable interact = (IInteractable) te;
			return interact.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		return ActionResultType.PASS;
	}
	
	@Override
	public boolean hasTileEntity(BlockState state) {
		return active;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		return new RitualStone(tier, type);
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return active;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof IRandomUpdate) {
			IRandomUpdate update = (IRandomUpdate) te;
			update.randomTick(state, worldIn, pos, random);
		}
	}
	
	public int getTier() {
		return tier;
	}

	public void setTier(int tier) {
		this.tier = tier;
	}

	public EnumRitualType getType() {
		return type;
	}

	public void setType(EnumRitualType type) {
		this.type = type;
	}

	public boolean isActive() {
		return active;
	}

}
