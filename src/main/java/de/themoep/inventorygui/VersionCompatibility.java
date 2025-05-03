package de.themoep.inventorygui;

import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;

import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.MethodType;

public class VersionCompatibility {
    private static final MethodHandle GET_TOP_INVENTORY;
    private static final MethodHandle GET_BOTTOM_INVENTORY;
    private static final MethodHandle GET_TYPE;

    static {
        try {
            MethodHandles.Lookup lookup = MethodHandles.lookup();
            GET_TOP_INVENTORY = lookup.findVirtual(InventoryView.class,
                                                   "getTopInventory",
                                                   MethodType.methodType(Inventory.class));
            GET_BOTTOM_INVENTORY = lookup.findVirtual(InventoryView.class,
                                                      "getBottomInventory",
                                                      MethodType.methodType(Inventory.class));
            GET_TYPE = lookup.findVirtual(InventoryView.class, "getType", MethodType.methodType(InventoryType.class));
        } catch (NoSuchMethodException | IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static InventoryType getType(Object iv) {
        try {
            return (InventoryType) GET_TYPE.invoke(iv);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }


    static Inventory getBottomInventory(Object iv) {
        try {
            return (Inventory) GET_BOTTOM_INVENTORY.invoke(iv);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static Inventory getTopInventory(Object iv) {
        try {
            return (Inventory) GET_TOP_INVENTORY.invoke(iv);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
