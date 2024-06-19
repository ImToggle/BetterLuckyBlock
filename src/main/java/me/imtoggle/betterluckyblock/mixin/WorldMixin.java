package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.HooksKt;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(World.class)
public abstract class WorldMixin {
    @Inject(method = "getBlockState", at = @At(value = "RETURN"), cancellable = true)
    private void overrideState(BlockPos pos, CallbackInfoReturnable<IBlockState> cir) {
        IBlockState state = cir.getReturnValue();
        if (!HooksKt.worldIsLuckyBlock(pos, state)) return;
        cir.setReturnValue(state.withProperty(HooksKt.getLUCKY(), true));
    }
}
