package princ.library.client;

import com.mojang.blaze3d.platform.InputConstants;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class KeyMappingHelper {
    public static void registerKeyMapping(String id, InputConstants.Type type, int defaultKey, String category, BiConsumer<Minecraft, KeyMapping> onEndTick) {
        KeyMapping thisKey = new KeyMapping(
                id,
                type,
                defaultKey,
                category
        );

        KeyBindingHelper.registerKeyBinding(thisKey);

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            onEndTick.accept(client, thisKey);
        });
    }

    public static void registerOwnKeyMapping(KeyMapping key, Consumer<Minecraft> onEndTick) {
        KeyBindingHelper.registerKeyBinding(key);
        ClientTickEvents.END_CLIENT_TICK.register(onEndTick::accept);
    }

    public static boolean isKeyDown(KeyMapping key) {
        return InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), InputConstants.getKey(key.saveString()).getValue());
    }
}
