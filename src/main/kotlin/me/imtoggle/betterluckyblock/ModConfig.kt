package me.imtoggle.betterluckyblock

import cc.polyfrost.oneconfig.config.Config
import cc.polyfrost.oneconfig.config.data.*

object ModConfig : Config(Mod(BetterLuckyBlock.NAME, ModType.UTIL_QOL), "${BetterLuckyBlock.MODID}.json") {

    init {
        initialize()
    }

}