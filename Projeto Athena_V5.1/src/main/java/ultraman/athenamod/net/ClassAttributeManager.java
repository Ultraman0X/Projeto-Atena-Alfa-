package ultraman.athenamod.net;

import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;

public class ClassAttributeManager {

    private static final Identifier HP_ID      = Identifier.of("athenamod", "class_hp");
    private static final Identifier SPEED_ID   = Identifier.of("athenamod", "class_speed");
    private static final Identifier DAMAGE_ID  = Identifier.of("athenamod", "class_damage");
    private static final Identifier ATTACK_ID  = Identifier.of("athenamod", "class_attack_speed");
    private static final Identifier BULWARK_ID = Identifier.of("athenamod", "class_knockback_resistance");
    private static final Identifier OVERWHELM_ID = Identifier.of("athenamod", "class_attack_knockback");
    private static final Identifier STEALTH_ID = Identifier.of("athenamod", "class_sneaking_speed");
    private static final Identifier LUCK_ID    = Identifier.of("athenamod", "class_luck");

    /** Remove todos os modificadores do mod antes de aplicar os da nova classe. */
    private static void removeAll(PlayerEntity player) {
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH), HP_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED), SPEED_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE), DAMAGE_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED), ATTACK_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE), BULWARK_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK), OVERWHELM_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_EFFICIENCY), STEALTH_ID);
        removeIfPresent(player.getAttributeInstance(EntityAttributes.GENERIC_LUCK), LUCK_ID);
    }

    private static void removeIfPresent(EntityAttributeInstance inst, Identifier id) {
        if (inst != null) inst.removeModifier(id);
    }

    private static void add(EntityAttributeInstance inst, Identifier id, double value) {
        if (inst != null) {
            inst.removeModifier(id);
            inst.addPersistentModifier(new EntityAttributeModifier(id, value,
                    EntityAttributeModifier.Operation.ADD_VALUE));
        }
    }

    public static void applyAttributes(PlayerEntity player, ClassType classType) {
        removeAll(player);

        EntityAttributeInstance health  = player.getAttributeInstance(EntityAttributes.GENERIC_MAX_HEALTH);
        EntityAttributeInstance speed   = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_SPEED);
        EntityAttributeInstance damage  = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_DAMAGE);
        EntityAttributeInstance attack  = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_SPEED);
        EntityAttributeInstance bulwark = player.getAttributeInstance(EntityAttributes.GENERIC_KNOCKBACK_RESISTANCE);
        EntityAttributeInstance breaker = player.getAttributeInstance(EntityAttributes.GENERIC_ATTACK_KNOCKBACK);
        EntityAttributeInstance stealth = player.getAttributeInstance(EntityAttributes.GENERIC_MOVEMENT_EFFICIENCY);
        EntityAttributeInstance luck    = player.getAttributeInstance(EntityAttributes.GENERIC_LUCK);

        switch (classType) {
            case WARRIOR -> {
                add(health,  HP_ID,       12.0);
                add(speed,   SPEED_ID,    0);
                add(damage,  DAMAGE_ID,   3.0);
                add(attack,  ATTACK_ID,   1.0);
                add(bulwark, BULWARK_ID,  0.75);
                add(breaker, OVERWHELM_ID, 1.0);
                add(stealth, STEALTH_ID,  0);
                add(luck,    LUCK_ID,     2.0);
            }
            case ROGUE -> {
                add(health,  HP_ID,       8.0);
                add(speed,   SPEED_ID,    0.1);
                add(damage,  DAMAGE_ID,   4.0);
                add(attack,  ATTACK_ID,   3.0);
                add(bulwark, BULWARK_ID,  0.25);
                add(breaker, OVERWHELM_ID, 1.5);
                add(stealth, STEALTH_ID,  0.7);
                add(luck,    LUCK_ID,     6.0);
            }
            case MAGE -> {
                add(health,  HP_ID,       -4.0);
                add(speed,   SPEED_ID,    0.05);
                add(damage,  DAMAGE_ID,   5.0);
                add(attack,  ATTACK_ID,   2.0);
                add(bulwark, BULWARK_ID,  0.05);
                add(breaker, OVERWHELM_ID, 0.5);
                add(stealth, STEALTH_ID,  0.4);
                add(luck,    LUCK_ID,     8.0);
            }
            case CLERIC -> {
                add(health,  HP_ID,       6.0);
                add(speed,   SPEED_ID,    -0.005);
                add(damage,  DAMAGE_ID,   2.0);
                add(attack,  ATTACK_ID,   1.0);
                add(bulwark, BULWARK_ID,  0.75);
                add(breaker, OVERWHELM_ID, 1.5);
                add(stealth, STEALTH_ID,  0);
                add(luck,    LUCK_ID,     4.0);
            }
            default -> { /* NONE — atributos já foram removidos */ }
        }
    }
}
