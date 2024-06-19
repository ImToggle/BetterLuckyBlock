package me.imtoggle.betterluckyblock

import cc.polyfrost.oneconfig.events.EventManager
import cc.polyfrost.oneconfig.events.event.Stage
import cc.polyfrost.oneconfig.events.event.TickEvent
import cc.polyfrost.oneconfig.libs.eventbus.Subscribe
import cc.polyfrost.oneconfig.utils.dsl.mc
import net.minecraft.block.BlockColored
import net.minecraft.client.renderer.block.statemap.StateMap
import net.minecraft.init.Blocks
import net.minecraftforge.client.model.ModelLoader
import net.minecraftforge.fml.common.Mod
import net.minecraftforge.fml.common.event.FMLInitializationEvent
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent

@Mod(modid = BetterLuckyBlock.MODID, name = BetterLuckyBlock.NAME, version = BetterLuckyBlock.VERSION, modLanguageAdapter = "cc.polyfrost.oneconfig.utils.KotlinLanguageAdapter")
object BetterLuckyBlock {
    const val MODID = "@ID@"
    const val NAME = "@NAME@"
    const val VERSION = "@VER@"

    private var lastEnabled = false

    @Mod.EventHandler
    fun onPreInit(event: FMLPreInitializationEvent) {
        ModelLoader.setCustomStateMapper(Blocks.stained_glass) { block ->
            val map = StateMap.Builder().withName(BlockColored.COLOR).withSuffix("_stained_glass").ignore(LUCKY).build().putStateModelLocations(block)
            block.blockState.validStates
                .filter { it.getValue(LUCKY) && it.getValue(BlockColored.COLOR) in colorMap.values }
                .associateWithTo(map) { getModelPath(it.getValue(BlockColored.COLOR)) }
        }
    }

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

}