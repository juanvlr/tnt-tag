package fr.juanvalero.tnttag.api.game.item;

import org.bukkit.entity.Projectile;
import org.bukkit.inventory.ItemStack;

import java.util.Map;
import java.util.Optional;

public interface ItemService {

    Optional<Item> getItem(ItemStack itemStack);

    Optional<ProjectileItem> getProjectile(Projectile projectile);

    void launchProjectile(ItemStack itemStack, Projectile projectile);

    Map<Integer, ItemStack> getRandomItems(int amount);
}
