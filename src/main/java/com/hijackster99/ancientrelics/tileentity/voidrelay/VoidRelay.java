package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.List;

import com.hijackster99.ancientrelics.tileentity.ARTileEntity;

import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.capabilities.ICapabilityProvider;

public class VoidRelay extends ARTileEntity {

	private Network net;
	
	private List<BlockPos> connections;
	
	private ICapabilityProvider voidOut;
	private ICapabilityProvider voidIn;
	
	public int maxTransfer = 200;
	
	public VoidRelay() {
		super(VOID_RELAY);
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
	public CompoundNBT save(CompoundNBT nbt) {
		int i;
		for(i = 0; i < connections.size(); i++) {
			nbt.putIntArray(Integer.toString(i), new int[] {connections.get(i).getX(), connections.get(i).getY(), connections.get(i).getZ()});
		}
		nbt.putInt("total", i);
		return super.save(nbt);
	}
	
	@Override
	public void load(BlockState state, CompoundNBT nbt) {
		super.load(state, nbt);
		if(net == null) net = new Network(this);
		int total = nbt.getInt("total");
		for(int i = 0; i < total; i++) {
			int[] bp = nbt.getIntArray(Integer.toString(i));
			connections.add(new BlockPos(bp[0], bp[1], bp[2]));
			TileEntity te = level.getBlockEntity(new BlockPos(bp[0], bp[1], bp[2]));
			if(te != null && te instanceof VoidRelay) {
				net.addRelay(this, (VoidRelay) te, null);
			}
		}
	}
	
	@Override
	public void clearCache() {
		super.clearCache();
		net.removeRelay(this);
	}

	public List<BlockPos> getConnections() {
		return connections;
	}
	
	public VoidRelay getRelayConnection(int i) {
		TileEntity te = level.getBlockEntity(connections.get(i));
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
	
	public void addToNet(VoidRelay relay, PlayerEntity playerIn) {
		net.addRelay(this, relay, playerIn);
	}
	
	public void removeFromNet(VoidRelay relay) {
		net.removeRelay(relay);
	}
	
	public void setVoidIn(ICapabilityProvider te) {
		voidIn = te;
	}
	
	public void setVoidOut(ICapabilityProvider te) {
		voidOut = te;
	}
	
	public void setNetVoidIn(ICapabilityProvider te) {
		net.addVoidIn(this, te);
	}
	
	public void setNetVoidOut(ICapabilityProvider te) {
		net.addVoidOut(this, te);
	}
	
	public ICapabilityProvider getVoidIn() {
		return voidIn;
	}
	
	public ICapabilityProvider getVoidOut() {
		return voidOut;
	}

}
