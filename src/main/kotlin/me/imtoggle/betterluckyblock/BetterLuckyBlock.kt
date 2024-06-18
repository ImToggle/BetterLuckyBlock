package me.imtoggle.betterluckyblock

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.Stage
import cc.polyfrost.oneconfig.events.event.TickEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.block.BlockStainedGlass
import net.minecraft.block.properties.PropertyBool
import net.minecraft.block.state.IBlockState
import net.minecraft.client.resources.model.IBakedModel
import net.minecraft.client.resources.model.ModelManager
import net.minecraft.client.resources.model.ModelResourceLocation
import net.minecraft.entity.item.EntityArmorStand
import net.minecraft.item.EnumDyeColor
import net.minecraft.item.ItemSkull
import net.minecraft.item.ItemStack
import net.minecraft.util.AxisAlignedBB
import net.minecraft.util.BlockPos
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent

@Mod(modid = BetterLuckyBlock.MODID, name = BetterLuckyBlock.NAME, version = BetterLuckyBlock.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object BetterLuckyBlock {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    val colors = listOf(1, 11, 13, 14, 15)

    val locationMap = HashMap<Int, ModelResourceLocation>()

    val typeMap = hashMapOf(
        "§6Normal Lucky Block" to 1,
        "§9Promising Lucky Block" to 11,
        "§aFortunate Lucky Block" to 13,
        "§5Offensive Lucky Block" to 15,
        "§cMiracle Lucky Block" to 14
    )

    var lastEnabled = false

    val LUCKY = PropertyBool.create("lucky")

    @Mod.EventHandler
    fun onInit(event: FMLInitializationEvent) {
        ModConfig
        EventManager.INSTANCE.register(this)
    }

    @Subscribe
    fun onTick(e: TickEvent) {
        if (e.stage != Stage.END) return
        if (ModConfig.enabled != lastEnabled) {
            lastEnabled = ModConfig.enabled
            mc.renderGlobal.loadRenderers()
        }
    }

    init {
        for (color in colors) {
            locationMap[color] = ModelResourceLocation("${MODID}:luckyblock", EnumDyeColor.byMetadata(color).getName())
        }
    }

    fun getLocations(): Collection<ModelResourceLocation> {
        return locationMap.values
    }

    fun isLuckyBlock(blockPos: BlockPos?, state: IBlockState?): Boolean {
        if (blockPos == null) return false
        val block = state?.block ?: return false
        if (block !is BlockStainedGlass) return false
        val aabb = with(blockPos) {
            AxisAlignedBB(x.toDouble(), y.toDouble(), z.toDouble(), x.toDouble() + 1, y.toDouble() + 1, z.toDouble() + 1)
        }
        val entity = mc.theWorld.getEntitiesWithinAABB(EntityArmorStand::class.java, aabb).filter { it.posY == blockPos.y - 1.21875 }.getOrNull(0)
            ?: return false
        val itemStack = entity.getCurrentArmor(3) ?: return false

        return itemStack.isLuckySkull()
    }

    fun IBlockState.isLucky(): Boolean {
        return properties.contains(LUCKY) && properties[LUCKY] as Boolean
    }

    fun ItemStack.isLuckySkull() =
        item is ItemSkull && displayName in typeMap.keys

    fun ModelManager.getLuckyModelByStack(stack: ItemStack): IBakedModel =
        getModel(locationMap[typeMap[stack.displayName]])

    fun ModelManager.getLuckyModelByState(state: IBlockState): IBakedModel =
        getModel(locationMap[state.block.getMetaFromState(state)])

}