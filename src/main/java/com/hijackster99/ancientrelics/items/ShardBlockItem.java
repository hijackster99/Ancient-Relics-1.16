package com.hijackster99.ancientrelics.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ShardBlockItem extends ARBlockItem {

	public ShardBlockItem(Block block, int stackSize, CreativeModeTab tab) {
		super(block, stackSize, tab);
	}
	
	@Override
	protected boolean canPlace(BlockPlaceContext context, BlockState state) {
		return context.getLevel().getBlockState(context.getClickedPos().subtract(context.getClickedFace().getNormal())).isFaceSturdy(context.getLevel().getChunkForCollisions(context.getClickedPos().getX() >> 4, context.getClickedPos().getZ() >> 4), context.getClickedPos(), context.getClickedFace());
	}

}
