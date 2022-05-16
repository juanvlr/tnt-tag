package fr.juanvalero.tnttag.item;

import fr.juanvalero.tnttag.api.game.Game;
import fr.juanvalero.tnttag.api.game.item.Item;
import fr.juanvalero.tnttag.api.utils.item.ItemStackBuilder;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class SwapItem extends Item {

    private final Game game;

    @Inject
    public SwapItem(Plugin plugin, Game game) {
        super(plugin);

        this.game = game;
    }

    @Override
    public int getId() {
        return 7;
    }

    @Override
    protected int getCooldown() {
        return 60;
    }

    @Override
    protected ItemStack getItemStack() {
        return new ItemStackBuilder(Material.ENDER_PEARL)
                .withName(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.text("Swap", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                )
                .withLore(
                        Component
                                .text("[", NamedTextColor.WHITE)
                                .append(Component.keybind("key.use", NamedTextColor.RED))
                                .append(Component.text("]", NamedTextColor.WHITE))
                                .append(Component.text(" pour échanger sa position avec celle d'un joueur"))
                )
                .build();
    }

    @Override
    protected void action(Player player) {
        Player targetPlayer = this.game.getPlayers().getRandom(player);

        Location currentLocation = player.getLocation();
        Location targetLocation = targetPlayer.getLocation();

        targetPlayer.teleport(currentLocation);
        player.teleport(targetLocation);

        if (this.game.isTagged(player)) {
            this.updateLocation(player);
        }

        if (this.game.isTagged(targetPlayer)) {
            this.updateLocation(targetPlayer);
        }
    }

    private void updateLocation(Player player) {
        Location location = player.getLocation();

        this.game.getTaggedPlayers().forEach(taggedPlayer -> {
            Location taggedPlayerLocation = taggedPlayer.getLocation();

            if (taggedPlayerLocation.distance(location) < taggedPlayer.getCompassTarget().distance(location)) {
                // The moving player became the closest player from the current tagged player
                // Refresh his tracker
                taggedPlayer.setCompassTarget(location);
            }
        });
    }
}