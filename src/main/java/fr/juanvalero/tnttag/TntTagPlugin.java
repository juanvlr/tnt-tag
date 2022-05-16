package fr.juanvalero.tnttag;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Stage;
import fr.juanvalero.tnttag.api.APIModule;
import fr.juanvalero.tnttag.api.bootstrap.Boostrap;
import fr.juanvalero.tnttag.core.CoreModule;
import org.bukkit.plugin.java.JavaPlugin;

public class TntTagPlugin extends JavaPlugin {

    private static final Stage PLUGIN_STAGE = Stage.DEVELOPMENT;

    @Override
    public void onEnable() {
        super.getSLF4JLogger().info("== Enabling TntTag v{} by Choukas ==", super.getDescription().getVersion());

        try {
            Injector injector = Guice.createInjector(PLUGIN_STAGE,
                    new APIModule(this, this.getSLF4JLogger(), this.getConfig()),
                    new CoreModule());
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
