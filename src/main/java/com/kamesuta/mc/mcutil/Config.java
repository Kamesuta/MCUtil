package com.kamesuta.mc.mcutil;

import java.io.File;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.kamesuta.mc.mcutil.ConfigBase.ConfigProperty;

public class Config {
	private static @Nullable Config instance;

	private ConfigBase config;

	public final ConfigProperty<Boolean> notice;
	public final ConfigProperty<Boolean> afk;
	public final ConfigProperty<Boolean> avoidvoid;
	public final ConfigProperty<String> avoidvoidcommand;

	private Config(final @Nonnull File staticFile, final @Nonnull String version) {
		// init static config
		this.config = new ConfigBase(staticFile, version);

		this.notice = this.config.propertyBoolean(this.config.get("Features", "Notice", true, "Enable notice balloon.").setLanguageKey("mcutil.config.enablenotice"));
		this.afk = this.config.propertyBoolean(this.config.get("Features", "AFK", true, "Enable AFK chat").setLanguageKey("mcutil.config.enableafk"));
		this.avoidvoid = this.config.propertyBoolean(this.config.get("Features", "AvoidVoid", true, "Enable avoid Void").setLanguageKey("mcutil.config.enableavoidvoid"));
		this.avoidvoidcommand = this.config.propertyString(this.config.get("Features", "AvoidVoidCommand", "/spawn", "Avoid Void Command").setLanguageKey("mcutil.config.avoidvoidcommand"));
	}

	public ConfigBase getBase() {
		return this.config;
	}

	public void save() {
		this.config.save();
	}

	public static @Nonnull Config getConfig() {
		if (instance!=null)
			return instance;
		throw new IllegalStateException("config not initialized");
	}

	public static void init(final @Nonnull File staticFile, final @Nonnull String version) {
		instance = new Config(staticFile, version);
	}
}
