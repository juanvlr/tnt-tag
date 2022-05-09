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
import java.util.stream.IntStream;

public class ItemServiceImpl implements ItemService {

    private final Plugin plugin;
    private final Map<Integer, Item> items;

    @Inject
    public ItemServiceImpl(Plugin plugin, Set<Item> items) {
        this.plugin = plugin;
        this.items = items.stream()
                .collect(Collectors.toMap(Item::getId, Function.identity()));
    }

    @Override
    public Optional<Item> getItem(ItemStack itemStack) {
        NBTItem itemNbt = new NBTItem(itemStack);

        if (!itemNbt.hasKey("id")) {
            return Optional.empty();
        }

        int id = itemNbt.getInteger("id");

        return Optional.ofNullable(this.items.get(id));
    }

    @Override
    public Optional<ProjectileItem> getProjectile(Projectile projectile) {
        if (!projectile.hasMetadata("id")) {
            return Optional.empty();
        }

        MetadataValue metadata = projectile.getMetadata("id").get(0);
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

        if (!itemNbt.hasKey("id")) {
            return;
        }

        int id = itemNbt.getInteger("id");

        projectile.setMetadata("id", new FixedMetadataValue(this.plugin, id));
    }

    @Override
    public Map<Integer, ItemStack> getRandomItems(int amount) {
        List<Integer> itemIds = new ArrayList<>(this.items.keySet());
        Collections.shuffle(itemIds);

        return IntStream.range(0, amount)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), slot -> {
                    int id = itemIds.get(slot);

                    return this.items.get(id).asItemStack();
                }));
    }
}
