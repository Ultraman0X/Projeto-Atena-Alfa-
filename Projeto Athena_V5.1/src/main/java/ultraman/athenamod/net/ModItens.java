package ultraman.athenamod.net;

import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItens {

    public static final Item TOKEN_GUERREIRO = Registry.register(
            Registries.ITEM,
            Identifier.of(AthenaMod.MOD_ID, "token_guerreiro"),
            new ClassTokenItem(ClassType.WARRIOR, new Item.Settings())
    );

    public static final Item TOKEN_LADINO = Registry.register(
            Registries.ITEM,
            Identifier.of(AthenaMod.MOD_ID, "token_ladino"),
            new ClassTokenItem(ClassType.ROGUE, new Item.Settings())
    );

    public static final Item TOKEN_MAGO = Registry.register(
            Registries.ITEM,
            Identifier.of(AthenaMod.MOD_ID, "token_mago"),
            new ClassTokenItem(ClassType.MAGE, new Item.Settings())
    );

    public static final Item TOKEN_CLERIGO = Registry.register(
            Registries.ITEM,
            Identifier.of(AthenaMod.MOD_ID, "token_clerigo"),
            new ClassTokenItem(ClassType.CLERIC, new Item.Settings())
    );

    public static void register() {
        // Adiciona os tokens ao grupo de ferramentas/utilitários para fácil acesso
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.TOOLS).register(entries -> {
            entries.add(TOKEN_GUERREIRO);
            entries.add(TOKEN_LADINO);
            entries.add(TOKEN_MAGO);
            entries.add(TOKEN_CLERIGO);
        });
    }
}
