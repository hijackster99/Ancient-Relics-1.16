package com.hijackster99.ancientrelics.Tileentity;

import com.hijackster99.ancientrelics.Tileentity.Ritual.RitualStone;
import com.hijackster99.ancientrelics.core.References;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.registries.ObjectHolder;

public class ARTileEntity extends TileEntity {

	public ARTileEntity(TileEntityType<?> tileEntityTypeIn) {
		super(tileEntityTypeIn);
	}
	
	@ObjectHolder(References.MODID + ":ritual_stone")
	public static TileEntityType<RitualStone> RITUAL_STONE;

}
