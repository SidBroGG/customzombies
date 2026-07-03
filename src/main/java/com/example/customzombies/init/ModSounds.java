package com.example.customzombies.init;

import com.example.customzombies.Customzombies;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(Registries.SOUND_EVENT, Customzombies.MODID);

    public static final DeferredHolder<SoundEvent, SoundEvent> HOMELANDER_ZOMBIE_HURT = SOUND_EVENTS.register("homelander_zombie_hurt", () -> SoundEvent.createVariableRangeEvent(
            ResourceLocation.fromNamespaceAndPath(Customzombies.MODID, "homelander_zombie_hurt")
    ));
}
