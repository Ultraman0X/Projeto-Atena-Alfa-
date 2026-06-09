package ultraman.athenamod.net;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.attachment.v1.AttachmentRegistry;
import net.fabricmc.fabric.api.attachment.v1.AttachmentType;
import net.minecraft.util.Identifier;

public class AthenaMod implements ModInitializer {
    public static final String MOD_ID = "athenamod";
//    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    // Armazena o nome da classe como String ("WARRIOR", "ROGUE", "MAGE", "CLERIC", "NONE")
    public static final AttachmentType<String> PLAYER_CLASS_ATTACHMENT =
            AttachmentRegistry.createDefaulted(
                    Identifier.of(MOD_ID, "player_class"),
                    () -> "NONE"
            );

    // Armazena a mana atual do jogador
    public static final AttachmentType<Integer> PLAYER_MANA_ATTACHMENT =
            AttachmentRegistry.createDefaulted(
                    Identifier.of(MOD_ID, "player_mana"),
                    () -> 0
            );

    @Override
    public void onInitialize() {
        ModItens.register();
        System.out.println("Registrando itens Athena");
        ModNetworking.register();
        ModEvents.register();
        System.out.println("ATHENA MOD INICIALIZADO - TESTE 2025");
    }
}
