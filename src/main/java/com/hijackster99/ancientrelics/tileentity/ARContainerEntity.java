package com.hijackster99.ancientrelics.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public abstract class ARContainerEntity extends BaseContainerBlockEntity{

	protected ARContainerEntity(BlockEntityType<?> p_155076_, BlockPos p_155077_, BlockState p_155078_) {
		super(p_155076_, p_155077_, p_155078_);
	}

}
