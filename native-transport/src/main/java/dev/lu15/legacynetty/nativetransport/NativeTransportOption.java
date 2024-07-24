package dev.lu15.legacynetty.nativetransport;

import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollSocketChannel;
import io.netty.channel.kqueue.KQueue;
import io.netty.channel.kqueue.KQueueEventLoopGroup;
import io.netty.channel.kqueue.KQueueSocketChannel;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.incubator.channel.uring.IOUring;
import io.netty.incubator.channel.uring.IOUringEventLoopGroup;
import io.netty.incubator.channel.uring.IOUringSocketChannel;
import java.util.function.Supplier;
import java.util.stream.Stream;
import net.minecraft.util.Lazy;
import org.jetbrains.annotations.NotNull;

public enum NativeTransportOption {

    IO_URING(IOUring.isAvailable(), IOUringSocketChannel.class, IOUringEventLoopGroup::new),
    KQUEUE(KQueue.isAvailable(), KQueueSocketChannel.class, KQueueEventLoopGroup::new),
    EPOLL(Epoll.isAvailable(), EpollSocketChannel.class, EpollEventLoopGroup::new),
    NIO(true, NioSocketChannel.class, NioEventLoopGroup::new);

    public static final NativeTransportOption BEST = Stream.of(NativeTransportOption.values())
            .filter(NativeTransportOption::isSupported)
            .findFirst()
            .orElse(NativeTransportOption.NIO);

    private final boolean supported;
    private final @NotNull Class<? extends SocketChannel> channelClass;
    private final @NotNull Lazy<? extends EventLoopGroup> constructor;

    NativeTransportOption(boolean supported, @NotNull Class<? extends SocketChannel> channelClass, @NotNull Supplier<? extends EventLoopGroup> constructor) {
        this.supported = supported;
        this.channelClass = channelClass;
        this.constructor = new Lazy<EventLoopGroup>() {
            @Override
            protected EventLoopGroup create() {
                return constructor.get();
            }
        };
    }

    public boolean isSupported() {
        return supported;
    }

    public @NotNull Lazy<? extends EventLoopGroup> create() {
        return this.constructor;
    }

    @SuppressWarnings("unchecked") // this cast is always safe
    public <T extends SocketChannel> @NotNull Class<T> getChannelClass() {
        return (Class<T>) this.channelClass;
    }

}
