package com.hijackster99.ancientrelics.crafting;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.wrapper.RecipeWrapper;

public class InfuseRecipeWrapper extends RecipeWrapper {

	public InfuseRecipeWrapper(InfuseCraftInv inv) {
		super(inv);
	}
	
	public String getRing()
	{
		return ((InfuseRecipeWrapper) inv).getRing();
	}
	
	public int getTier()
	{
		return ((InfuseRecipeWrapper) inv).getTier();
	}
	
	public boolean containsItem(ItemStack item, String ring)
	{
		return ((InfuseCraftInv) inv).containsItem(item, ring);
	}

}
