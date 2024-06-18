package dev.lu15.legacynetty.mixin.common;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import io.netty.handler.codec.DecoderException;
import net.minecraft.util.PacketByteBuf;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;
import org.spongepowered.asm.mixin.injection.Redirect;

// todo: tidy up these mixins so that @Overwrite is only used when necessary
@Mixin(PacketByteBuf.class)
public abstract class PacketByteBufMixin extends ByteBuf {

    @Shadow public abstract int readVarInt();

    /**
     * @author LooFifteen
     * @reason prevent using ByteBuf#array()
     */
    @Overwrite
    public String readString(int i) {
        int j = this.readVarInt();
        if (j > i * 4) {
            throw new DecoderException("The received encoded string buffer length is longer than maximum allowed (" + j + " > " + i * 4 + ")");
        } else if (j < 0) {
            throw new DecoderException("The received encoded string buffer length is less than zero! Weird string!");
        } else {
            byte[] bytes = new byte[j];
            this.readBytes(bytes);
            String string = new String(bytes, Charsets.UTF_8);
            if (string.length() > i) {
                throw new DecoderException("The received string length is longer than maximum allowed (" + j + " > " + i + ")");
            } else {
                return string;
            }
        }
    }

}
