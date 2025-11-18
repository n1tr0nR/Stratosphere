package dev.voltron.stratosphere;

import dev.voltron.stratosphere.content.item.ModItems;
import dev.voltron.stratosphere.init.ModEntities;
import net.fabricmc.api.ModInitializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stratosphere implements ModInitializer {
	public static final String MOD_ID = "stratosphere";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModEntities.init();
		ModItems.registerModItems();
	}
}