package ultraman.athenamod.net.spell;

import ultraman.athenamod.net.ClassType;

import java.util.List;

public class ClassSpellBook {
    public static List<Spell> getSpells(ClassType type) {
        return switch (type) {
            case WARRIOR -> List.of(new TauntSpell(), new PowerAttackSpell(), new RallySpell());
            case ROGUE   -> List.of(new InvisibleSpell(), new PoisonAttackSpell(), new AgilitySpell());
            case MAGE    -> List.of(new FireballSpell(), new WindBurstSpell(), new TeleportSpell());
            case CLERIC  -> List.of(new HealSpell(), new BlessSpell(), new BaneSpell());
            default      -> List.of();
        };
    }
}
