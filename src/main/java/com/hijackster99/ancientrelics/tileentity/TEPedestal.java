package com.hijackster99.ancientrelics.tileentity;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.container.Container;
import net.minecraft.inventory.container.INamedContainerProvider;
import net.minecraft.util.Direction;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEPedestal extends ARTileEntity implements INamedContainerProvider, ICapabilityProvider {

	private ITextComponent name;
	
	private IItemHandler inventory;
	
	@CapabilityInject(IItemHandler.class)
	private static Capability<IItemHandler> INV_CAP = null;
	
	public TEPedestal() {
		super(PEDESTAL);
		name = new StringTextComponent("container.pedestal");
		inventory = new ItemStackHandler() {
			
			@Override
			public int getSlotLimit(int slot) {
				return 1;
			}
			
			@Override
			protected void onContentsChanged(int slot) {
				setChanged();
			}
			
		};
	}

	@Override
	public Container createMenu(int p_createMenu_1_, PlayerInventory inv, PlayerEntity player) {
		return null;
	}

	@Override
	public ITextComponent getDisplayName() {
		return name;
	}
	
	public void setCustomName(ITextComponent name) {
		this.name = name;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return INV_CAP.orEmpty(cap, LazyOptional.of(() -> inventory));
	}

}
