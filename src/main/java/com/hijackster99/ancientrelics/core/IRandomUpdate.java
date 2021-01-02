package com.hijackster99.ancientrelics.core;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IRandomUpdate {

	public void randomTick(World worldObj, BlockPos pos);
	
}
