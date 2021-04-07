package dev.barfuzzle99.taplmoon.taplmoon;

import de.freesoccerhdx.advancedworldcreator.biomegenerators.MultiNoiseBiomeGenerator;
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
import net.minecraft.server.v1_16_R3.Block;
import net.minecraft.server.v1_16_R3.Blocks;
import net.minecraft.server.v1_16_R3.NoiseSettings;
import net.minecraft.server.v1_16_R3.WorldGenStage;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameRule;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.awt.*;


public class MoonWorldCreator {

    CommandSender whoCreated;

    public MoonWorldCreator(CommandSender whoCreated) {
        this.whoCreated = whoCreated;
    }

    public static void forceLoadWorlds() {
        createOverworld();
        createNether();
        createTheEnd();
    }

    public void createWorlds() {
        new BukkitRunnable() {
            @Override
            public void run() {
                whoCreated.sendMessage("[TapLMoon] Creating world ''moon'' (1/3)");
                createOverworld();
            }
        }.runTaskLater(TaplMoon.getInstance(), 0);

        new BukkitRunnable() {
            @Override
            public void run() {
                whoCreated.sendMessage("[TapLMoon] Creating world ''moon_nether'' (2/3)");
                createNether();
            }
        }.runTaskLater(TaplMoon.getInstance(), 1);

        new BukkitRunnable() {
            @Override
            public void run() {
                whoCreated.sendMessage("[TapLMoon] Creating world ''moon_the_end'' (3/3)");
                createTheEnd();
                // Kick players to force their clients to know about the newly created biomes
                // on re-log. Workaround for https://bugs.mojang.com/browse/MC-197616
                for (Player player : Bukkit.getOnlinePlayers()) {
                    player.kickPlayer(ChatColor.GREEN + "[TapLMoon] ✔ World creation done! You can re-log now");
                }
            }
        }.runTaskLater(TaplMoon.getInstance(), 2);
    }

    private static final Color GRASS_COLOR = new Color(250,250,140);
    private static final Color FOLIAGE_COLOR = new Color(60,100,170);
    private static final Color WATER_COLOR = new Color(220,220,220);

    private static void createOverworld() {
        // World settings
        //
        AdvancedCreator advancedCreator = new AdvancedCreator("moon");
        advancedCreator.setSeed(Bukkit.getWorlds().get(0).getSeed());
        NoiseSettings noiseSettings = PresetNoiseSettings.OVERWORLD;

        advancedCreator.setGenerateStructures(true);
        MultiNoiseBiomeGenerator.CREATOR multiNoiseBiomeCreator = new MultiNoiseBiomeGenerator.CREATOR(advancedCreator.getSeed());

        // Moon plains
        //
        CustomBiome moonPlains = new CustomBiome("tapl_moon", "moon_plains");
        applyMoonBiomeAppearance(moonPlains);
        addMoonBiomeStructures(moonPlains);
        addMoonBiomeOres(moonPlains);
        addMoonOverworldMobs(moonPlains);
        moonPlains.setDepth(0.2f);
        moonPlains.setScale(0.025f);

        RegisteredCustomBiome registeredMoonBiome = AdvancedWorldCreatorAPI.registerCustomBiome(moonPlains, false);

        // Moon Hills
        //
        CustomBiome moonHills = new CustomBiome("tapl_moon", "moon_hills");
        applyMoonBiomeAppearance(moonHills);
        addMoonBiomeStructures(moonHills);
        addMoonBiomeOres(moonHills);
        addMoonOverworldMobs(moonHills);
        moonHills.setDepth(0.2f);
        moonHills.setScale(0.4f);

        RegisteredCustomBiome registeredMoonHills = AdvancedWorldCreatorAPI.registerCustomBiome(moonHills, false);

        // Moon crater
        //
        CustomBiome moonCrater = new CustomBiome("tapl_moon", "moon_crater");
        for (int i = 0; i < 10; i ++) {
            moonCrater.addWorldGenFeature(WorldGenStage.Features.AIR, WorldGenCarver.NETHER_CAVE);
        }
        moonCrater.setCustomSurfaceBuilder(SurfaceType.DEFAULT, Blocks.END_STONE, Blocks.STONE, Blocks.STONE);
        moonCrater.setDepth(-1.0f);
        moonCrater.setScale(0.01f);

        RegisteredCustomBiome registeredMoonCrater = AdvancedWorldCreatorAPI.registerCustomBiome(moonCrater, false);

        // World creation
        //
        final Block solidMaterial = Blocks.STONE;
        final Block fluidMaterial = Blocks.VOID_AIR;
        final int bedrockRoofPosition = -10;
        final int bedrockFloorPosition = 0;
        final int seaLevel = 1;
        final boolean disableMobGeneration = false;

        multiNoiseBiomeCreator.addBiome(registeredMoonCrater, 0.0f, 0.0f, 0.1f, 1.0f, 0.5f);
        multiNoiseBiomeCreator.addBiome(registeredMoonBiome, 0.0f, 0.0f, 0.2f, 1.0f, 0.0f);
        multiNoiseBiomeCreator.addBiome(registeredMoonHills, 0.0f, 0.0f, 0.8f, 0.0f, 0.7f);

        advancedCreator.loadDefaultStructureGeneratorConfig();
        advancedCreator.setCustomWorldSetting(noiseSettings, solidMaterial, fluidMaterial,
                bedrockRoofPosition, bedrockFloorPosition, seaLevel, disableMobGeneration);

        AdvancedWorldCreatorAPI.createWorld(advancedCreator, CustomDimensionSettings.getOverworldSettings(), multiNoiseBiomeCreator.create());
        // Not using CustomDimensionSettings to do that because it prevents the
        // creation of nether portals
        Bukkit.getWorld("moon").setGameRule(GameRule.DO_DAYLIGHT_CYCLE, false);
        Bukkit.getWorld("moon").setTime(14500);
    }

    private static void applyMoonBiomeAppearance(CustomBiome moonBiome) {
        moonBiome.setGrassColor(GRASS_COLOR);
        moonBiome.setFoliageColor(FOLIAGE_COLOR);
        moonBiome.setWaterColor(WATER_COLOR);
        moonBiome.setPrecipitation(BiomeBase.Precipitation.NONE);
        moonBiome.setCustomSurfaceBuilder(SurfaceType.DEFAULT, Blocks.END_STONE, Blocks.STONE, Blocks.STONE);
        moonBiome.setTemperature(0.5f);
    }

    private static void addMoonBiomeStructures(CustomBiome moonBiome) {
        moonBiome.addBiomeStructure(BiomeStructure.VILLAGE_PLAINS);
        moonBiome.addBiomeStructure(BiomeStructure.STRONGHOLD);
        moonBiome.addBiomeStructure(BiomeStructure.MINESHAFT);
        moonBiome.addBiomeStructure(BiomeStructure.RUINED_PORTAL);
        moonBiome.addWorldGenFeature(WorldGenStage.Features.AIR, WorldGenCarver.CAVE);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.LAKES, BiomeDecoratorGroups.LAKE_LAVA);
    }

    private static void addMoonBiomeOres(CustomBiome moonBiome) {
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_COAL);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_GOLD);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_EMERALD);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_DIAMOND);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_INFESTED);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_IRON);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_REDSTONE);
        moonBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_LAPIS);
    }

    private static final void addMoonOverworldMobs(CustomBiome moonBiome) {
        // Minecraft defaults for monsters
        moonBiome.addMobToBiome(EntityType.SPIDER, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.ZOMBIE, 95 ,4, 4);
        moonBiome.addMobToBiome(EntityType.ZOMBIE_VILLAGER, 5 ,4, 4);
        moonBiome.addMobToBiome(EntityType.SKELETON, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.CREEPER, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.SLIME, 100 ,4, 4);
        moonBiome.addMobToBiome(EntityType.ENDERMAN, 10 ,4, 4);
        moonBiome.addMobToBiome(EntityType.WITCH, 5 ,4, 4);
    }

    private static final void createNether() {
        AdvancedCreator advancedCreator = new AdvancedCreator("moon_nether");
        CustomBiome moonNetherBiome = new CustomBiome("tapl_moon", "moon_nether_wastes");
        advancedCreator.setGenerateStructures(true);

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
        moonNetherBiome.addWorldGenDecorationFeature(WorldGenStage.Decoration.UNDERGROUND_ORES, BiomeDecoratorGroups.ORE_QUARTZ_NETHER);
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

        advancedCreator.setCustomWorldSetting(noiseSettings, solidMaterial, fluidMaterial,
                bedrockRoofPosition, bedrockFloorPosition, seaLevel, disableMobGeneration);

        AdvancedWorldCreatorAPI.createWorld(advancedCreator, CustomDimensionSettings.getNetherSettings(), biomeGenerator);
    }

    private static final void createTheEnd() {
        new WorldCreator("moon_the_end")
                .environment(World.Environment.THE_END)
                .createWorld();
        Location spawnLoc = Bukkit.getWorld("moon_the_end").getSpawnLocation().clone();
        spawnLoc.setX(spawnLoc.getBlockX() + 100);
        spawnLoc.setY(spawnLoc.getBlockY() - 16);
        Bukkit.getWorld("moon_the_end").setSpawnLocation(spawnLoc);
    }
}
