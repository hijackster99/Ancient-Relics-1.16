package com.hijackster99.ancientrelics.Items;

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
		return context.getWorld().getBlockState(context.getPos().subtract(context.getFace().getDirectionVec())).isSolidSide(context.getWorld().getBlockReader(context.getWorld().getChunk(context.getPos()).getPos().x, context.getWorld().getChunk(context.getPos()).getPos().z), context.getPos(), context.getFace());
	}

}
