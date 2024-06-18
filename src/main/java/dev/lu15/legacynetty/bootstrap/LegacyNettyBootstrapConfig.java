package dev.lu15.legacynetty.bootstrap;

import io.netty.bootstrap.AbstractBootstrapConfig;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;

public final class LegacyNettyBootstrapConfig extends AbstractBootstrapConfig<Bootstrap, Channel> {

    public LegacyNettyBootstrapConfig(Bootstrap bootstrap) {
        super(bootstrap);
    }

}
