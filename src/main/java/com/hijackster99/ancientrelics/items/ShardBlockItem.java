package com.hijackster99.ancientrelics.items;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;

public class ShardBlockItem extends ARBlockItem {

	public ShardBlockItem(Block block, int stackSize, ItemGroup tab) {
		super(block, stackSize, tab);
	}
	
	@Override
	protected boolean canPlace(BlockItemUseContext context, BlockState state) {
		return context.getWorld().getBlockState(context.getPos().subtract(context.getFace().getDirectionVec())).isSolidSide(context.getWorld().getBlockReader(context.getPos().getX() >> 4, context.getPos().getZ() >> 4), context.getPos(), context.getFace());
	}

}
