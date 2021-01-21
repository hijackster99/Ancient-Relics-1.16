package com.hijackster99.ancientrelics.items;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class PeridotStaff extends ARItem{

	public PeridotStaff(String registryName, int maxStack, ItemGroup tab) {
		super(registryName, maxStack, tab);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, PlayerEntity playerIn, Hand handIn) {
		if(playerIn.isSneaking()) {
			if(handIn == Hand.MAIN_HAND) {
				playerIn.inventory.setInventorySlotContents(playerIn.inventory.currentItem, new ItemStack(ARItem.RUBY_STAFF, 1));
			}else {
				playerIn.inventory.setInventorySlotContents(45, new ItemStack(ARItem.RUBY_STAFF, 1));
			}
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}

}
