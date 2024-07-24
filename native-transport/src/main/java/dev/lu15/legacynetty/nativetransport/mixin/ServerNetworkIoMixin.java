package dev.lu15.legacynetty.nativetransport.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import dev.lu15.legacynetty.nativetransport.NativeTransportOption;
import io.netty.channel.EventLoopGroup;
import java.nio.channels.ServerSocketChannel;
import net.minecraft.server.ServerNetworkIo;
import net.minecraft.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerNetworkIo.class)
public abstract class ServerNetworkIoMixin {

    @Inject(
            method = "bind",
            at = @At(value = "INVOKE", target = "Ljava/util/List;add(Ljava/lang/Object;)Z")
    )
    private void legacy_netty$injectNativeTransport(
            @NotNull CallbackInfo callback,
            @Local LocalRef<Class<? extends ServerSocketChannel>> class_,
            @Local LocalRef<Lazy<? extends EventLoopGroup>> lazy
    ) {
        NativeTransportOption nativeTransportOption = NativeTransportOption.BEST;
        class_.set(nativeTransportOption.getChannelClass());
        lazy.set(nativeTransportOption.create());
    }

}
