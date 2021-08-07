package com.hijackster99.ancientrelics.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class InfuseRecipe implements IRecipe<InfuseCraftInv> {
	
	public static final IRecipeType<InfuseRecipe> INFUSE_RECIPE = IRecipeType.register("infuse_recipe");
	private ResourceLocation id;
	private String group;
	private int tier;
	private ItemStack result;
	private Map<Ingredient, String> ingredients;
	private int voidCost;
	
	public InfuseRecipe(ResourceLocation id, String group, ItemStack result, Map<Ingredient, String> ingredients, int voidCost, int tier) {
		this.id = id;
		this.result = result;
		this.ingredients = ingredients;
		this.voidCost = voidCost;
		this.tier = tier;
		this.group = group;
	}
	
	public InfuseRecipe() {
		this.id = new ResourceLocation("ancientrelics:peridot_gem");
		this.result = ItemStack.EMPTY;
		ingredients = new HashMap<Ingredient, String>();
		this.voidCost = 0;
		this.tier = 0;
		this.group = "";
	}
	
	@Override
	public boolean matches(InfuseCraftInv inv, World worldIn) {
		for(Map.Entry<Ingredient, String> entry : ingredients.entrySet()) {
			Ingredient ingr = entry.getKey();
			String ring = entry.getValue();
			ItemStack stack = ingr.getItems()[0];
			
			if(!inv.containsItem(stack, ring)) return false;
		}
		if(inv.getTier() < tier) return false;
		return true;
	}

	@Override
	public ItemStack assemble(InfuseCraftInv inv) {
		return result;
	}

	@Override
	public boolean canCraftInDimensions(int x, int y) {
		return ingredients.size() < x * y;
	}

	@Override
	public ItemStack getResultItem() {
		return result;
	}

	@Override
	public ResourceLocation getId() {
		return id;
	}

	@Override
	public IRecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public IRecipeType<?> getType() {
		return INFUSE_RECIPE;
	}
	
	public Map<Ingredient, String> getIngredientMap() {
		return ingredients;
	}
	
	public int getVoidCost() {
		return voidCost;
	}
	
	public int getTier() {
		return tier;
	}
	
	public String getGroup() {
		return group;
	}

}
