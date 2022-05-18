/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.player;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class PlayerCollectionImpl implements PlayerCollection {

    private final Map<UUID, Player> players;

    public PlayerCollectionImpl() {
        this.players = new HashMap<>();
    }

    public PlayerCollectionImpl(List<Player> players) {
        this();

        players.forEach(this::add);
    }

    @Override
    public void add(Player player) {
        this.players.put(player.getUniqueId(), player);
    }

    @Override
    public void remove(Player player) {
        this.players.remove(player.getUniqueId());
    }

    @Override
    public boolean contains(Player player) {
        return this.players.containsKey(player.getUniqueId());
    }

    @Override
    public boolean isEmpty() {
        return this.players.isEmpty();
    }

    @Override
    public int count() {
        return this.players.size();
    }

    @Override
    public Player getFirst() {
        return this.players.values().stream()
                .findFirst()
                .orElseThrow();
    }

    @Override
    public Player getRandom(Player excludedPlayer) {
        List<UUID> includedPlayerIds = new ArrayList<>(this.players.keySet());
        includedPlayerIds.remove(excludedPlayer.getUniqueId());

        return this.players.get(includedPlayerIds.get(new Random().nextInt(this.count() - 1)));
    }

    @Override
    public PlayerCollection getRandoms(int amount) {
        List<Player> players = new ArrayList<>(this.players.values());
        Collections.shuffle(players);

        return new PlayerCollectionImpl(players.subList(0, amount));
    }

    @Override
    public void forEach(Consumer<? super Player> action) {
        this.players.values().forEach(action);
    }

    @Override
    public Player getClosest(Player player) {
        Location location = player.getLocation();

        return this.players.values().stream()
                .sorted((player1, player2) -> {
                    double p1Distance = player1.getLocation().distance(location);
                    double p2Distance = player2.getLocation().distance(location);

                    return Double.compare(p1Distance, p2Distance);
                })
                .toList()
                .get(0);
    }

    @Override
    public PlayerCollection filter(Predicate<? super Player> filter) {
        return new PlayerCollectionImpl(this.players.values().stream().filter(filter).collect(Collectors.toList()));
    }
}
