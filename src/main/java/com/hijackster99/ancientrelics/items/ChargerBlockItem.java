package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.blocks.ARBlock;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

public class ChargerBlockItem extends ARBlockItem{

	public ChargerBlockItem(Block block, int stackSize, CreativeModeTab tab) {
		super(block, stackSize, tab);
	}
	
	@Override
	protected boolean canPlace(BlockPlaceContext context, BlockState state) {
		return context.getLevel().getBlockState(context.getClickedPos().below()).getBlock() == ARBlock.PEDESTAL;
	}

}
