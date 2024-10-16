package walksy.crosshairaddons.config;

import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import me.shedaniel.clothconfig2.impl.builders.SubCategoryBuilder;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;
import walksy.crosshairaddons.manager.ConfigManager;

public class ClothConfigIntegration {

    protected static Screen getConfigScreen(Screen parent) {
        // Get the previous screen
        ConfigBuilder builder = ConfigBuilder.create()
            .setParentScreen(MinecraftClient.getInstance().currentScreen)
            .setTitle(Text.literal("Crosshair Addons Config"));

        ConfigCategory generalCategory = builder.getOrCreateCategory(Text.literal("General"));
        ConfigCategory crosshairCategory = builder.getOrCreateCategory(Text.literal("Crosshair"));
        ConfigEntryBuilder entryBuilder = builder.entryBuilder();

        generalCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Crosshair Addons Enabled"), ConfigManager.modEnabled)
            .setDefaultValue(ConfigManager.modEnabled)
            .setTooltip(Text.literal("Should enable crosshair addons"))
            .setSaveConsumer(newValue -> {
                ConfigManager.modEnabled = newValue;
            })
            .build());

        /**
         *  General Settings
         */

        crosshairCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Environment Blend"), ConfigManager.environmentBlend)
            .setDefaultValue(ConfigManager.environmentBlend)
            .setTooltip(Text.literal("Should addon textures blend in with the environment"))
            .setSaveConsumer(newValue -> {
                ConfigManager.environmentBlend = newValue;
            })
            .build());
        crosshairCategory.addEntry(entryBuilder.startBooleanToggle(Text.literal("Show In Third Person"), ConfigManager.showInThirdPerson)
            .setDefaultValue(ConfigManager.showInThirdPerson)
            .setTooltip(Text.literal("Should addon textures show in third person"))
            .setSaveConsumer(newValue -> {
                ConfigManager.showInThirdPerson = newValue;
            })
            .build());
        SubCategoryBuilder crosshairIndicatorSub = entryBuilder.startSubCategory(Text.literal("Crosshair Indicator"));
        crosshairIndicatorSub.add(entryBuilder.startBooleanToggle(Text.literal("Player Indicator"), ConfigManager.playerIndicator)
            .setDefaultValue(ConfigManager.playerIndicator)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.playerIndicator = newValue;
            })
            .build());

        crosshairIndicatorSub.add(entryBuilder.startBooleanToggle(Text.literal("Mob Indicator"), ConfigManager.mobIndicator)
            .setDefaultValue(ConfigManager.mobIndicator)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.mobIndicator = newValue;
            })
            .build());
        crosshairCategory.addEntry(crosshairIndicatorSub.setExpanded(true).build());

        /**
         *  Elytra Indicator
         */

        SubCategoryBuilder elytraIndicatorSub = entryBuilder.startSubCategory(Text.literal("Elytra Indicator"));
        elytraIndicatorSub.add(entryBuilder.startBooleanToggle(Text.literal("Enabled"), ConfigManager.elytraIndicator)
            .setDefaultValue(ConfigManager.elytraIndicator)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.elytraIndicator = newValue;
            })
            .build());

        elytraIndicatorSub.add(entryBuilder.startDoubleField(Text.literal("Size"), ConfigManager.elytraSize)
            .setDefaultValue(ConfigManager.elytraSize)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.elytraSize = newValue;
            })

            .build());
        crosshairCategory.addEntry(elytraIndicatorSub.setExpanded(true).build());


        /**
         * Hitmarker Indicator
         */

        SubCategoryBuilder hitmarkerIndicatorSub = entryBuilder.startSubCategory(Text.literal("HitMarker Indicator"));
        hitmarkerIndicatorSub.add(entryBuilder.startBooleanToggle(Text.literal("Enabled"), ConfigManager.hitMarker)
            .setDefaultValue(ConfigManager.hitMarker)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.hitMarker = newValue;
            })
            .build());
        hitmarkerIndicatorSub.add(entryBuilder.startEnumSelector(Text.literal("HitMarker Type"), ConfigManager.HitMarkerTypes.class, ConfigManager.hitMarkerType)
            .setDefaultValue(ConfigManager.hitMarkerType)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.hitMarkerType = newValue;
            })
            .setEnumNameProvider(val -> Text.of(ConfigManager.getEnumName(val.ordinal())))
            .build());
        hitmarkerIndicatorSub.add(entryBuilder.startIntSlider(Text.literal("Fade / Animation Speed"), ConfigManager.hitMarkerSpeed, 1, 15)
            .setDefaultValue(ConfigManager.hitMarkerSpeed)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.hitMarkerSpeed = newValue;
            })
            .build());
        hitmarkerIndicatorSub.add(entryBuilder.startIntSlider(Text.literal("Duration"), ConfigManager.hitMarkerTime, 1, 50)
            .setDefaultValue(ConfigManager.hitMarkerTime)
            .setTooltip(Text.literal(""))
            .setSaveConsumer(newValue -> {
                ConfigManager.hitMarkerTime = newValue;
            })
            .build());
        crosshairCategory.addEntry(hitmarkerIndicatorSub.setExpanded(true).build());

        builder.setSavingRunnable(ConfigManager::save);
        return builder.build();
    }
}


