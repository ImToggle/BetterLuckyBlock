package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.ModConfig;
import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBreakable;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(BlockBreakable.class)
public abstract class BlockBreakableMixin extends Block {

    public BlockBreakableMixin(Material materialIn) {
        super(materialIn);
    }

    @Inject(method = "shouldSideBeRendered", at = @At("HEAD"), cancellable = true)
    private void side(IBlockAccess worldIn, BlockPos pos, EnumFacing side, CallbackInfoReturnable<Boolean> cir) {
        if (ModConfig.INSTANCE.enabled && BetterLuckyBlock.INSTANCE.isLucky(Minecraft.getMinecraft().theWorld.getBlockState(pos.offset(side.getOpposite())))) {
            cir.setReturnValue(true);
        }
    }

}