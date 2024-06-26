package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.HooksKt;
import net.minecraft.client.renderer.ItemModelMesher;
import net.minecraft.client.resources.model.IBakedModel;
import net.minecraft.client.resources.model.ModelManager;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemModelMesher.class)
public class ItemModelMesherMixin {
    @Shadow
    @Final
    private ModelManager modelManager;

    @Inject(method = "getItemModel(Lnet/minecraft/item/ItemStack;)Lnet/minecraft/client/resources/model/IBakedModel;", at = @At("HEAD"), cancellable = true)
    private void skull(ItemStack item, CallbackInfoReturnable<IBakedModel> cir) {
        if (!HooksKt.isLuckySkull(item)) return;
        cir.setReturnValue(HooksKt.getLuckyItemModel(modelManager, item));
    }
}
