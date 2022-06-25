package com.hijackster99.ancientrelics.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;

public class TEPedestal extends ARTileEntity implements INamedContainerProvider {

	private ITextComponent name;
	
	public TEPedestal() {
		super(PEDESTAL);
		name = new StringTextComponent("container.pedestal");
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory p_createMenu_2_, PlayerEntity p_createMenu_3_) {
		return null;
	}

	@Override
	public ITextComponent getDisplayName() {
		return name;
	}
	
	public void setCustomName(ITextComponent name) {
		this.name = name;
	}

}
