package com.github._8ml.core.module.game.sfx;
/*
Created by @8ML (https://github.com/8ML) on July 04 2021
*/

import org.bukkit.Sound;
import org.bukkit.entity.Player;

public class SoundEffect {

    private final Sound sound;
    private final float volume;
    private final float pitch;

    public SoundEffect(Sound sound, float volume, float pitch) {
        this.sound = sound;
        this.volume = volume;
        this.pitch = pitch;
    }

    public void play(Player player) {
        player.playSound(player.getLocation(), this.sound, this.volume, this.pitch);
    }

    public Sound getSound() {
        return sound;
    }

    public float getVolume() {
        return volume;
    }

    public float getPitch() {
        return pitch;
    }
}
