/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import fr.juanvalero.tnttag.bootstrap.Boostrap;
import org.bukkit.plugin.java.JavaPlugin;

public class TntTagPlugin extends JavaPlugin {

    private static final Stage PLUGIN_STAGE = Stage.DEVELOPMENT;

    @Override
    public void onEnable() {
        super.getSLF4JLogger().info("== TntTag v{} by Choukas ==", super.getDescription().getVersion());

        super.reloadConfig();

        try {
            Injector injector = Guice.createInjector(PLUGIN_STAGE, new TntTagModule(this));
            injector.getInstance(Boostrap.class).bootstrap();

            super.getSLF4JLogger().info("TntTag enabled successfully");
        } catch (Exception e) {
            super.getSLF4JLogger().error(
                    "An error occurred while enabling TntTag. Please refer to the following exception for further details."
            );

            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        super.getSLF4JLogger().info("TntTag disabled successfully !");
    }
}
