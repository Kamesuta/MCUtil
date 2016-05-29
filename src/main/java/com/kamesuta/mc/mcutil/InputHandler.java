package com.kamesuta.mc.mcutil;

import org.lwjgl.input.Keyboard;

import com.kamesuta.mc.mcutil.screenshot.Action;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import net.minecraft.client.settings.KeyBinding;

public class InputHandler {
	public static final InputHandler INSTANCE = new InputHandler();

	private final Action screenshotaction = new Action();

	private static final KeyBinding KEY_BINDING_GUI = new KeyBinding("mcutilmod.key.gui", Keyboard.KEY_O,
			"mcutil.key.category");

	public static final KeyBinding[] KEY_BINDINGS = new KeyBinding[] { KEY_BINDING_GUI };

	private InputHandler() {
	}

	@SubscribeEvent
	public void onKeyInput(final InputEvent event) {
		if (KEY_BINDING_GUI.isPressed()) {
			this.screenshotaction.screenShot();
		}
	}
}
