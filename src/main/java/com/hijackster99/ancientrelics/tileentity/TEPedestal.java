package com.hijackster99.ancientrelics.tileentity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.Nameable;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEPedestal extends ARTileEntity implements Nameable, ICapabilityProvider, MenuProvider {

	private Component name;
	
	private IItemHandler inventory;
	
	private static Capability<IItemHandler> INV_CAP = CapabilityManager.get(new CapabilityToken<>() {});
	
	public TEPedestal(BlockPos pos, BlockState state) {
		super(PEDESTAL, pos, state);
		name = new TextComponent("container.pedestal");
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
	public Component getName() {
		return name;
	}
	
	public void setCustomName(Component name) {
		this.name = name;
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return INV_CAP.orEmpty(cap, LazyOptional.of(() -> inventory));
	}

	@Override
	public AbstractContainerMenu createMenu(int p_39954_, Inventory playerInv, Player player) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Component getDisplayName() {
		return name;
	}
}
