package dev.barfuzzle99.taplmoon.taplmoon;

import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.NoiseSamplingSettings;
import net.minecraft.server.v1_16_R3.NoiseSettings;
import net.minecraft.server.v1_16_R3.NoiseSlideSettings;

public class PresetNoiseSettings {
    public static NoiseSettings OVERWORLD = new NoiseSettingsBuilder().build();
    public static NoiseSettings NETHER = new NoiseSettingsBuilder()
            .setDefaultBlock(Blocks.NETHERRACK)
            .setDefaultFluid(Blocks.LAVA)
            .setTopSlide(new NoiseSlideSettings(120, 3, 0))
            .setBottomSlide(new NoiseSlideSettings(320, 4, -1))
            .setSampling(new NoiseSamplingSettings(1, 3, 80, 60))
            .setDensityFactor(0.0)
            .setGenerationHeight(128)
            .setDensityOffset(0.019921875)
            .setUseRandomDensityOffset(false)
            .setUseSimplexSurfaceNoise(false)
            .build();
}
