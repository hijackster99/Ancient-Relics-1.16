package com.hijackster99.ancientrelics.core;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IUpdate {

	public void tick(World worldObj, BlockPos pos);
	
}
