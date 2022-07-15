package com.hijackster99.ancientrelics.core;

import java.util.Random;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.state.BlockState;

public interface IRandomUpdate {

	public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random);
	
}
