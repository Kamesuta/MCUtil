package com.kamesuta.mc.mcutil;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.MinecraftForge;

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION, guiFactory = "com.kamesuta.mc.mcutil.ConfigGuiFactory")
public class McUtilMod {
	@Instance(Reference.MODID)
	public static McUtilMod instance;

	// レンダーIDの取得
	public static int RenderID;

	@EventHandler
	public void perInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();

		Config.init(event.getSuggestedConfigurationFile(), "1.0.0");

		for (final KeyBinding keyBinding : InputHandler.KEY_BINDINGS)
			ClientRegistry.registerKeyBinding(keyBinding);
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(InputHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ClientTickHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ChatEventHandler.INSTANCE);
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent event) {

	}

	@EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {

	}
}