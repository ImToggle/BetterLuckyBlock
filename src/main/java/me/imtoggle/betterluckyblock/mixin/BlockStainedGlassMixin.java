package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.HooksKt;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStainedGlass;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockStainedGlass.class)
public abstract class BlockStainedGlassMixin extends Block {
    private BlockStainedGlassMixin(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "createBlockState", at = @At("HEAD"), cancellable = true)
    public void addState(CallbackInfoReturnable<BlockState> cir) {
        cir.setReturnValue(new BlockState(this, BlockStainedGlass.COLOR, HooksKt.getLUCKY()));
    }

    @Override
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side) {
        return HooksKt.isLuckyBlock(pos) || super.shouldSideBeRendered(worldIn, pos, side);
    }

    @Override
    public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
        if (!HooksKt.isLuckyBlock(pos)) return state;
        return state.withProperty(HooksKt.getLUCKY(), true);
    }

    @Inject(method = "getMetaFromState", at = @At("RETURN"), cancellable = true)
    public void writeMeta(IBlockState state, CallbackInfoReturnable<Integer> cir) {
        int lucky = state.getValue(HooksKt.getLUCKY()) ? 1 : 0;
        cir.setReturnValue(cir.getReturnValueI() | lucky << 4);
    }

    @Inject(method = "getStateFromMeta", at = @At("HEAD"), cancellable = true)
    public void readMeta(int meta, CallbackInfoReturnable<IBlockState> cir) {
        IBlockState state = getDefaultState()
            .withProperty(BlockStainedGlass.COLOR, EnumDyeColor.byMetadata(meta & 0xF))
            .withProperty(HooksKt.getLUCKY(), meta >> 4 == 1);
        cir.setReturnValue(state);
    }
}