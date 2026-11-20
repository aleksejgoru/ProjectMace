package com.mace;

import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public final class MaceKeys {
    public static KeyBinding toggleEsp, toggleAura, panic, openMenu;

    public static void register() {
        String cat = "Project Mace";
        toggleEsp = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mace.esp", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_SHIFT, cat));
        toggleAura = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mace.aura", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_RIGHT_CONTROL, cat));
        openMenu = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mace.menu", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_INSERT, cat));
        panic = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.mace.panic", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_END, cat));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (toggleEsp.wasPressed()) { 
                MaceClient.espEnabled = !MaceClient.espEnabled; 
                client.player.sendMessage(Text.literal("§6[mace] ESP: " + (MaceClient.espEnabled ? "ON" : "OFF")), true);
            }
            while (toggleAura.wasPressed()) { 
                MaceClient.maceAura = !MaceClient.maceAura; 
                client.player.sendMessage(Text.literal("§6[mace] Aura: " + (MaceClient.maceAura ? "ON" : "OFF")), true);
            }
            while (openMenu.wasPressed()) { client.setScreen(new MaceMenuScreen()); }
            while (panic.wasPressed()) { 
                MaceClient.espEnabled = false; 
                MaceClient.maceAura = false; 
                client.player.sendMessage(Text.literal("§c[mace] PANIC ALL OFF"), true); 
            }
        });
    }
}
