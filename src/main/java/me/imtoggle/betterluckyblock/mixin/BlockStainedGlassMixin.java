package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.BlockPos;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStainedGlass.class)
public abstract class BlockStainedGlassMixin extends Block {
    public BlockStainedGlassMixin(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "createBlockState", at = @At("HEAD"), cancellable = true)
    private void state(CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(new BlockState(this, BlockStainedGlass.COLOR, BetterLuckyBlock.INSTANCE.getLUCKY()));
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        return worldIn.getBlockState(pos);
    }
}