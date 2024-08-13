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
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilityProvider;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.ItemStackHandler;

public class TEPedestal extends ARContainerEntity implements ICapabilityProvider {

	private Component name;
	
	private IItemHandler inventory;
	
	private static Capability<IItemHandler> INV_CAP = CapabilityManager.get(new CapabilityToken<>() {});
	
	public TEPedestal(BlockPos pos, BlockState state) {
		super(ARTileEntity.PEDESTAL, pos, state);
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
	public Component getDisplayName() {
		return name;
	}

	@Override
	public int getContainerSize() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean isEmpty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ItemStack getItem(int p_18941_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeItem(int p_18942_, int p_18943_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack removeItemNoUpdate(int p_18951_) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setItem(int p_18944_, ItemStack p_18945_) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean stillValid(Player p_18946_) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clearContent() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected Component getDefaultName() {
		return new TextComponent("container.pedestal");
	}

	@Override
	protected AbstractContainerMenu createMenu(int p_58627_, Inventory p_58628_) {
		return null;
	}
}
