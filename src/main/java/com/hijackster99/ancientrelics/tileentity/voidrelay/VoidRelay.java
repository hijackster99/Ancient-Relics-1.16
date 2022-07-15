package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.List;

import com.hijackster99.ancientrelics.tileentity.ARTileEntity;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.fml.LogicalSide;
import net.minecraftforge.fml.util.thread.EffectiveSide;

public class VoidRelay extends ARTileEntity {
	
	private Network net;
	
	private List<BlockPos> connections;
	
	private BlockPos voidOut;
	private BlockPos voidIn;
	
	public int maxTransfer = 200;
	
	public VoidRelay(BlockPos pos, BlockState state) {
		super(VOID_RELAY, pos, state);
		if(EffectiveSide.get() == LogicalSide.SERVER)
			net = new Network(this);
		connections = new ArrayList<BlockPos>();
	}
	
	public Network getNetwork() {
		return net;
	}
	
	public void setNetwork(Network net) {
		this.net = net;
	}
	
	@Override
	public CompoundTag save(CompoundTag nbt) {
		CompoundTag tag = new CompoundTag();
		int i;
		for(i = 0; i < connections.size(); i++) {
			tag.putIntArray(Integer.toString(i), new int[] {connections.get(i).getX(), connections.get(i).getY(), connections.get(i).getZ()});
		}
		tag.putInt("total", i);
		if(voidIn != null)
			tag.putIntArray("void_in", new int[] {voidIn.getX(), voidIn.getY(), voidIn.getZ()});
		if(voidOut != null)
			tag.putIntArray("void_out", new int[] {voidOut.getX(), voidOut.getY(), voidOut.getZ()});
		nbt.put("relay", tag);
		return super.save(nbt);
	}
	
	@Override
	public void load(CompoundTag nbt) {
		super.load(nbt);
		CompoundTag tag = nbt.getCompound("relay");
		int total = tag.getInt("total");
		for(int i = 0; i < total; i++) {
			int[] bp = tag.getIntArray(Integer.toString(i));
			connections.add(new BlockPos(bp[0], bp[1], bp[2]));
		}
		if(tag.contains("void_in")) {
			int[] in = tag.getIntArray("void_in");
			voidIn = new BlockPos(in[0], in[1], in[2]);
		}
		if(tag.contains("void_out")) {
			int[] out = tag.getIntArray("void_out");
			voidOut = new BlockPos(out[0], out[1], out[2]);
		}
		if(net != null) net.load = true;
	}
	
	@Override
	public void setRemoved() {
		super.setRemoved();
		if(!level.isClientSide())
			net.removeRelay(this);
	}

	public List<BlockPos> getConnections() {
		return connections;
	}
	
	public VoidRelay getRelayConnection(int i) {
		BlockEntity te = level.getBlockEntity(connections.get(i));
		if(te instanceof VoidRelay) {
			return (VoidRelay) te;
		}
		return null;
	}
	
	public void addConnection(VoidRelay relay) {
		if(!connections.contains(relay.worldPosition))
			connections.add(relay.worldPosition);
	}
	
	public void removeConnection(VoidRelay relay) {
		connections.remove(relay.worldPosition);
	}
	
	public void addToNet(VoidRelay relay, Player playerIn) {
		net.addRelay(this, relay, playerIn);
	}
	
	public void removeFromNet(VoidRelay relay) {
		net.removeRelay(relay);
	}
	
	public void breakConnection(VoidRelay relay) {
		net.breakConnection(this, relay);
	}
	
	public void setVoidIn(BlockPos te) {
		voidIn = te;
	}
	
	public void setVoidOut(BlockPos te) {
		voidOut = te;
	}
	
	public void removeVoidIn() {
		net.removeVoidIn(getVoidIn());
		setVoidIn(null);
	}
	
	public void removeVoidOut() {
		net.removeVoidOut(getVoidOut());
		setVoidOut(null);
	}
	
	public void setNetVoidIn(BlockEntity te) {
		net.addVoidIn(this, te);
	}
	
	public void setNetVoidOut(BlockEntity te) {
		net.addVoidOut(this, te);
	}
	
	public BlockEntity getVoidIn() {
		if(level != null && voidIn != null)
			return level.getBlockEntity(voidIn);
		return null;
	}
	
	public BlockEntity getVoidOut() {
		if(level != null && voidOut != null)
			return level.getBlockEntity(voidOut);
		return null;
	}

}
