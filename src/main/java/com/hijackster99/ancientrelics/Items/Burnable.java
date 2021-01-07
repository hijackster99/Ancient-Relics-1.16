package com.hijackster99.ancientrelics.items;

import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;

public class Burnable extends ARItem{

	int burnTime;
	
	public Burnable(String registryName, int maxStack, ItemGroup tab, int burnTime) {
		super(registryName, maxStack, tab);
		this.burnTime = burnTime;
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack) {
		return itemStack.getItem() == this ? burnTime : -1;
	}

}
