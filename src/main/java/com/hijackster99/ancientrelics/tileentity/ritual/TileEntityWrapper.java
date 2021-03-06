package com.hijackster99.ancientrelics.tileentity.ritual;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;

public class TileEntityWrapper {
	
	public void tick(World worldObj, BlockPos pos) {
		
	}
	
	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random) {
		
	}
	
	public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
		return ActionResultType.PASS;
	}
	
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side){
		return LazyOptional.empty();
	}
	
	public void read(BlockState state, CompoundNBT nbt) {
		
	}
	
	public CompoundNBT write(CompoundNBT compound) {
		return compound;
	}
	
}
