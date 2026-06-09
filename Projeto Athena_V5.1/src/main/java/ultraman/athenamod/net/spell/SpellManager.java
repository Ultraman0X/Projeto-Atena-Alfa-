package ultraman.athenamod.net.spell;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.world.World;
import ultraman.athenamod.net.PlayerClassData;

public class SpellManager {
    public static void castSpell(Spell spell, World world, PlayerEntity player) {
        if (!PlayerClassData.consumeMana(player, spell.getManaCost())) {
            player.sendMessage(Text.literal("Mana insuficiente!"), true);
            return;
        }
        spell.cast(world, player);
    }
}
