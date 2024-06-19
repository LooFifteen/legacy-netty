package dev.lu15.legacynetty.nativetransport;

import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.logger.v1.Logger;
import org.jetbrains.annotations.NotNull;

public final class LegacyNettyNativeTransport implements ModInitializer {

    private static final @NotNull Logger LOGGER = Logger.get("legacy-netty-native-transport");

    @Override
    public void onInitialize() {
        LOGGER.info("Using native transport: " + NativeTransportOption.BEST.name());
    }

}
