package dev.falsehonesty.staciahelper;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("staciahelper")
public class StaciaHelper {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();

    public StaciaHelper() {
        LOGGER.info("Loaded StaciaHelper.");
    }
}
