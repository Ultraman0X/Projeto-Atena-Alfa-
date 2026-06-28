package ultraman.athenamod.net;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.entity.player.PlayerEntity;

@Environment(EnvType.CLIENT)
public class ManaHudOverlay {

    private static int currentMana = 0;
    private static int maxMana     = 0;

    public static void setMana(int current, int max) {
        currentMana = current;
        maxMana     = max;
    }

    public static void render(DrawContext drawContext, float tickDelta) {
        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player = mc.player;

        // Sem jogador ou em tela de pausa/menu, não renderiza
        if (player == null || mc.currentScreen != null) return;

        ClassType classType = PlayerClassData.get(player);

        // Só mostra a barra se a classe tiver mana
        //if (!classType.usesMana()) return;

        int x      = 10;
        int y      = mc.getWindow().getScaledHeight() - 60;
        int width  = 80;
        int filled = maxMana > 0 ? (currentMana * width) / maxMana : 0;

        // Fundo escuro
        drawContext.fill(x, y, x + width, y + 8, 0xFF222222);
        // Barra de mana (azul)
        drawContext.fill(x, y, x + filled, y + 8, 0xFF4444FF);
        // Texto "Mana: X/Y"
        drawContext.drawText(
                mc.textRenderer,
                "Mana: " + currentMana + "/" + maxMana,
                x, y - 10,
                0xFFAAAAFF,
                true
        );
    }
}
