package dev.lu15.legacynetty;

import io.netty.util.Version;
import java.util.Map;
import net.fabricmc.api.ModInitializer;
import net.legacyfabric.fabric.api.logger.v1.Logger;
import org.jetbrains.annotations.NotNull;

public final class LegacyNetty implements ModInitializer {

    private static final @NotNull Logger LOGGER = Logger.get("legacy-netty");

    @Override
    public void onInitialize() {
        Map<String, Version> versionMap = Version.identify();
        if (!versionMap.containsKey("netty-all")) throw new RuntimeException("Netty version not found");
        LOGGER.info("Using " + versionMap.get("netty-all"));
    }

}
