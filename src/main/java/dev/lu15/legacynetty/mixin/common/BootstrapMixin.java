package dev.lu15.legacynetty.mixin.common;

import dev.lu15.legacynetty.bootstrap.LegacyNettyBootstrapConfig;
import io.netty.bootstrap.AbstractBootstrapConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(Bootstrap.class)
public abstract class BootstrapMixin {

    @Unique
    private final @NotNull LegacyNettyBootstrapConfig config = new LegacyNettyBootstrapConfig((Bootstrap) (Object) this);

    @Unique
    public AbstractBootstrapConfig<Bootstrap, Channel> config() {
        return this.config;
    }

}
