package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import com.hijackster99.ancientrelics.blocks.VoidGas;
import com.hijackster99.ancientrelics.core.VoidGasTank;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.text.StringTextComponent;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler;
import net.minecraftforge.fluids.capability.IFluidHandler.FluidAction;

public class Network {

	public boolean load;
	
	private List<VoidRelay> relays;
	
	private Map<TileEntity, VoidRelay> voidIns;
	private Map<TileEntity, VoidRelay> voidOuts;
	
	private Iterator<TileEntity> voidInIter;
	
	public Network(VoidRelay source) {
		relays = new ArrayList<VoidRelay>();
		voidIns = new HashMap<TileEntity, VoidRelay>();
		voidOuts = new HashMap<TileEntity, VoidRelay>();
		relays.add(source);
		NetworkManager.INSTANCE.addNetwork(this);
	}
	
	public void tick() {
		if(load) {
			VoidRelay relay = relays.get(0);
			if(relay.getVoidIn() != null) {
				voidIns.put(relay.getVoidIn(), relay);
			}if(relay.getVoidOut() != null) {
				voidOuts.put(relay.getVoidOut(), relay);
			}
			addAllConnections(relay);
			load = false;
		}
		if(voidInIter == null || !voidInIter.hasNext()) {
			voidInIter = voidIns.keySet().iterator();
		}
		if(voidInIter.hasNext()) {
			TileEntity te = voidInIter.next();
			if(te.isRemoved()) voidInIter.remove();
			else {
				IFluidHandler tank = te.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
				if(tank != null && tank instanceof VoidGasTank) {
					if(tank.getFluidInTank(0).getAmount() > 0) {
						Iterator<TileEntity> iter = voidOuts.keySet().iterator();
						while(iter.hasNext()) {
							TileEntity te2 = iter.next();
							if(te2.isRemoved()) iter.remove();
							else {
								IFluidHandler tank2 = te2.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY).orElse(null);
								if(tank2 != null && tank2 instanceof VoidGasTank) {
									int maxTransfer = Math.min(voidIns.get(te).maxTransfer, voidOuts.get(te2).maxTransfer);
									int transfer = Math.min(Math.min(tank.getFluidInTank(0).getAmount(), tank2.getTankCapacity(0) - tank2.getFluidInTank(0).getAmount()), maxTransfer);
									tank.drain(transfer, FluidAction.EXECUTE);
									tank2.fill(new FluidStack(VoidGas.VOID_GAS_STILL, transfer), FluidAction.EXECUTE);
								}
							}
						}
					}
				}
			}
		}
	}
	
	public void addVoidIn(VoidRelay relay, TileEntity te) {
		if(!voidIns.containsKey(te)) {
			voidIns.put(te, relay);
			relay.setVoidIn(te.getBlockPos());
		}
	}
	
	public void addVoidOut(VoidRelay relay, TileEntity te) {
		if(!voidOuts.containsKey(te)) {
			voidOuts.put(te, relay);
			relay.setVoidOut(te.getBlockPos());
		}
	}
	
	public boolean addRelay(VoidRelay relay1, VoidRelay relay2, PlayerEntity playerIn) {
		if(relay2.getNetwork() == this) {
			if(playerIn != null) playerIn.displayClientMessage(new StringTextComponent("Connection Failed! Relay already in network!"), false);
			return false;
		}
		else {
			mergeNetwork(relay2.getNetwork());
			relay1.addConnection(relay2);
			relay2.addConnection(relay1);
			return true;
		}
	}
	
	public void removeRelay(VoidRelay relay) {
		if(size() == 1) 
			NetworkManager.INSTANCE.removeNetwork(this);
		else {
			for(int i = 1; i < relay.getConnections().size(); i++) {
				VoidRelay r = relay.getRelayConnection(i);
				r.removeConnection(relay);
				Network net = new Network(r);
				r.setNetwork(net);
				net.addAllConnections(r);
			}
			VoidRelay source = relay.getRelayConnection(0);
			source.removeConnection(relay);
			this.clear();
			this.relays.add(source);
			if(source.getVoidIn() != null) {
				voidIns.put(source.getVoidIn(), source);
			}if(source.getVoidOut() != null) {
				voidOuts.put(source.getVoidOut(), source);
			}
			source.setNetwork(this);
			this.addAllConnections(source);
		}
	}
	
	public void breakConnection(VoidRelay relay1, VoidRelay relay2) {
		relay1.removeConnection(relay2);
		this.clear();
		this.relays.add(relay1);
		if(relay1.getVoidIn() != null) {
			voidIns.put(relay1.getVoidIn(), relay1);
		}if(relay1.getVoidOut() != null) {
			voidOuts.put(relay1.getVoidOut(), relay1);
		}
		relay1.setNetwork(this);
		this.addAllConnections(relay1);
		
		relay2.removeConnection(relay1);
		Network net = new Network(relay2);
		net.relays.add(relay2);
		if(relay2.getVoidIn() != null) {
			net.voidIns.put(relay2.getVoidIn(), relay2);
		}if(relay2.getVoidOut() != null) {
			net.voidOuts.put(relay2.getVoidOut(), relay2);
		}
		relay2.setNetwork(net);
		net.addAllConnections(relay2);
	}
	
	public void removeVoidIn(TileEntity te) {
		voidIns.remove(te);
	}
	
	public void removeVoidOut(TileEntity te) {
		voidOuts.remove(te);
	}
	
	public void mergeNetwork(Network other) {
		if(size() >= other.size()) {
			for(VoidRelay relay : other.relays) {
				relay.setNetwork(this);
				checkVoids(relay);
				relays.add(relay);
			}
			NetworkManager.INSTANCE.removeNetwork(other);
		}else {
			for(VoidRelay relay : relays) {
				relay.setNetwork(other);
				other.checkVoids(relay);
				other.relays.add(relay);
			}
			NetworkManager.INSTANCE.removeNetwork(this);
		}
	}
	
	public void checkVoids(VoidRelay relay) {
		if(relay.getVoidIn() != null) {
			if(voidIns.containsKey(relay.getVoidIn())) 
				relay.setVoidIn(null);
			else 
				voidIns.put(relay.getVoidIn(), relay);
		}
		if(relay.getVoidOut() != null) {
			if(voidOuts.containsKey(relay.getVoidOut())) 
				relay.setVoidOut(null);
			else 
				voidOuts.put(relay.getVoidOut(), relay);
		}
	}
	
	public void addAllConnections(VoidRelay relay) {
		for(int i = 0; i < relay.getConnections().size(); i++) {
			if(addRelay(relay, relay.getRelayConnection(i), null)) {
				addAllConnections(relay.getRelayConnection(i));
			}
		}
	}
	
	public void clear() {
		relays.clear();
		voidIns.clear();
		voidOuts.clear();
	}
	
	public int size() {
		return relays.size();
	}
	
	public boolean isLoaded() {
		for(TileEntity te : voidIns.keySet()) {
			if(!te.getLevel().isLoaded(te.getBlockPos())) return false;
		}
		for(TileEntity te : voidOuts.keySet()) {
			if(!te.getLevel().isLoaded(te.getBlockPos())) return false;
		}
		return true;
	}
	
}
