package com.pp.augmenta.menu.client;

import com.pp.augmenta.menu.AugmenterMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class AugmenterScreen extends AbstractContainerScreen<AugmenterMenu> {
    private static final ResourceLocation BACKGROUND =
            ResourceLocation.withDefaultNamespace("textures/gui/container/generic_54.png");
    private static final int CONTAINER_ROWS = 3;

    public AugmenterScreen(AugmenterMenu menu, Inventory inventory, Component title) {
        super(menu, inventory, title);
        this.imageWidth = 176;
        this.imageHeight = 114 + CONTAINER_ROWS * 18;
        this.inventoryLabelY = this.imageHeight - 94;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTick, int mouseX, int mouseY) {
        int x = (this.width - this.imageWidth) / 2;
        int y = (this.height - this.imageHeight) / 2;

        int topHeight = CONTAINER_ROWS * 18 + 17;
        guiGraphics.blit(BACKGROUND, x, y, 0, 0, this.imageWidth, topHeight);
        guiGraphics.blit(BACKGROUND, x, y + topHeight, 0, 126, this.imageWidth, 96);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
