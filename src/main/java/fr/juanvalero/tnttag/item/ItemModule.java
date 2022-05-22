/*
 * Copyright (c) 2022 - Juan Valero
 */

package fr.juanvalero.tnttag.item;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;
import fr.juanvalero.tnttag.api.game.item.Item;

public class ItemModule extends AbstractModule {

    @Override
    protected void configure() {
        Multibinder<Item> itemBinder = Multibinder.newSetBinder(binder(), Item.class);

        itemBinder.addBinding().to(DashItem.class);
        itemBinder.addBinding().to(FlyItem.class);
        itemBinder.addBinding().to(IcyAreaItem.class);
        itemBinder.addBinding().to(InvisibilityItem.class);
        itemBinder.addBinding().to(MobilityItem.class);
        itemBinder.addBinding().to(RepulsionItem.class);
        itemBinder.addBinding().to(SwapItem.class);
    }
}
