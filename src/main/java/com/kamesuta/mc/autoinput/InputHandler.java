package com.kamesuta.mc.autoinput;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	private static final KeyBinding KEY_BINDING_LOAD = new KeyBinding("autoinputmod.key.load", Keyboard.KEY_K,
			"autoinputmod.key.category");

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_LOAD };

	private final Minecraft minecraft = Minecraft.getMinecraft();

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(InputEvent event) {
		if (this.minecraft.currentScreen == null) {
			if (KEY_BINDING_LOAD.isPressed()) {
//				this.minecraft.displayGuiScreen(new GuiSchematicLoad(this.minecraft.currentScreen));
				ClientTickHandler.enableclick = !ClientTickHandler.enableclick;
			}
		}
	}
}
