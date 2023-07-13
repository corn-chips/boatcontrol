package org.cornchips.boatcontrol;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class BoatControlClient implements ClientModInitializer {

    private static KeyBinding toggleKey;
    private static MinecraftClient mc;
    private static boolean toggle;

    @Override
    public void onInitializeClient() {
        mc = net.minecraft.client.MinecraftClient.getInstance();

        toggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
           "key.boatconrtol.toggle",
           InputUtil.Type.KEYSYM,
           GLFW.GLFW_KEY_C,
           "category.boatcontrol.test"
        ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (toggleKey.wasPressed()) {
                toggle = !toggle;
                if(toggle) {
                    client.player.sendMessage(Text.literal("Boat control on"));
                } else {
                    client.player.sendMessage(Text.literal("Boat control off"));
                }

            }
        });
        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (toggle){
                if(mc.player.getRootVehicle().getType() == EntityType.BOAT) {
                    mc.player.getRootVehicle().setYaw(mc.player.getYaw());
                }
            }
        });
    }
}
