package com.hijackster99.ancientrelics.tileentity.ritual.wrappers;

import java.util.List;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.blocks.VoidGas;
import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.ritual.TileEntityWrapper;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.entity.Entity.RemovalReason;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.AABB;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public class ExtractWrapper extends TileEntityWrapper {
	
	private int burnTime;
	private int VPT;
	
	private int VPTModifier;
	private int burnTimeModifier;
	
	private int particle;
	
	private VoidGasTank tank;
	
	private static Capability<IFluidHandler> FLUID_CAP = CapabilityManager.get(new CapabilityToken<>() {});
	
	@Override
	public void init(Level worldIn, BlockPos pos) {
		burnTime = 0;
		VPT = 1;
		VPTModifier = type.contains(ARBlock.RITUAL_STONE_1_PERIDOT) ? 2 : 1;
		burnTimeModifier = type.contains(ARBlock.RITUAL_STONE_1_RUBY) ? 2 : 1;
		particle = 10;
		tank = new VoidGasTank(type.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) ? 20000 : 10000);
	}
	
	@Override
	public void tick(Level worldObj, BlockPos pos) {
		if(burnTime == 0) {
			List<ItemEntity> entities = worldObj.getEntitiesOfClass(ItemEntity.class, new AABB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
			for(ItemEntity item : entities) {
				ForgeHooks.getBurnTime(item.getItem(), RecipeType.SMELTING);
				if(ForgeHooks.getBurnTime(item.getItem(), RecipeType.SMELTING) > 0) {
					burnTime = ForgeHooks.getBurnTime(item.getItem(), RecipeType.SMELTING) * burnTimeModifier;
					if(ItemTags.getAllTags().getTag(new ResourceLocation("ancientrelics:void_coals")).contains(item.getItem().getItem())) {
						VPT = 20 * VPTModifier;
					}else {
						VPT = 1 * VPTModifier;
					}
					worldObj.getBlockEntity(pos).setChanged();
					if(item.getItem().getCount() == 1) item.remove(RemovalReason.DISCARDED);
					else item.getItem().setCount(item.getItem().getCount() - 1);
					worldObj.playSound(null, pos.offset(Direction.UP.getNormal()), SoundEvents.BLAZE_SHOOT, SoundSource.BLOCKS, 1.0f, 1.0f);
					particle = 10;
				}
			}
		}else {
			tank.fill(new FluidStack(VoidGas.VOID_GAS_STILL, VPT), IFluidHandler.FluidAction.EXECUTE);
			burnTime -= VPT;
			particle--;
			worldObj.getBlockEntity(pos).setChanged();
			if(!worldObj.isClientSide() && particle == 0) {
				worldObj.addParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 1.1, pos.getZ() + 0.5, .2d, 0d, .2d);
				particle = 10;
			}
		}
	}
	
	@Override
	public CompoundTag write(CompoundTag compound) {
		compound.putInt("burnTime", burnTime);
		compound.putInt("VPT", VPT);
		tank.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void read(CompoundTag nbt) {
		super.read(nbt);
		tank.readFromNBT(nbt);
		burnTime = nbt.getInt("burnTime");
		VPT = nbt.getInt("VPT");
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return FLUID_CAP.orEmpty(cap, LazyOptional.of(() -> tank));
	}
	
}
