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
		return context.getLevel().getBlockState(context.getClickedPos().subtract(context.getClickedFace().getNormal())).isFaceSturdy(context.getLevel().getChunkForCollisions(context.getClickedPos().getX() >> 4, context.getClickedPos().getZ() >> 4), context.getClickedPos(), context.getClickedFace());
	}

}
