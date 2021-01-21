package com.hijackster99.ancientrelics.tileentity;

import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualStone;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ARTileEntity extends TileEntity {

	public ARTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	@ObjectHolder(References.MODID + ":ritual_stone")
	public static TileEntityType<RitualStone> RITUAL_STONE;
	
	@ObjectHolder(References.MODID + ":void_relay")
	public static TileEntityType<RitualStone> VOID_RELAY;

}
