package ultraman.athenamod.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

@Environment(EnvType.CLIENT)
public class ModKeyBindings {

    private static final String CATEGORY = "key.athenamod.spells";

    public static KeyBinding SPELL_1;
    public static KeyBinding SPELL_2;
    public static KeyBinding SPELL_3;
    public static KeyBinding SPELL_4;
    public static KeyBinding ULTIMATE;

    public static void register() {
        SPELL_1  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.athenamod.spell1",  InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_V, CATEGORY));
        SPELL_2  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.athenamod.spell2",  InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_B, CATEGORY));
        SPELL_3  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.athenamod.spell3",  InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_N, CATEGORY));
        SPELL_4  = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.athenamod.spell4",  InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_M, CATEGORY));
        ULTIMATE = KeyBindingHelper.registerKeyBinding(new KeyBinding("key.athenamod.ultimate", InputUtil.Type.KEYSYM, GLFW.GLFW_KEY_Z, CATEGORY));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            if (client.player == null) return;
            while (SPELL_1.wasPressed())  AthenaModClient.sendSpellPacket(0);
            while (SPELL_2.wasPressed())  AthenaModClient.sendSpellPacket(1);
            while (SPELL_3.wasPressed())  AthenaModClient.sendSpellPacket(2);
            while (SPELL_4.wasPressed())  AthenaModClient.sendSpellPacket(3);
            while (ULTIMATE.wasPressed()) AthenaModClient.sendSpellPacket(4);
        });
    }
}
