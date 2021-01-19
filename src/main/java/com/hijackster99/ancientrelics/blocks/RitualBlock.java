package com.hijackster99.ancientrelics.blocks;

import java.util.Random;

import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualStone;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ToolType;

public class RitualBlock extends ARBlock{
	
	public RitualBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, ToolType harvestTool, int miningLevel, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, harvestTool, miningLevel, requiresTool);
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
		return BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_active")) != null ? BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_active")).contains(this) : false;
	}
	
	@Override
	public TileEntity createTileEntity(BlockState state, IBlockReader world) {
		RitualStone rs = new RitualStone();
		for(ResourceLocation rl : this.getTags()) {
			if(rl.getPath().startsWith("ritual_tier")) rs.setTier((Tag<Block>) BlockTags.getCollection().get(rl));
			else if(rl.getPath().startsWith("ritual_type")) rs.setRitualType((Tag<Block>) BlockTags.getCollection().get(rl));
		}
		return rs;
	}
	
	@Override
	public boolean ticksRandomly(BlockState state) {
		return BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_active")) != null ? BlockTags.getCollection().get(new ResourceLocation("ancientrelics:ritual_type_active")).contains(this) : false;
	}
	
	@Override
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		TileEntity te = worldIn.getTileEntity(pos);
		if(te instanceof IRandomUpdate) {
			IRandomUpdate update = (IRandomUpdate) te;
			update.randomTick(state, worldIn, pos, random);
		}
	}

}
