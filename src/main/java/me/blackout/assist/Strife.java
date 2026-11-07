package me.blackout.assist;

import me.blackout.assist.eventBus.EventBus;
import me.blackout.assist.eventBus.EventHandler;
import me.blackout.assist.events.SendMessageEvent;
import me.blackout.assist.utils.Utils;
import net.fabricmc.api.ModInitializer;

import net.minecraft.client.Minecraft;
import net.minecraft.resources.Identifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Strife implements ModInitializer {
	public static final String MOD_ID = "pvp-assist";
	public static final Logger LOG = LoggerFactory.getLogger(MOD_ID);
	public static EventBus EVENT_BUS = new EventBus();

	public static Minecraft mc;

	@Override
	public void onInitialize() {
		LOG.info("Initializing PvP Assist");

		// Init global client accessor
		mc = Minecraft.getInstance();

		// Subscribe
		EVENT_BUS.subscribe(this);

		// Register necessity
		new Utils().register();
	}

	@EventHandler
	public void onSendMessage(SendMessageEvent event) {
		if (event.getMessage().equals(".")) event.cancel();
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
