package com.mace;

import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Items;
import net.minecraft.util.Hand;
import net.minecraft.util.math.Vec3d;

public class MaceAura {
    private static int cooldown = 0;

    public static void tick(MinecraftClient mc) {
        if (!MaceClient.maceAura || mc.player == null || mc.world == null) return;
        if (cooldown-- > 0) return;
        if (!mc.player.getMainHandStack().isOf(Items.MACE)) return;

        Entity target = findTarget(mc);
        if (target == null) return;
        if (mc.player.distanceTo(target) > 4.5) return;

        Vec3d eye = mc.player.getEyePos();
        Vec3d tgt = target.getPos().add(0, target.getHeight() * 0.5, 0);
        Vec3d diff = tgt.subtract(eye);
        float yaw = (float)(Math.toDegrees(Math.atan2(-diff.x, diff.z)));
        float pitch = (float)(Math.toDegrees(-Math.atan2(diff.y, Math.hypot(diff.x, diff.z))));
        
        mc.player.setYaw(yaw);
        mc.player.setPitch(pitch);
        mc.interactionManager.attackEntity(mc.player, target);
        mc.player.swingHand(Hand.MAIN_HAND);
        cooldown = 8;
    }

    private static Entity findTarget(MinecraftClient mc) {
        Entity best = null;
        double bestDist = Double.MAX_VALUE;
        for (Entity e : mc.world.getEntities()) {
            if (!(e instanceof PlayerEntity p) || p == mc.player || p.isDead()) continue;
            if (!MaceConfig.targetPlayersCombat && p.isPlayer()) continue; // Config check
            double d = mc.player.distanceTo(p);
            if (d < bestDist) { bestDist = d; best = p; }
        }
        return best;
    }
}
