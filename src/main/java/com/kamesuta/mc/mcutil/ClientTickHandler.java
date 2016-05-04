package com.kamesuta.mc.mcutil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.TickEvent;
import cpw.mods.fml.common.network.FMLNetworkEvent;
import net.minecraft.client.Minecraft;

public class ClientTickHandler {
	public static final ClientTickHandler INSTANCE = new ClientTickHandler();

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private ClientTickHandler() {
	}

	@SubscribeEvent
	public void onClientConnect(final FMLNetworkEvent.ClientConnectedToServerEvent event) {
	}

	@SubscribeEvent
	public void onClientDisconnect(final FMLNetworkEvent.ClientDisconnectionFromServerEvent event) {
	}

	@SubscribeEvent
	public void onClientTick(final TickEvent.ClientTickEvent event) {
		if (event.phase == TickEvent.Phase.START) {
			this.minecraft.mcProfiler.startSection("mcutil");

			this.minecraft.mcProfiler.endSection();
		}
	}
}