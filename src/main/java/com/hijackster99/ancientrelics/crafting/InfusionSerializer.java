package com.hijackster99.ancientrelics.crafting;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.item.crafting.ShapedRecipe;
import net.minecraft.network.PacketBuffer;
import net.minecraft.util.JSONUtils;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraftforge.registries.ForgeRegistryEntry;

public class InfusionSerializer  extends ForgeRegistryEntry<IRecipeSerializer<?>> implements IRecipeSerializer<InfuseRecipe> {

	@SuppressWarnings("deprecation")
	@Override
	public InfuseRecipe fromJson(ResourceLocation loc, JsonObject json) {
		String group = JSONUtils.getAsString(json, "group", "");
		
		int tier = JSONUtils.getAsInt(json, "tier", 1);
		
		Map<Ingredient, String> ingredients = new HashMap<Ingredient, String>();
		
		for(int i = 0; i < tier; i++) {
			if(JSONUtils.isValidNode(json, "ring_" + i)) {
				JsonElement ingredientJSON = JSONUtils.isArrayNode(json, "ring_" + i) ? JSONUtils.getAsJsonArray(json, "ring_" + i) : JSONUtils.getAsJsonObject(json, "ring_" + i);
		        Ingredient ingredient = Ingredient.fromJson(ingredientJSON);
		        
		        ingredients.put(ingredient, "ring_" + i);
			}
		}
		
		ItemStack result;
		if(!json.has("result")) {
			result = ItemStack.EMPTY;
		}else if(json.get("result").isJsonObject()) {
			result = ShapedRecipe.itemFromJson(JSONUtils.getAsJsonObject(json, "result"));
		}else {
			result = new ItemStack(Registry.ITEM.get(new ResourceLocation(JSONUtils.getAsString(json, "result"))));
		}
		
		int voidCost = JSONUtils.getAsInt(json, "void_cost", 0);
		
		return new InfuseRecipe(loc, group, result, ingredients, voidCost, tier);
	}

	@Override
	public InfuseRecipe fromNetwork(ResourceLocation loc, PacketBuffer buffer) {
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
	public void toNetwork(PacketBuffer buffer, InfuseRecipe recipe) {
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
