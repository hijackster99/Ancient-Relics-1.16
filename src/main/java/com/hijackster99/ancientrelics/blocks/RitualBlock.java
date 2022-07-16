package com.hijackster99.ancientrelics.blocks;

import java.util.Random;

import com.hijackster99.ancientrelics.core.IInteractable;
import com.hijackster99.ancientrelics.core.IRandomUpdate;
import com.hijackster99.ancientrelics.tileentity.ARTileEntity;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualStone;

import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;

public class RitualBlock extends ARBlock implements EntityBlock {
	
	public RitualBlock(String registryName, Material materialIn, float hardnessIn, float resistanceIn, boolean requiresTool) {
		super(registryName, materialIn, hardnessIn, resistanceIn, requiresTool);
	}
	
	@Override
	public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		BlockEntity te = worldIn.getBlockEntity(pos);
		if(te instanceof IInteractable) {
			IInteractable interact = (IInteractable) te;
			return interact.onBlockActivated(state, worldIn, pos, player, handIn, hit);
		}
		return InteractionResult.PASS;
	}
	
	@Override
	public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
		int tier = -1;
		Tag<Block> type = null;
		for(ResourceLocation rl : this.getTags()) {
			if(rl.getPath().startsWith("ritual_tier")) tier = Integer.valueOf(rl.getPath().substring(rl.getPath().length() - 1));
			else if(rl.getPath().endsWith("active"));
			else if(rl.getPath().startsWith("ritual_type")) type = (Tag<Block>) BlockTags.getAllTags().getTag(rl);
		}
		return new RitualStone(tier, type, pos, state);
	}
	
	@Override
	public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level worldIn, BlockState stateIn, BlockEntityType<T> type) {
		return BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_active")).contains(this) ? type == ARTileEntity.RITUAL_STONE ? RitualStone::tick : null : null;
	}
	
	@Override
	public boolean isRandomlyTicking(BlockState state) {
		return BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_active")) != null ? BlockTags.getAllTags().getTag(new ResourceLocation("ancientrelics:ritual_type_active")).contains(this) : false;
	}
	
	@Override
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		BlockEntity te = worldIn.getBlockEntity(pos);
		if(te instanceof IRandomUpdate) {
			IRandomUpdate update = (IRandomUpdate) te;
			update.randomTick(state, worldIn, pos, random);
		}
	}

}
