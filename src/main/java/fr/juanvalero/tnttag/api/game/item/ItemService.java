package fr.juanvalero.tnttag.api.game.item;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Optional;

/**
 * Item utilities.
 */
public interface ItemService {

    /**
     * Returns the {@link Item} associated to an {@link ItemStack}.
     *
     * @param itemStack The item stack
     * @return An {@link Optional} containing the {@link Item} associated to the specified {@link ItemStack} if found,
     * an empty {@link Optional} otherwise.
     */
    Optional<Item> getItem(ItemStack itemStack);

    /**
     * Returns the {@link ProjectileItem} associated to an {@link ItemStack}.
     *
     * @param projectile The projectile
     * @return An {@link Optional} containing the {@link ProjectileItem} associated to the specified {@link ItemStack} if found,
     * an empty {@link Optional} otherwise.
     */
    Optional<ProjectileItem> getProjectile(Projectile projectile);

    /**
     * Launches a projectile.
     *
     * @param itemStack  The item stack associated to the launched projectile
     * @param projectile The launched projectile
     */
    void launchProjectile(ItemStack itemStack, Projectile projectile);

    /**
     * Returns a list of random items.
     *
     * @param amount The number of items
     * @return A list of {@code amount} random items
     */
    List<ItemStack> getRandomItems(int amount);
}
