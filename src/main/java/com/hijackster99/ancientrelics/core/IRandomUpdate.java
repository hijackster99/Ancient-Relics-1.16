package com.hijackster99.ancientrelics.core;

import java.util.Random;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;

public interface IRandomUpdate {

	public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random);
	
}
