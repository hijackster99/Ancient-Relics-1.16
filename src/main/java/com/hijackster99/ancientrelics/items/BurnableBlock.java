package com.hijackster99.ancientrelics.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.block.Block;

public class BurnableBlock extends ARBlockItem {

	int burnTime;
	
	public BurnableBlock(Block block, int stackSize, CreativeModeTab tab, int burnTime) {
		super(block, stackSize, tab);
		this.burnTime = burnTime;
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return itemStack.getItem() == this ? burnTime : -1;
	}

}
