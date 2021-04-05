package dev.barfuzzle99.taplmoon.taplmoon;

import de.freesoccerhdx.advancedworldcreator.biomegenerators.MultiNoiseBiomeGenerator;
import de.freesoccerhdx.advancedworldcreator.biomegenerators.OverworldBiomeGenerator;
import de.freesoccerhdx.advancedworldcreator.main.AdvancedCreator;
import de.freesoccerhdx.advancedworldcreator.main.AdvancedWorldCreatorAPI;
import de.freesoccerhdx.advancedworldcreator.main.CustomBiome;
import de.freesoccerhdx.advancedworldcreator.main.CustomDimensionSettings;
import de.freesoccerhdx.advancedworldcreator.main.RegisteredCustomBiome;
import de.freesoccerhdx.advancedworldcreator.wrapper.BiomeStructure;
import de.freesoccerhdx.advancedworldcreator.wrapper.SurfaceType;
import de.freesoccerhdx.advancedworldcreator.wrapper.WorldGenCarver;
import net.minecraft.server.v1_16_R3.BiomeBase;
import net.minecraft.server.v1_16_R3.BiomeDecoratorGroups;
import net.minecraft.server.v1_16_R3.Biomes;
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.NoiseSettings;
import net.minecraft.server.v1_16_R3.ResourceKey;
import net.minecraft.server.v1_16_R3.WorldGenStage;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.awt.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class MoonWorldCreator {

    CommandSender whoCreated;

    public MoonWorldCreator(CommandSender whoCreated) {
        this.whoCreated = whoCreated;
    }

    public void createWorlds() {
        whoCreated.sendMessage("[TapLMoon] Creating world ''moon'' (1/3)");
        createOverworld();
        whoCreated.sendMessage("[TapLMoon] Creating world ''moon_nether'' (2/3)");
        createNether();
        whoCreated.sendMessage("[TapLMoon] Creating world ''moon_the_end'' (3/3)");
        createTheEnd();
        // Kick players to force their clients to know about the newly created biomes
        // Workaround for https://bugs.mojang.com/browse/MC-197616
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.kickPlayer("We had to disconnect you for some maintenance, but you can re-log now");
        }
    }

    final Color GRASS_COLOR = new Color(250,250,140);
    final Color FOLIAGE_COLOR = new Color(250,250,140);
    final Color WATER_COLOR = new Color(220,220,220);

    private void createOverworld() {
        AdvancedCreator advancedCreator = new AdvancedCreator("moon");
        advancedCreator.setSeed(Bukkit.getWorlds().get(0).getSeed());
        CustomBiome moonBiome = new CustomBiome("tapl_moon", "moon");

        // Appearance
        //
        moonBiome.setGrassColor(GRASS_COLOR);
        moonBiome.setFoliageColor(FOLIAGE_COLOR);
        moonBiome.setWaterColor(WATER_COLOR);
        moonBiome.setPrecipitation(BiomeBase.Precipitation.SNOW);
        moonBiome.setCustomSurfaceBuilder(SurfaceType.DEFAULT, Blocks.END_STONE, Blocks.STONE, Blocks.STONE);
        moonBiome.setDepth(0.0225f);
        moonBiome.setScale(0.025f);

        /* TODO: OPTIONALS
            moonBiome.setFogColor(new Color(255, 200, 20));
            moonBiome.setSkyColor(new Color(255, 200, 20));
            moonBiome.setBiomeParticles(Particles.ASH, 0.2f);
        */

        // Structures and carvers
        //
        moonBiome.addBiomeStructure(BiomeStructure.VILLAGE_PLAINS);
        moonBiome.addBiomeStructure(BiomeStructure.STRONGHOLD);
        moonBiome.addBiomeStructure(BiomeStructure.MINESHAFT);
        moonBiome.addBiomeStructure(BiomeStructure.RUINED_PORTAL);
        moonBiome.addWorldGenFeature(WorldGenStage.Features.AIR, WorldGenCarver.CAVE);
        advancedCreator.loadDefaultStructureGeneratorConfig();

        // Ores
        //
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_COAL);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_GOLD);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_EMERALD);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIAMOND);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_INFESTED);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_IRON);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_REDSTONE);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_LAPIS);

        // Note to self: spawn passive mobs manually, they won't spawn on end stone
        // Mostly Minecraft defaults
        moonBiome.addMobToBiome(EntityType.SHEEP, 12 ,4, 4);
        moonBiome.addMobToBiome(EntityType.PIG, 10 ,4, 4);
        moonBiome.addMobToBiome(EntityType.CHICKEN, 10 ,4, 4);
        moonBiome.addMobToBiome(EntityType.COW, 8 ,4, 4);
        moonBiome.addMobToBiome(EntityType.SPIDER, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.ZOMBIE, 95 ,4, 4);
        moonBiome.addMobToBiome(EntityType.ZOMBIE_VILLAGER, 5 ,4, 4);
        moonBiome.addMobToBiome(EntityType.SKELETON, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.CREEPER, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.SLIME, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.ENDERMAN, 10 ,4, 4);
        // moonBiome.addMobToBiome(EntityType.SQUID 12 ,4, 4) -- this is default, but we don't need it
        moonBiome.addMobToBiome(EntityType.WITCH, 5 ,4, 4);

        RegisteredCustomBiome registeredMoonBiome = AdvancedWorldCreatorAPI.registerCustomBiome(moonBiome, false);
        OverworldBiomeGenerator.CREATOR obgCreator = new OverworldBiomeGenerator.CREATOR(advancedCreator.getSeed());
        NoiseSettings noiseSettings = PresetNoiseSettings.OVERWORLD;

        // All biomes
        //
        Field[] fields = Biomes.class.getDeclaredFields();
        for (Field f : fields) {
            if (Modifier.isStatic(f.getModifiers()) && f.getType().equals(Biomes.PLAINS.getClass())) {
                try {
                    obgCreator.overwriteBiome((ResourceKey<BiomeBase>) f.get(null), registeredMoonBiome);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }

        // World settings
        //
        final Block solidMaterial = Blocks.STONE;
        final Block fluidMaterial = Blocks.WATER;
        final int bedrockRoofPosition = -10;
        final int bedrockFloorPosition = 0;
        final int seaLevel = 1;
        final boolean disableMobGeneration = false;

        advancedCreator.setGenerateStructures(true);
        advancedCreator.setCustomWorldSetting(noiseSettings, solidMaterial, fluidMaterial,
                bedrockRoofPosition, bedrockFloorPosition, seaLevel, disableMobGeneration);

        AdvancedWorldCreatorAPI.createWorld(advancedCreator, CustomDimensionSettings.getOverworldSettings(), obgCreator.create());
    }

    private void createNether() {
        AdvancedCreator advancedCreator = new AdvancedCreator("moon_nether");
        CustomBiome moonNetherBiome = new CustomBiome("tapl_moon", "moon_nether_wastes");

        // Appearance
        //
        moonNetherBiome.setGrassColor(GRASS_COLOR);
        moonNetherBiome.setFoliageColor(FOLIAGE_COLOR);
        moonNetherBiome.setWaterColor(WATER_COLOR);
        moonNetherBiome.setFogColor(new Color(100, 20, 20));
        moonNetherBiome.setSkyColor(new Color(100, 20, 20));
        moonNetherBiome.setCustomSurfaceBuilder(SurfaceType.NETHER, Blocks.NETHERRACK, Blocks.END_STONE, Blocks.END_STONE);
        moonNetherBiome.setDepth(0.1f);
        moonNetherBiome.setScale(0.2f);

        // Temperature
        //
        moonNetherBiome.setTemperature(2f);

        // Structures and carvers
        //
        moonNetherBiome.addBiomeStructure(BiomeStructure.FORTRESS);
        moonNetherBiome.addWorldGenFeature(WorldGenStage.Features.AIR, WorldGenCarver.NETHER_CAVE);
        advancedCreator.loadDefaultStructureGeneratorConfig();

        // Decoration and ores
        //
        moonNetherBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_GOLD_NETHER);
        moonNetherBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DEBRIS_SMALL);
        moonNetherBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DEBRIS_LARGE);
        moonNetherBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.SURFACE_STRUCTURES, BiomeDecoratorGroups.PATCH_FIRE);

        // Nether defaults
        //
        moonNetherBiome.addMobToBiome(EntityType.GHAST, 50, 4, 4);
        moonNetherBiome.addMobToBiome(EntityType.ZOMBIFIED_PIGLIN, 100, 4, 4);
        moonNetherBiome.addMobToBiome(EntityType.PIGLIN, 15, 4, 4);
        moonNetherBiome.addMobToBiome(EntityType.MAGMA_CUBE, 2, 4, 4);
        moonNetherBiome.addMobToBiome(EntityType.STRIDER, 60, 1, 2);

        RegisteredCustomBiome registeredMoonNether = AdvancedWorldCreatorAPI.registerCustomBiome(moonNetherBiome, false);
        NoiseSettings noiseSettings = PresetNoiseSettings.NETHER;

        // Nether wastes
        //
        float temperatureNoise = 0;
        float humidity = 0;
        float altitude = 0;
        float weirdness = 0;
        float offset = 0;

        MultiNoiseBiomeGenerator biomeGenerator = new MultiNoiseBiomeGenerator.CREATOR(advancedCreator.getSeed())
                .addBiome(registeredMoonNether, temperatureNoise, humidity, altitude, weirdness, offset)
                .create();

        // World settings
        //
        final Block solidMaterial = Blocks.END_STONE;
        final Block fluidMaterial = Blocks.LAVA;
        final int bedrockRoofPosition = 128;
        final int bedrockFloorPosition = 0;
        final int seaLevel = 32;
        final boolean disableMobGeneration = false;

        advancedCreator.setGenerateStructures(true);
        advancedCreator.setCustomWorldSetting(noiseSettings, solidMaterial, fluidMaterial,
                bedrockRoofPosition, bedrockFloorPosition, seaLevel, disableMobGeneration);

        AdvancedWorldCreatorAPI.createWorld(advancedCreator, CustomDimensionSettings.getNetherSettings(), biomeGenerator);
    }

    private void createTheEnd() {
        new WorldCreator("moon_the_end")
                .environment(World.Environment.THE_END)
                .createWorld();
    }
}
