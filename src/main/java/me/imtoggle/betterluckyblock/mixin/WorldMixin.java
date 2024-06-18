package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import me.imtoggle.betterluckyblock.ModConfig;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {

    @Inject(method = "getBlockState", at = @At(value = "RETURN"), cancellable = true)
    private void head(BlockPos pos, CallbackInfoReturnable<IBlockState> cir) {
        IBlockState state = cir.getReturnValue();
        if (state == null) return;
        if (state.getBlock() != Blocks.stained_glass) return;
        boolean isLucky = ModConfig.INSTANCE.enabled && BetterLuckyBlock.INSTANCE.isLuckyBlock(pos, state);
        cir.setReturnValue(state.withProperty(BetterLuckyBlock.INSTANCE.getLUCKY(), isLucky));
    }

}
