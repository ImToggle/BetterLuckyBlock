package me.imtoggle.betterluckyblock.mixin;

import cc.polyfrost.oneconfig.libs.universal.UChat;
import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.block.statemap.BlockStateMapper;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.client.renderer.block.statemap.StateMap;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.init.Blocks;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockModelShapes.class)
public class BlockModelShapesMixin {

    @Shadow @Final private ModelManager modelManager;

    @Shadow @Final private BlockStateMapper blockStateMapper;

    @Inject(method = "getModelForState", at = @At("HEAD"), cancellable = true)
    private void model(IBlockState state, CallbackInfoReturnable<IBakedModel> cir) {
        if (BetterLuckyBlock.INSTANCE.isLucky(state)) {
            cir.setReturnValue(BetterLuckyBlock.INSTANCE.getLuckyModelByState(modelManager, state));
        }
    }

    @Inject(method = "registerBlockWithStateMapper", at = @At("HEAD"), cancellable = true)
    private void glass(Block assoc, IStateMapper stateMapper, CallbackInfo ci) {
        if (assoc == Blocks.stained_glass) {
            blockStateMapper.registerBlockStateMapper(assoc, (new StateMap.Builder()).withName(BlockColored.COLOR).ignore(BetterLuckyBlock.INSTANCE.getLUCKY()).withSuffix("_stained_glass").build());
            ci.cancel();
        }
    }
}
