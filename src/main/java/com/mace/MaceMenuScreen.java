package com.mace;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.text.Text;
import net.minecraft.client.gui.widget.ButtonWidget;

public class MaceMenuScreen extends Screen {
    private int currentTab = 0;
    private final String[] tabs = { "Combat", "Targets", "Stun Slam", "Pearl", "Aim", "Wind", "Misc" };

    public MaceMenuScreen() { super(Text.literal("Project Mace Config")); }

    @Override
    protected void init() {
        this.clearChildren();
        int cx = this.width / 2;
        int cy = this.height / 2;
        
        int tabX = cx - 240;
        for (int i = 0; i < tabs.length; i++) {
            final int idx = i;
            this.addDrawableChild(ButtonWidget.builder(Text.literal(tabs[i]), b -> this.currentTab = idx).dimensions(tabX + (i * 68), cy - 120, 64, 20).build());
        }

        this.addDrawableChild(ButtonWidget.builder(Text.literal("Отмена"), b -> this.close()).dimensions(cx - 200, cy + 100, 190, 20).build());
        this.addDrawableChild(ButtonWidget.builder(Text.literal("Сохранить и выйти"), b -> this.close()).dimensions(cx + 10, cy + 100, 190, 20).build());
    }

    @Override
    public void render(DrawContext ctx, int mouseX, int mouseY, float delta) {
        ctx.fill(0, 0, this.width, this.height, 0xDD0A0A0A);
        int cx = this.width / 2;
        int cy = this.height / 2;

        ctx.fill(cx - 250, cy - 145, cx + 250, cy - 125, 0xFF1A1A1A);
        ctx.fill(cx - 250, cy - 125, cx + 250, cy - 123, 0xFFC8A028);
        ctx.drawCenteredTextWithShadow(this.textRenderer, "§6Project Mace §8- §7Config", cx, cy - 138, 0xFFFFFF);

        ctx.fill(cx - 250, cy - 95, cx + 250, cy + 90, 0xEE111111);
        ctx.drawBorder(cx - 250, cy - 95, 500, 185, 0xFF333333);

        int activeX = cx - 240 + (currentTab * 68);
        ctx.fill(activeX, cy - 98, activeX + 64, cy - 96, 0xFFFF5522);

        switch (currentTab) {
            case 0 -> renderCombat(ctx, cx, cy);
            case 1 -> renderTargets(ctx, cx, cy);
            case 2 -> renderStun(ctx, cx, cy);
            case 3 -> renderPearl(ctx, cx, cy);
            case 4 -> renderAim(ctx, cx, cy);
            case 5 -> renderWind(ctx, cx, cy);
            case 6 -> renderMisc(ctx, cx, cy);
        }
        super.render(ctx, mouseX, mouseY, delta);
    }

    private void drawOption(DrawContext ctx, int x, int y, String label, boolean val) {
        ctx.drawTextWithShadow(this.textRenderer, label, x, y + 5, 0xAAAAAA);
        int bx = x + 200;
        int col = val ? 0xFF55FF55 : 0xFFFF5555;
        String txt = val ? "Да" : "Нет";
        ctx.fill(bx, y, bx + 100, y + 18, 0xFF2A2A2A);
        ctx.drawBorder(bx, y, 100, 18, 0xFF555555);
        ctx.drawCenteredTextWithShadow(this.textRenderer, txt, bx + 50, y + 5, col);
    }

    private void drawSlider(DrawContext ctx, int x, int y, String label, int val) {
        ctx.drawTextWithShadow(this.textRenderer, label, x, y + 5, 0xAAAAAA);
        int bx = x + 200;
        ctx.fill(bx, y, bx + 100, y + 18, 0xFF1A1A1A);
        ctx.drawBorder(bx, y, 100, 18, 0xFF555555);
        ctx.drawCenteredTextWithShadow(this.textRenderer, "Value: §e" + val, bx + 50, y + 5, 0xFFFFFF);
    }

    private void renderCombat(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Auto Attribute Swap", MaceConfig.autoAttrSwap); y+=25;
        drawOption(ctx, x, y, "Smart Switch", MaceConfig.smartSwitch); y+=25;
        drawSlider(ctx, x, y, "Swap Delay (Ticks)", MaceConfig.swapDelay); y+=25;
        drawOption(ctx, x, y, "Lunge Swap", MaceConfig.lungeSwap); y+=25;
        drawSlider(ctx, x, y, "Lunge Swap Delay", MaceConfig.lungeDelay);
    }

    private void renderTargets(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Target Mobs (Combat)", MaceConfig.targetMobsCombat); y+=25;
        drawOption(ctx, x, y, "Target Players (Combat)", MaceConfig.targetPlayersCombat); y+=25;
        drawOption(ctx, x, y, "Target Mobs (Aim)", MaceConfig.targetMobsAim); y+=25;
        drawOption(ctx, x, y, "Target Players (Aim)", MaceConfig.targetPlayersAim);
    }

    private void renderStun(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Auto Stun Slam", MaceConfig.autoStunSlam); y+=25;
        drawOption(ctx, x, y, "Return Slot", MaceConfig.returnSlot); y+=25;
        drawSlider(ctx, x, y, "No-Mace Axe Hit Delay", MaceConfig.noMaceDelay);
    }

    private void renderPearl(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Pearl Catch", MaceConfig.pearlCatch); y+=25;
        drawSlider(ctx, x, y, "Catch Delay (Ticks)", MaceConfig.catchDelay); y+=25;
        drawSlider(ctx, x, y, "Catch Angle", MaceConfig.catchAngle); y+=25;
        drawOption(ctx, x, y, "Return Angle", MaceConfig.returnAngle); y+=25;
        drawSlider(ctx, x, y, "Return Angle Value", MaceConfig.returnAngleVal);
    }

    private void renderAim(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Aim Assist", MaceConfig.aimAssist); y+=25;
        drawSlider(ctx, x, y, "Aim Speed", MaceConfig.aimSpeed); y+=25;
        drawSlider(ctx, x, y, "Aim Distance", MaceConfig.aimDist); y+=25;
        drawOption(ctx, x, y, "Trigger Bot", MaceConfig.triggerBot);
    }

    private void renderWind(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Right-Click Wind Charge", MaceConfig.rightClickWind); y+=25;
        drawOption(ctx, x, y, "Rocket Boost", MaceConfig.rocketBoost); y+=25;
        drawSlider(ctx, x, y, "Boost Redirect Angle", MaceConfig.boostAngle);
    }

    private void renderMisc(DrawContext ctx, int cx, int cy) {
        int x = cx - 220; int y = cy - 75;
        drawOption(ctx, x, y, "Disable Hurt Cam", MaceConfig.disableHurtCam);
    }

    @Override
    public boolean shouldPause() { return false; }
}
