package com.hijackster99.ancientrelics.Items;

import net.minecraft.block.Block;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class BurnableBlock extends ARBlockItem {

	int burnTime;
	
	public BurnableBlock(Block block, int stackSize, ItemGroup tab, int burnTime) {
		super(block, stackSize, tab);
		this.burnTime = burnTime;
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack) {
		return itemStack.getItem() == this ? burnTime : -1;
	}

}
