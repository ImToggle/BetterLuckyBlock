package net.optifine.model;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.optifine.render.RenderEnv;

import java.util.List;

public class BlockModelCustomizer {

    public static List<BakedQuad> getRenderQuads(List<BakedQuad> quads, IBlockAccess worldIn, IBlockState stateIn, BlockPos posIn, EnumFacing enumfacing, EnumWorldBlockLayer layer, long rand, RenderEnv renderEnv) {
        return null;
    }

}
