package com.example.lightningpop.mixin;

import com.example.lightningpop.TotemLightningClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LightningEntity;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPlayNetworkHandler.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "onEntityStatus", at = @At("HEAD"))
    private void onTotemPop(EntityStatusS2CPacket packet, CallbackInfo ci) {
        if (!TotemLightningClient.isEnabled()) {
            return;
        }

        if (packet.getStatus() == 35) {
            MinecraftClient client = MinecraftClient.getInstance();
            if (!client.isOnThread()) {
                return;
            }
            if (client.world != null) {
                Entity entity = packet.getEntity(client.world);
                if (entity != null) {
                    LightningEntity lightning = new LightningEntity(EntityType.LIGHTNING_BOLT, client.world);
                    lightning.refreshPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), 0.0F, 0.0F);
                    lightning.setCosmetic(true);
                    client.world.addEntity(lightning);
                }
            }
        }
    }
}
