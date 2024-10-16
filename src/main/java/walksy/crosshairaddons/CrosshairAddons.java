package walksy.crosshairaddons;

import net.fabricmc.api.ModInitializer;
import walksy.crosshairaddons.manager.ConfigManager;

public class CrosshairAddons implements ModInitializer {

    @Override
    public void onInitialize()
    {
        ConfigManager.load();
    }
}
