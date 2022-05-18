/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.api.game.item;

import de.tr7zw.changeme.nbtapi.NBTItem;
import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static fr.juanvalero.tnttag.api.game.item.Item.ID_FIELD;

public class ItemServiceImpl implements ItemService {

    private final Plugin plugin;
    private final Map<Integer, Item> items;

    @Inject
    public ItemServiceImpl(Plugin plugin, Set<Item> items) {
        this.plugin = plugin;

        this.items = items.stream().collect(Collectors.toMap(Item::getId, Function.identity()));
    }

    @Override
    public Optional<Item> getItem(ItemStack itemStack) {
        NBTItem itemNbt = new NBTItem(itemStack);

        if (!itemNbt.hasKey(ID_FIELD)) {
            return Optional.empty();
        }

        int id = itemNbt.getInteger(ID_FIELD);

        return Optional.ofNullable(this.items.get(id));
    }

    @Override
    public Optional<ProjectileItem> getProjectile(Projectile projectile) {
        if (!projectile.hasMetadata(ID_FIELD)) {
            return Optional.empty();
        }

        MetadataValue metadata = projectile.getMetadata(ID_FIELD).get(0);
        Object value = metadata.value();

        if (value == null) {
            return Optional.empty();
        }

        if (!(this.items.get((int) value) instanceof ProjectileItem projectileItem)) {
            return Optional.empty();
        }

        return Optional.of(projectileItem);
    }

    @Override
    public void launchProjectile(ItemStack itemStack, Projectile projectile) {
        NBTItem itemNbt = new NBTItem(itemStack);

        if (!itemNbt.hasKey(ID_FIELD)) {
            return;
        }

        int id = itemNbt.getInteger(ID_FIELD);

        projectile.setMetadata(ID_FIELD, new FixedMetadataValue(this.plugin, id));
    }

    @Override
    public List<ItemStack> getRandomItems(int amount) {
        List<Item> randomItems = new ArrayList<>(this.items.values());
        Collections.shuffle(randomItems);

        return randomItems.stream()
                .map(Item::asItemStack)
                .collect(Collectors.toList())
                .subList(0, amount);
    }
}
