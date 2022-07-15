package com.hijackster99.ancientrelics.crafting;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.Recipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;

public class InfuseRecipe implements Recipe<InfuseRecipeWrapper> {
	
	public static final RecipeType<InfuseRecipe> INFUSE_RECIPE = RecipeType.register("infuse_recipe");
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
	public boolean matches(InfuseRecipeWrapper inv, Level worldIn) {
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
	public ItemStack assemble(InfuseRecipeWrapper inv) {
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
	public RecipeSerializer<?> getSerializer() {
		return null;
	}

	@Override
	public RecipeType<?> getType() {
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
