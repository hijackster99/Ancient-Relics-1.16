package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.Tag;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityWrapper implements ICapabilityProvider {
	
	protected Ritual ritual;
	protected Tag<Block> type;
	
	public void init(Level worldIn, BlockPos pos) {
		
	}
	
	public void tick(Level worldObj, BlockPos pos) {
		
	}
	
	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random) {
		
	}
	
	public InteractionResult onBlockActivated(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
		return InteractionResult.PASS;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
		return LazyOptional.empty();
	}
	
	public void read(CompoundTag nbt) {
		
	}
	
	public CompoundTag write(CompoundTag compound) {
		return compound;
	}
	
}
