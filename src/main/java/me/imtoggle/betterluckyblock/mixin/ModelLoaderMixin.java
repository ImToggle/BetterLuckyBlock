package me.imtoggle.betterluckyblock.mixin;

import me.imtoggle.betterluckyblock.BetterLuckyBlock;
import net.minecraft.client.renderer.BlockModelShapes;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.resources.IResourceManager;
import net.minecraft.client.resources.model.ModelBakery;
import net.minecraftforge.client.model.ModelLoader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ModelLoader.class)
public abstract class ModelLoaderMixin extends ModelBakery {
    public ModelLoaderMixin(IResourceManager p_i46085_1_, TextureMap p_i46085_2_, BlockModelShapes p_i46085_3_) {
        super(p_i46085_1_, p_i46085_2_, p_i46085_3_);
    }

    @Inject(method = "loadBlocks", at = @At("HEAD"), remap = false)
    private void loadCustom(CallbackInfo ci) {
        loadVariants(BetterLuckyBlock.INSTANCE.getLocations());
    }
}
