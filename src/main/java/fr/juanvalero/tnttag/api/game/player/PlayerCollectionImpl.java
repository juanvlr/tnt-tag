package fr.juanvalero.tnttag.api.game.player;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Consumer;

public class PlayerCollectionImpl implements PlayerCollection {

    private final Map<UUID, Player> players;

    public PlayerCollectionImpl() {
        this.players = new HashMap<>();
    }

    @Override
    public int count() {
        return this.players.size();
    }

    @Override
    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    @Override
    public void add(Player player) {
        UUID playerId = player.getUniqueId();

        if (this.players.containsKey(playerId)) {
            throw new IllegalStateException(String.format("A player with the id %s is already present", playerId));
        }

        this.players.put(playerId, player);
    }

    @Override
    public void remove(Player player) {
        UUID playerId = player.getUniqueId();

        if (!this.players.containsKey(playerId)) {
            throw new IllegalStateException(String.format("No player with the id %s is present", playerId));
        }

        this.players.remove(playerId);
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        this.players.values().forEach(action);
    }
}
