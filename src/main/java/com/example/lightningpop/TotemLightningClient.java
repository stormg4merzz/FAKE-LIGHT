package com.example.lightningpop;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.minecraft.text.Text;

public class TotemLightningClient implements ClientModInitializer {
    private static boolean enabled = true;

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(ClientCommandManager.literal("light")
                .executes(context -> {
                    enabled = !enabled;
                    if (enabled) {
                        context.getSource().sendFeedback(Text.literal("\u00a76[STORM] \u00a7aLightning Pop Mod: ON"));
                    } else {
                        context.getSource().sendFeedback(Text.literal("\u00a76[STORM] \u00a7cLightning Pop Mod: OFF"));
                    }
                    return 1;
                })
            );
        });

        System.out.println("Lightning pop by STORM Initialized!");
    }

    public static boolean isEnabled() {
        return enabled;
    }
}
