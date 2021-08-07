package com.hijackster99.ancientrelics.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;

public class InfuseCraftInv extends Inventory{
	
	private List<String> rings;
	private int tier;
	
	public InfuseCraftInv(int size, int tier) {
		super(size);
		rings = new ArrayList<String>(size);
		this.tier = tier;
	}
	
	public void putItem(int index, ItemStack stack, String ring) {
		rings.set(index, ring);
		setItem(index, stack);
	}
	
	public void removeItem(int index) {
		removeItem(index, 1);
		rings.set(index, null);
	}
	
	public String getRing(int index) {
		return rings.get(index);
	}
	
	public boolean containsItem(ItemStack stack, String ring) {
		for(int i = 0; i < getContainerSize(); i++) {
			ItemStack stack2 = getItem(i);
			if(ItemStack.isSameIgnoreDurability(stack, stack2)) {
				if(ring.equals(rings.get(i))) {
					removeItem(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public boolean containsItemNbt(ItemStack stack, String ring) {
		for(int i = 0; i < getContainerSize(); i++) {
			ItemStack stack2 = getItem(i);
			if(ItemStack.isSameIgnoreDurability(stack, stack2) && ItemStack.tagMatches(stack, stack2)) {
				if(ring.equals(rings.get(i))) {
					removeItem(i);
					return true;
				}
			}
		}
		return false;
	}
	
	public int getTier() {
		return tier;
	}
	
}
