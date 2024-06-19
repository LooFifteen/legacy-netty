package dev.lu15.legacynetty.nativetransport.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import dev.lu15.legacynetty.nativetransport.NativeTransportOption;
import io.netty.channel.EventLoopGroup;
import java.nio.channels.SocketChannel;
import net.minecraft.network.ClientConnection;
import net.minecraft.util.Lazy;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ClientConnection.class)
public abstract class ClientConnectionMixin {

    @Inject(
            method = "connect",
            at = @At(value = "INVOKE", target = "Lio/netty/bootstrap/Bootstrap;<init>()V", remap = false)
    )
    private static void onConnect(@NotNull CallbackInfoReturnable<ClientConnection> cir, @Local Class<? extends SocketChannel> class_, @Local Lazy<? extends EventLoopGroup> lazy) {
        NativeTransportOption nativeTransportOption = NativeTransportOption.BEST;
        class_ = nativeTransportOption.getChannelClass();
        lazy = nativeTransportOption.create();
    }

}
