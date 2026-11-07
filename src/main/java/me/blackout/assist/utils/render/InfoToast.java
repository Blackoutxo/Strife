package me.blackout.assist.utils.render;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastManager;
import net.minecraft.client.renderer.RenderPipelines;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextColor;
import net.minecraft.resources.Identifier;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jspecify.annotations.NonNull;

import java.awt.*;

import static me.blackout.assist.Strife.mc;

public class InfoToast implements Toast {
    private static final int TITLE_COLOR = Color.HSBtoRGB(214, 76, 100);
    private static final int TEXT_COLOR = Color.HSBtoRGB(0, 0, 86);
    private static final Identifier TEXTURE = Identifier.parse("toast/advancement");
    private static final long DEFAULT_DURATION = 6000;
    private static final SimpleSoundInstance DEFAULT_SOUND = SimpleSoundInstance.forUI(SoundEvents.NOTE_BLOCK_CHIME.value(), 1.2f, 1);

    private @NotNull Component title;
    private @Nullable Component text;
    private @Nullable ItemStack icon;
    private @Nullable SimpleSoundInstance customSound;
    private long duration;

    private boolean soundPlayed;
    private long start = -1;
    private Visibility visibility = Visibility.HIDE;

    private InfoToast(Builder builder) {
        this.title = builder.title;
        this.text = builder.text;
        this.icon = builder.icon;
        this.customSound = builder.customSound;
        this.duration = builder.duration;
    }

    public static class Builder {
        private final @NotNull Component title;
        private @Nullable Component text;
        private @Nullable ItemStack icon;
        private @Nullable SimpleSoundInstance customSound = DEFAULT_SOUND;
        private long duration = DEFAULT_DURATION;

        public Builder(@NotNull String title) {
            this.title = net.minecraft.network.chat.Component.literal(title).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TITLE_COLOR)));
        }

        public Builder text(@Nullable String text) {
            this.text = text != null && !text.trim().isEmpty() ? Component.literal(text).setStyle(Style.EMPTY.withColor(TextColor.fromRgb(TEXT_COLOR))) : null;
            return this;
        }

        public Builder icon(@Nullable Item item) {
            this.icon = item != null ? item.getDefaultInstance() : null;
            return this;
        }

        public Builder sound(@Nullable SimpleSoundInstance sound) {
            this.customSound = sound;
            return this;
        }

        public Builder duration(long duration) {
            this.duration = Math.max(0, duration);
            return this;
        }

        public InfoToast build() {
            return new InfoToast(this);
        }
    }

    @Override
    public @NonNull Visibility getWantedVisibility() {
        return this.visibility;
    }

    @Override
    public void update(@NonNull ToastManager manager, long time) {
        if (start == -1) start = time;

        visibility = time - start >= duration ? Visibility.HIDE : Visibility.SHOW;

        if (!soundPlayed) {
            mc.getSoundManager().play(customSound);
            soundPlayed = true;
        }
    }

    @Override
    public void extractRenderState(GuiGraphicsExtractor graphics, @NonNull Font font, long fullyVisibleForMs) {
        graphics.blitSprite(RenderPipelines.GUI_TEXTURED, TEXTURE, 0, 0, width(), height());

        int textX = icon != null ? 28 : 12;
        int titleY = 12;

        if (text != null) {
            graphics.text(font, text, textX, 18, TEXT_COLOR, false);
            titleY = 7;
        }

        graphics.text(font, title, textX, titleY, TITLE_COLOR, false);

        if (icon != null) graphics.item(icon, 8, 8);
    }
}
