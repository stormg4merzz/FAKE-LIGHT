package com.example.lightningpop.mixin;

import com.example.lightningpop.TotemLightningClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.protocol.game.ClientboundEntityEventPacket;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LightningBolt;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ClientPacketListener.class)
public class ClientPlayNetworkHandlerMixin {

    @Inject(method = "handleEntityEvent", at = @At("HEAD"))
    private void onTotemPop(ClientboundEntityEventPacket packet, CallbackInfo ci) {
        if (!TotemLightningClient.isEnabled()) {
            return;
        }

        if (packet.getEventId() == 35) {
            Minecraft client = Minecraft.getInstance();
            if (!client.isSameThread()) {
                return;
            }
            if (client.level != null) {
                Entity entity = packet.getEntity(client.level);
                if (entity != null) {
                    LightningBolt lightning = new LightningBolt(EntityType.LIGHTNING_BOLT, client.level);
                    lightning.setPos(entity.getX(), entity.getY(), entity.getZ());
                    lightning.setVisualOnly(true);
                    client.level.addEntity(lightning);
                }
            }
        }
    }
}
