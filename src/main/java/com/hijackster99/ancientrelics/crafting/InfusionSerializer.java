package com.hijackster99.ancientrelics.crafting;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.core.Registry;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.ShapedRecipe;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class InfusionSerializer  extends ForgeRegistryEntry<RecipeSerializer<?>> implements RecipeSerializer<InfuseRecipe> {

	@SuppressWarnings("deprecation")
	@Override
	public InfuseRecipe fromJson(ResourceLocation loc, JsonObject json) {
		String group = GsonHelper.getAsString(json, "group", "");
		
		int tier = GsonHelper.getAsInt(json, "tier", 1);
		
		Map<Ingredient, String> ingredients = new HashMap<Ingredient, String>();
		
		for(int i = 0; i < tier; i++) {
			if(GsonHelper.isValidNode(json, "ring_" + i)) {
				JsonElement ingredientJSON = GsonHelper.isArrayNode(json, "ring_" + i) ? GsonHelper.getAsJsonArray(json, "ring_" + i) : GsonHelper.getAsJsonObject(json, "ring_" + i);
		        Ingredient ingredient = Ingredient.fromJson(ingredientJSON);
		        
		        ingredients.put(ingredient, "ring_" + i);
			}
		}
		
		ItemStack result;
		if(!json.has("result")) {
			result = ItemStack.EMPTY;
		}else if(json.get("result").isJsonObject()) {
			result = ShapedRecipe.itemStackFromJson(GsonHelper.getAsJsonObject(json, "result"));
		}else {
			result = new ItemStack(Registry.ITEM.get(new ResourceLocation(GsonHelper.getAsString(json, "result"))));
		}
		
		int voidCost = GsonHelper.getAsInt(json, "void_cost", 0);
		
		return new InfuseRecipe(loc, group, result, ingredients, voidCost, tier);
	}

	@Override
	public InfuseRecipe fromNetwork(ResourceLocation loc, FriendlyByteBuf buffer) {
		int groupLength = buffer.readInt();
		String group = new String(buffer.readByteArray(groupLength));
		int tier = buffer.readInt();
		Map<Ingredient, String> ingredients = new HashMap<Ingredient, String>();
		int total = buffer.readInt();
		for(int i = 0; i < total; i++) {
			int length = buffer.readInt();
			String str = new String(buffer.readByteArray(length));
			Ingredient ing = Ingredient.fromNetwork(buffer);
			ingredients.put(ing, str);
		}
		ItemStack result = buffer.readItem();
		int voidCost = buffer.readInt();
		return new InfuseRecipe(loc, group, result, ingredients, voidCost, tier);
	}

	@Override
	public void toNetwork(FriendlyByteBuf buffer, InfuseRecipe recipe) {
		buffer.writeInt(recipe.getGroup().getBytes().length);
		buffer.writeBytes(recipe.getGroup().getBytes());
		buffer.writeInt(recipe.getTier());
		Map<Ingredient, String> ingredients = recipe.getIngredientMap();
		buffer.writeInt(ingredients.entrySet().size());
		for(Map.Entry<Ingredient, String> entry : ingredients.entrySet()) {
			buffer.writeInt(entry.getValue().getBytes().length);
			buffer.writeBytes(entry.getValue().getBytes());
			entry.getKey().toNetwork(buffer);
		}
		buffer.writeItem(recipe.getResultItem());
		buffer.writeInt(recipe.getVoidCost());
	}

}
