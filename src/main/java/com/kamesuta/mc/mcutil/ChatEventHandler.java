package com.kamesuta.mc.mcutil;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ClientChatReceivedEvent;

public class ChatEventHandler {
	public static final ChatEventHandler INSTANCE = new ChatEventHandler();

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private ChatEventHandler() {}

	@SubscribeEvent
	public void onClientChatReceivedEvent(final ClientChatReceivedEvent event) {
		final String message = event.message.getFormattedText();

		final boolean msg = message.startsWith("\u00a77") && message.contains("whispers");
		final boolean global = message.startsWith("<");
		if (msg || global) {
			this.minecraft.getSoundHandler().playSound(PositionedSoundRecord
					.func_147673_a(new ResourceLocation(Reference.MODID.toLowerCase() + ":sound_chat")));
		}
	}
}
