package dev.voltron.stratosphere;

import dev.voltron.stratosphere.content.item.ModItems;
import dev.voltron.stratosphere.init.ModBlocks;
import dev.voltron.stratosphere.init.ModEntities;
import net.fabricmc.api.ModInitializer;

import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Stratosphere implements ModInitializer {
	public static final String MOD_ID = "stratosphere";
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	public static final RegistryKey<World> SPACE = RegistryKey.of(RegistryKeys.WORLD,
			Identifier.of(MOD_ID, "space_world"));

	@Override
	public void onInitialize() {
		ModEntities.init();
		ModItems.registerModItems();
		ModBlocks.init();
	}
}