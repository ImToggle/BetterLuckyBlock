package me.imtoggle.betterluckyblock

import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.block.BlockStainedGlass
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.client.resources.model.ModelManager
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.item.ItemSkull
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos

val LUCKY: PropertyBool = PropertyBool.create("lucky")

private val typeMap = mapOf(
    "§6Normal Lucky Block" to ModelResourceLocation("${BetterLuckyBlock.MODID}:luckyblock", "orange"),
    "§9Promising Lucky Block" to ModelResourceLocation("${BetterLuckyBlock.MODID}:luckyblock", "blue"),
    "§aFortunate Lucky Block" to ModelResourceLocation("${BetterLuckyBlock.MODID}:luckyblock", "green"),
    "§5Offensive Lucky Block" to ModelResourceLocation("${BetterLuckyBlock.MODID}:luckyblock", "black"),
    "§cMiracle Lucky Block" to ModelResourceLocation("${BetterLuckyBlock.MODID}:luckyblock", "red")
)

fun isLuckySkull(item: ItemStack?): Boolean {
    if (!ModConfig.enabled) return false
    item ?: return false
    return item.item is ItemSkull && item.displayName in typeMap
}

fun ModelManager.getLuckyItemModel(item: ItemStack): IBakedModel =
    getModel(typeMap[item.displayName])


fun isLuckyBlock(pos: BlockPos?): Boolean {
    if (!ModConfig.enabled) return false
    pos ?: return false
    val state = mc.theWorld?.getBlockState(pos) ?: return false
    if (state.block !is BlockStainedGlass) return false
    return state.getValue(LUCKY)
}

fun isLuckyBlock(pos: BlockPos?, state: IBlockState?): Boolean {
    if (!ModConfig.enabled) return false
    pos ?: return false
    state ?: return false
    if (state.block !is BlockStainedGlass) return false
    if (state.getValue(LUCKY)) return true
    val box = with(pos) {
        AxisAlignedBB(x.toDouble(), y.toDouble(), z.toDouble(), x.toDouble() + 1.0, y.toDouble() + 1.0, z.toDouble() + 1.0)
    }
    val entities = mc.theWorld.getEntitiesWithinAABB(EntityArmorStand::class.java, box)
    val entity = entities.firstOrNull { it.posY == pos.y - 1.21875 } ?: return false
    return isLuckySkull(entity.getCurrentArmor(3))
}
