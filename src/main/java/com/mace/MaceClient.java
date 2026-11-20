package com.mace;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MaceClient implements ClientModInitializer {
    public static boolean espEnabled = false;
    public static boolean maceAura = false;

    @Override
    public void onInitializeClient() {
        MaceKeys.register();
        ClientTickEvents.END_CLIENT_TICK.register(MaceAura::tick);
    }
}
