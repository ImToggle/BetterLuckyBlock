package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.ModConfig;
import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.optifine.model.BlockModelCustomizer;
import net.optifine.render.RenderEnv;
import org.spongepowered.asm.mixin.Dynamic;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Pseudo;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Pseudo
@Mixin(value = BlockModelCustomizer.class, remap = false)
public class BlockModelCustomizerMixin {

    @Dynamic("Optifine")
    @Inject(method = "getRenderQuads", at = @At("HEAD"), cancellable = true)
    private static void optifine(List<BakedQuad> quads, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, EnumWorldBlockLayer layer, long rand, RenderEnv renderEnv, CallbackInfoReturnable<List<BakedQuad>> cir) {
        if (ModConfig.INSTANCE.enabled && BetterLuckyBlock.INSTANCE.isLucky(Minecraft.getMinecraft().theWorld.getBlockState(posIn))) {
            cir.setReturnValue(quads);
        }
    }
}