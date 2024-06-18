package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.ModConfig;
import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockRendererDispatcher.class)
public class BlockRendererDispatcherMixin {

    @Shadow private BlockModelShapes blockModelShapes;

    @Inject(method = "getModelFromBlockState", at = @At("HEAD"), cancellable = true)
    private void custom(IBlockState state, IBlockAccess worldIn, BlockPos pos, CallbackInfoReturnable<IBakedModel> cir) {
        if (state == null) return;
        if (ModConfig.INSTANCE.enabled && BetterLuckyBlock.INSTANCE.isLucky(Minecraft.getMinecraft().theWorld.getBlockState(pos))) {
            cir.setReturnValue(BetterLuckyBlock.INSTANCE.getLuckyModelByState(blockModelShapes.getModelManager(), state));
        }
    }

}
