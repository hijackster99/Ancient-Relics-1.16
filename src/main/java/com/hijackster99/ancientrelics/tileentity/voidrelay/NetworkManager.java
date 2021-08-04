package com.hijackster99.ancientrelics.tileentity.voidrelay;

import java.util.ArrayList;
import java.util.List;

import com.hijackster99.ancientrelics.core.References;

import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

@EventBusSubscriber(modid = References.MODID)
public class NetworkManager {

	public static final NetworkManager INSTANCE = new NetworkManager();
	
	private List<Network> activeNetworks;
	
	private NetworkManager() {
		activeNetworks = new ArrayList<Network>();
	}
	
	public boolean addNetwork(Network net) {
		if(activeNetworks.contains(net)) return false;
		activeNetworks.add(net);
		return true;
	}
	
	public Network removeNetwork(Network net) {
		activeNetworks.remove(net);
		return net;
	}
	
	@SubscribeEvent
	public static void worldTick(WorldTickEvent event) {
		for(Network net : INSTANCE.activeNetworks) {
			if(net.isLoaded()) net.tick();
		}
	}
	
}
