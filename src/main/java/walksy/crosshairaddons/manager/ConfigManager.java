package walksy.crosshairaddons.manager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import net.fabricmc.loader.api.FabricLoader;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigManager {

    public static boolean modEnabled = true;
    public static boolean playerIndicator = true;
    public static boolean mobIndicator = true;
    public static boolean shieldIndicator = true;
    public static boolean shieldSelfIndicator = false;
    public static boolean hitMarker = true;
    public static int hitMarkerTime = 15;
    public static boolean elytraIndicator = true;
    public static double elytraSize = 1;
    public static boolean environmentBlend = true;
    public static boolean showInThirdPerson = true;
    public static HitMarkerTypes hitMarkerType = HitMarkerTypes.DEFAULT;
    public static int hitMarkerSpeed = 10;


    private static final Path configDir = FabricLoader.getInstance().getConfigDir();
    private static final File configFile = configDir.resolve("crosshairaddons.json").toFile();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    public static void save() {
        ConfigData configData = new ConfigData(modEnabled, playerIndicator, mobIndicator, shieldIndicator, shieldSelfIndicator, hitMarker, hitMarkerTime, elytraIndicator, elytraSize, environmentBlend, showInThirdPerson, hitMarkerType, hitMarkerSpeed);
        try (FileWriter writer = new FileWriter(configFile)) {
            GSON.toJson(configData, writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void load() {
        if (configFile.exists()) {
            try (FileReader reader = new FileReader(configFile)) {
                ConfigData configData = GSON.fromJson(reader, ConfigData.class);
                modEnabled = configData.modEnabled;
                playerIndicator = configData.playerIndicator;
                mobIndicator = configData.mobIndicator;
                shieldIndicator = configData.shieldIndicator != null ? configData.shieldIndicator : false;
                shieldSelfIndicator = configData.shieldSelfIndicator != null ? configData.shieldSelfIndicator : false;
                hitMarker = configData.hitMarker;
                hitMarkerTime = configData.hitMarkerTime;
                elytraIndicator = configData.elytraIndicator;
                elytraSize = configData.elytraSize;
                environmentBlend = configData.environmentBlend;
                showInThirdPerson = configData.showInThirdPerson;
                hitMarkerType = configData.hitMarkerType;
                hitMarkerSpeed = configData.hitMarkerSpeed;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static class ConfigData {
        boolean modEnabled;
        boolean playerIndicator;
        boolean mobIndicator;
        Boolean shieldIndicator;
        Boolean shieldSelfIndicator;
        boolean hitMarker;
        int hitMarkerTime;
        boolean elytraIndicator;
        double elytraSize;
        boolean environmentBlend;
        boolean showInThirdPerson;
        HitMarkerTypes hitMarkerType;
        int hitMarkerSpeed;

        ConfigData(boolean modEnabled, boolean playerIndicator, boolean mobIndicator, boolean shieldIndicator, boolean shieldSelfIndicator, boolean hitMarker, int hitMarkerTime, boolean elytraIndicator, double elytraSize, boolean environmentBlend, boolean showInThirdPerson, HitMarkerTypes hitMarkerType, int hitMarkerSpeed) {
            this.modEnabled = modEnabled;
            this.playerIndicator = playerIndicator;
            this.mobIndicator = mobIndicator;
            this.shieldIndicator = shieldIndicator;
            this.shieldSelfIndicator = shieldSelfIndicator;
            this.hitMarker = hitMarker;
            this.hitMarkerTime = hitMarkerTime;
            this.elytraIndicator = elytraIndicator;
            this.elytraSize = elytraSize;
            this.environmentBlend = environmentBlend;
            this.showInThirdPerson = showInThirdPerson;
            this.hitMarkerType = hitMarkerType;
            this.hitMarkerSpeed = hitMarkerSpeed;
        }
    }

    public static String getEnumName(int ordinal)
    {
        return HitMarkerTypes.values()[ordinal].enumName;
    }

    public enum HitMarkerTypes {
        FADE("Fade"),
        ANIMATION("Animation"),
        DEFAULT("Default");

        public final String enumName;
        HitMarkerTypes(String name)
        {
            this.enumName = name;
        }
    }
}