package me.blackout.assist.utils.input;

import com.mojang.blaze3d.platform.InputConstants;
import me.blackout.assist.Strife;
import me.blackout.assist.gui.GUI;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keymapping.v1.KeyMappingHelper;
import net.minecraft.client.KeyMapping;

import static me.blackout.assist.Strife.mc;

public class Input {
    public static void registerKey() {
        KeyMapping.Category CATEGORY = KeyMapping.Category.register(
                Strife.id("pvp_assist")
        );

        KeyMapping openGUIKey = KeyMappingHelper.registerKeyMapping(
                new KeyMapping(
                        "key.pvp-assist.open-gui",
                        InputConstants.Type.KEYBOARD,
                        InputConstants.KEY_PERIOD,
                        CATEGORY
                ));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (openGUIKey.consumeClick()) {
                mc.gui.setScreen(new GUI());
            }
        });
    }
}
