package com.hijackster99.ancientrelics.items;

import com.hijackster99.ancientrelics.blocks.ARBlock;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemGroup;

public class ChargerBlockItem extends ARBlockItem{

	public ChargerBlockItem(Block block, int stackSize, ItemGroup tab) {
		super(block, stackSize, tab);
	}
	
	@Override
	protected boolean canPlace(BlockItemUseContext context, BlockState state) {
		return context.getLevel().getBlockState(context.getClickedPos().below()).getBlock() == ARBlock.PEDESTAL;
	}

}
