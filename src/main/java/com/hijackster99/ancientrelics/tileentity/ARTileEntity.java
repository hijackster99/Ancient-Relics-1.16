package com.hijackster99.ancientrelics.tileentity;

import com.hijackster99.ancientrelics.core.References;
import com.hijackster99.ancientrelics.tileentity.ritual.RitualStone;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.registries.ObjectHolder;

public class ARTileEntity extends BlockEntity {

	public ARTileEntity(BlockEntityType<?> tileEntityTypeIn, BlockPos pos, BlockState state) {
		super(tileEntityTypeIn, pos, state);
	}
	
	@ObjectHolder(References.MODID + ":ritual_stone")
	public static BlockEntityType<RitualStone> RITUAL_STONE;
	
	@ObjectHolder(References.MODID + ":void_relay")
	public static BlockEntityType<RitualStone> VOID_RELAY;
	
	@ObjectHolder(References.MODID + ":pedestal")
	public static BlockEntityType<RitualStone> PEDESTAL;

}
