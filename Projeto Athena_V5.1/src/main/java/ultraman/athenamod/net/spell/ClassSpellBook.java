package ultraman.athenamod.net.spell;

import ultraman.athenamod.net.ClassType;

import java.util.List;

public class ClassSpellBook {
    public static List<Spell> getSpells(ClassType type) {
        return switch (type) {
            case WARRIOR -> List.of();   // TODO: TauntSpell, PowerAttackSpell...
            case ROGUE   -> List.of();   // TODO: InvisibleSpell, PoisonStrikeSpell...
            case MAGE    -> List.of(new FireballSpell());
            case CLERIC  -> List.of();   // TODO: HealSpell, RegenerateSpell...
            default      -> List.of();
        };
    }
}
