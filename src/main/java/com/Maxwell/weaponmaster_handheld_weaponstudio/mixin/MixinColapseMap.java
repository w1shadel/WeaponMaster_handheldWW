package com.Maxwell.weaponmaster_handheld_weaponstudio.mixin;

import com.sky.weaponmaster.ColapseMap;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.HashMap;

@Mixin(ColapseMap.class)
public abstract class MixinColapseMap extends HashMap<Integer, ResourceLocation> {
    @Overwrite(remap = false)
    public ResourceLocation getCol(Integer key) {
        if (key == null || key < 0) return null;
        if (this.containsKey(key)) return this.get(key);
        int bestKey = -1;
        for (Integer registeredLevel : this.keySet()) {
            if (registeredLevel <= key && registeredLevel > bestKey) {
                bestKey = registeredLevel;
            }
        }
        return bestKey != -1 ? this.get(bestKey) : null;
    }
}