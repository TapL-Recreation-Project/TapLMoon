package dev.barfuzzle99.taplmoon.taplmoon;

import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.NoiseSamplingSettings;
import net.minecraft.server.v1_16_R3.NoiseSettings;
import net.minecraft.server.v1_16_R3.NoiseSlideSettings;

public class NoiseSettingsBuilder {

    private int seaLevel = 63;
    private boolean disableMobGeneration = false;
    private Block defaultBlock = Blocks.STONE;
    private Block defaultFluid = Blocks.WATER;
    private NoiseSlideSettings topSlide = new NoiseSlideSettings(
            -10, 3, 0
    );
    private NoiseSlideSettings bottomSlide = new NoiseSlideSettings(
            -30, 0, 0
    );
    // xz scale, xz factor, y scale, y factor
    private NoiseSamplingSettings sampling = new NoiseSamplingSettings(
            0.9999999814507745, 0.9999999814507745, 80, 160
    );
    private int generationHeight = 256;
    private double densityFactor = 1.0;
    private double densityOffset = -0.46875;
    private int horizontalSize = 1;
    private int verticalSize = 2;
    private boolean useSimplexSurfaceNoise = true;
    private boolean useRandomDensityOffset = true;
    private boolean doIslandNoiseOverride = false;
    private boolean isAmplified = false;

    public NoiseSettingsBuilder setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
        return this;
    }

    public NoiseSettingsBuilder setDisableMobGeneration(boolean disableMobGeneration) {
        this.disableMobGeneration = disableMobGeneration;
        return this;
    }

    public NoiseSettingsBuilder setDefaultBlock(Block defaultBlock) {
        this.defaultBlock = defaultBlock;
        return this;
    }

    public NoiseSettingsBuilder setDefaultFluid(Block defaultFluid) {
        this.defaultFluid = defaultFluid;
        return this;
    }

    public NoiseSettingsBuilder setTopSlide(NoiseSlideSettings topSlide) {
        this.topSlide = topSlide;
        return this;
    }

    public NoiseSettingsBuilder setBottomSlide(NoiseSlideSettings bottomSlide) {
        this.bottomSlide = bottomSlide;
        return this;
    }

    public NoiseSettingsBuilder setSampling(NoiseSamplingSettings sampling) {
        this.sampling = sampling;
        return this;
    }

    public NoiseSettingsBuilder setGenerationHeight(int generationHeight) {
        this.generationHeight = generationHeight;
        return this;
    }

    public NoiseSettingsBuilder setDensityFactor(double densityFactor) {
        this.densityFactor = densityFactor;
        return this;
    }

    public NoiseSettingsBuilder setDensityOffset(double densityOffset) {
        this.densityOffset = densityOffset;
        return this;
    }

    public NoiseSettingsBuilder setHorizontalSize(int horizontalSize) {
        this.horizontalSize = horizontalSize;
        return this;
    }

    public NoiseSettingsBuilder setVerticalSize(int verticalSize) {
        this.verticalSize = verticalSize;
        return this;
    }

    public NoiseSettingsBuilder setUseSimplexSurfaceNoise(boolean useSimplexSurfaceNoise) {
        this.useSimplexSurfaceNoise = useSimplexSurfaceNoise;
        return this;
    }

    public NoiseSettingsBuilder setUseRandomDensityOffset(boolean useRandomDensityOffset) {
        this.useRandomDensityOffset = useRandomDensityOffset;
        return this;
    }

    public NoiseSettingsBuilder setDoIslandNoiseOverride(boolean doIslandNoiseOverride) {
        this.doIslandNoiseOverride = doIslandNoiseOverride;
        return this;
    }

    public NoiseSettingsBuilder setAmplified(boolean amplified) {
        isAmplified = amplified;
        return this;
    }

    public NoiseSettings build() {
        return new NoiseSettings(generationHeight, sampling, topSlide, bottomSlide, horizontalSize, verticalSize,
                densityFactor, densityOffset, useSimplexSurfaceNoise, useRandomDensityOffset, doIslandNoiseOverride, isAmplified);
    }
}
