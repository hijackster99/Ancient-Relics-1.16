package com.hijackster99.ancientrelics.gui;

import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualStone;

import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class PedestalMenu extends AbstractContainerMenu {


	@ObjectHolder(References.MODID + ":pedestal")
	public static MenuType<PedestalMenu> PEDESTAL;
	
	private Inventory inv;
	
	public PedestalMenu(int containerId, Inventory inv) {
		this(PEDESTAL, containerId);
		this.inv = inv;
	}
	
	public PedestalMenu(MenuType<?> type, int containerId) {
		super(type, containerId);
	}

	@Override
	public boolean stillValid(Player p_38874_) {
		// TODO Auto-generated method stub
		return false;
	}

}
