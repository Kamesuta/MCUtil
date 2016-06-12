package com.kamesuta.mc.mcutil;

import com.kamesuta.mc.mcutil.notice.Notice;

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

@Mod(modid = Reference.MODID, name = Reference.NAME, version = Reference.VERSION)
public class McUtilMod {
	@Instance(Reference.MODID)
	public static McUtilMod instance;

	// レンダーIDの取得
	public static int RenderID;

	public static final Notice notice = new Notice();
	public static ConfigurationHandler config;

	@EventHandler
	public void perInit(final FMLPreInitializationEvent event) {
		Reference.logger = event.getModLog();

		ConfigurationHandler.init(event.getSuggestedConfigurationFile());

		for (final KeyBinding keyBinding : InputHandler.KEY_BINDINGS) {
			ClientRegistry.registerKeyBinding(keyBinding);
		}
	}

	@EventHandler
	public void init(final FMLInitializationEvent event) {
		FMLCommonHandler.instance().bus().register(InputHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ClientTickHandler.INSTANCE);
		FMLCommonHandler.instance().bus().register(ConfigurationHandler.INSTANCE);
		MinecraftForge.EVENT_BUS.register(ChatEventHandler.INSTANCE);
	}

	@EventHandler
	public void postInit(final FMLPostInitializationEvent event) {

	}

	@EventHandler
	public void serverStarting(final FMLServerStartingEvent event) {

	}
}