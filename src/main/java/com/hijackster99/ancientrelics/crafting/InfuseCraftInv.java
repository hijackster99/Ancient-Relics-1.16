package com.hijackster99.ancientrelics.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.ItemStackHandler;

public class InfuseCraftInv extends ItemStackHandler {
	
	private List<String> rings;
	private int tier;
	
	public InfuseCraftInv(int size, int tier) {
		super(size);
		rings = new ArrayList<String>(size);
		this.tier = tier;
	}
	
	public void putItem(int index, ItemStack stack, String ring) {
		rings.set(index, ring);
		setStackInSlot(index, stack);
	}
	
	public void removeItem(int index) {
		extractItem(index, 1, false);
		rings.set(index, null);
	}
	
	public String getRing(int index) {
		return rings.get(index);
	}
	
	public boolean containsItem(ItemStack stack, String ring) {
		for(int i = 0; i < stacks.size(); i++) {
			ItemStack stack2 = getStackInSlot(i);
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
		for(int i = 0; i < stacks.size(); i++) {
			ItemStack stack2 = getStackInSlot(i);
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
