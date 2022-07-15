package com.hijackster99.ancientrelics.items;

import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.RecipeType;

public class Burnable extends ARItem{

	int burnTime;
	
	public Burnable(String registryName, int maxStack, CreativeModeTab tab, int burnTime) {
		super(registryName, maxStack, tab);
		this.burnTime = burnTime;
	}
	
	@Override
	public int getBurnTime(ItemStack itemStack, RecipeType<?> recipeType) {
		return itemStack.getItem() == this ? burnTime : -1;
	}

}
