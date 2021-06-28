package com.gluton.quickspyglasser.compat.trinket;

import com.gluton.quickspyglasser.QuickSpyglasserClient;
import dev.emi.trinkets.api.Trinket;
import dev.emi.trinkets.api.TrinketComponent;
import dev.emi.trinkets.api.TrinketsApi;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

import java.util.Optional;

public class SpyglassTrinket implements Trinket {
    public static void regsiter() {
        TrinketsApi.registerTrinket(Items.SPYGLASS, new SpyglassTrinket());
    }

    public static ItemStack getEquippedTrinket(PlayerEntity player) {
        Optional<TrinketComponent> trinketComponent = TrinketsApi.getTrinketComponent(player);
        if (trinketComponent.isPresent()) {
            var trinkets = trinketComponent.get().getEquipped(QuickSpyglasserClient.quickSpyglassItem);
            // get the first trinket that matches the quickSpyglassItem
            for (var trinket : trinkets) {
                return trinket.getRight();
            }
        }
        return null;
    }
}
