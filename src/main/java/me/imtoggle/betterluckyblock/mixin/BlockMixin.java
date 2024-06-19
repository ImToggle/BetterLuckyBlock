package me.imtoggle.betterluckyblock.mixin;

import net.minecraft.block.Block;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Constant;
import org.spongepowered.asm.mixin.injection.ModifyConstant;

@Mixin(Block.class)
public abstract class BlockMixin {
    @ModifyConstant(method = "getStateById", constant = @Constant(intValue = 0xF))
    private static int moreDigitLa(int constant) {
        return 0xFFFFFFFF;
    }
}
