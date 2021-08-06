package com.hijackster99.ancientrelics.crafting;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class InfuseCraftInv implements IInventory {

	private List<RingStack> itemStacks;
	private int size;
	
	public InfuseCraftInv(int size) {
		itemStacks = new ArrayList<RingStack>(size);
		this.size = size;
	}
	
	@Override
	public void clearContent() {
		itemStacks.clear();
	}

	@Override
	public int getContainerSize() {
		return size;
	}

	@Override
	public boolean isEmpty() {
		return itemStacks.isEmpty();
	}

	@Override
	public ItemStack getItem(int slot) {
		return itemStacks.get(slot).getStack();
	}

	@Override
	public ItemStack removeItem(int a, int b) {
		NonNullList<ItemStack> stacks = NonNullList.create();
		for(RingStack s : itemStacks) {
			stacks.add(s.getStack());
		}
		return ItemStackHelper.removeItem(stacks, a, b);
	}

	@Override
	public ItemStack removeItemNoUpdate(int slot) {
		ItemStack stack = itemStacks.get(slot).getStack();
		if(stack != null) {
			itemStacks.set(slot, null);
			return stack;
		}
		return ItemStack.EMPTY;
	}

	@Override
	public void setItem(int slot, ItemStack stack) {
		if(itemStacks.get(slot) != null) {
			itemStacks.get(slot).stack = stack;
		}
	}

	@Override
	public void setChanged() {
		
	}

	@Override
	public boolean stillValid(PlayerEntity p_70300_1_) {
		return true;
	}
	
	private static class RingStack {
		
		private String ring;
		private ItemStack stack;
		
		public RingStack(String ring, ItemStack stack) {
			this.ring = ring;
			this.stack = stack;
		}
		
		public String getRing() {
			return ring;
		}
		
		public ItemStack getStack() {
			return stack;
		}
		
	}

}
