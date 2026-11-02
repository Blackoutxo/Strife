package me.blackout.assist.gui;

import me.blackout.assist.utils.render.Renderer2D;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;

import java.awt.*;

public class GUI extends Screen {



    public GUI() {
        super(Component.literal("PvPAssist"));
    }

    @Override
    public void extractRenderState(@NotNull GuiGraphicsExtractor graphics, int mouseX, int mouseY, float delta) {
        Renderer2D.quad(graphics, 100, 100, 100, 100, Color.DARK_GRAY);
        Renderer2D.quad(graphics, 400, 400, 50, 50, Color.BLUE);
    }

    @Override
    public void mouseMoved(double x, double y) {

        super.mouseMoved(x, y);
    }
}
