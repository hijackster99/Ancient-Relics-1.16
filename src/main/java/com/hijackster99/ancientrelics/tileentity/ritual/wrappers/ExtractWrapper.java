package com.hijackster99.ancientrelics.tileentity.ritual.wrappers;

import java.util.List;

import com.hijackster99.ancientrelics.blocks.ARBlock;
import com.hijackster99.ancientrelics.blocks.VoidGas;
import com.hijackster99.ancientrelics.core.VoidGasTank;
import com.hijackster99.ancientrelics.tileentity.ritual.TileEntityWrapper;

import net.minecraft.block.BlockState;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
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
	
	@CapabilityInject(IFluidHandler.class)
	private static Capability<IFluidHandler> FLUID_CAP = null;
	
	@Override
	public void init(World worldIn, BlockPos pos) {
		burnTime = 0;
		VPT = 1;
		VPTModifier = type.contains(ARBlock.RITUAL_STONE_1_PERIDOT) ? 2 : 1;
		burnTimeModifier = type.contains(ARBlock.RITUAL_STONE_1_RUBY) ? 2 : 1;
		particle = 10;
		tank = new VoidGasTank(type.contains(ARBlock.RITUAL_STONE_1_SAPPHIRE) ? 20000 : 10000);
	}
	
	@SuppressWarnings("deprecation")
	@Override
	public void tick(World worldObj, BlockPos pos) {
		if(burnTime == 0) {
			List<ItemEntity> entities = worldObj.getEntitiesOfClass(ItemEntity.class, new AxisAlignedBB(pos.getX(), pos.getY() + 1, pos.getZ(), pos.getX() + 1, pos.getY() + 2, pos.getZ() + 1));
			for(ItemEntity item : entities) {
				ForgeHooks.getBurnTime(item.getItem());
				if(ForgeHooks.getBurnTime(item.getItem()) > 0) {
					burnTime = ForgeHooks.getBurnTime(item.getItem()) * burnTimeModifier;
					if(ItemTags.getAllTags().getTag(new ResourceLocation("ancientrelics:void_coals")).contains(item.getItem().getItem())) {
						VPT = 20 * VPTModifier;
					}else {
						VPT = 1 * VPTModifier;
					}
					worldObj.getBlockEntity(pos).setChanged();
					if(item.getItem().getCount() == 1) item.remove();
					else item.getItem().setCount(item.getItem().getCount() - 1);
					worldObj.playSound(null, pos.offset(Direction.UP.getNormal()), SoundEvents.BLAZE_SHOOT, SoundCategory.BLOCKS, 1.0f, 1.0f);
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
	public CompoundNBT write(CompoundNBT compound) {
		compound.putInt("burnTime", burnTime);
		compound.putInt("VPT", VPT);
		tank.writeToNBT(compound);
		return compound;
	}
	
	@Override
	public void read(BlockState state, CompoundNBT nbt) {
		super.read(state, nbt);
		tank.readFromNBT(nbt);
		burnTime = nbt.getInt("burnTime");
		VPT = nbt.getInt("VPT");
	}
	
	@Override
	public <T> LazyOptional<T> getCapability(Capability<T> cap, Direction side) {
		return FLUID_CAP.orEmpty(cap, LazyOptional.of(() -> tank));
	}
	
}
