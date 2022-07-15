package com.hijackster99.ancientrelics.items;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ARBlockItem extends BlockItem {

	public ARBlockItem(Block block, int stackSize, CreativeModeTab tab) {
		super(block, new Item.Properties().stacksTo(stackSize).tab(tab));
		setRegistryName(block.getRegistryName());
	}
	
}
