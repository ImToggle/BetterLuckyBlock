package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.HooksKt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumWorldBlockLayer;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Iterator;

@Mixin(RenderChunk.class)
public abstract class RenderChunkMixin {
    @Unique
    private static boolean lucky$temp = false;

    @Dynamic("OptiFine")
    @Inject(method = "fixBlockLayer", at = @At("HEAD"), cancellable = true, remap = false)
    public void modifyLayer(IBlockState state, EnumWorldBlockLayer layer, CallbackInfoReturnable<EnumWorldBlockLayer> cir) {
        if (!lucky$temp) return;
        cir.setReturnValue(EnumWorldBlockLayer.SOLID);
    }

    @Redirect(method = "rebuildChunk", at = @At(value = "INVOKE", target = "Ljava/util/Iterator;next()Ljava/lang/Object;"))
    public Object capturePos(Iterator<BlockPos> instance) {
        BlockPos pos = instance.next();
        lucky$temp = HooksKt.isLuckyBlock(pos);
        return pos;
    }
}
