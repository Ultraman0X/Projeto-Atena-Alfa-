package ultraman.athenamod.net;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;

import static ultraman.athenamod.net.AthenaMod.PLAYER_CLASS_ATTACHMENT;
import static ultraman.athenamod.net.AthenaMod.PLAYER_MANA_ATTACHMENT;

public class PlayerClassData {

    public static ClassType get(PlayerEntity player) {
        String stored = player.getAttachedOrSet(PLAYER_CLASS_ATTACHMENT, "NONE");
        return ClassType.fromString(stored);
    }

    public static void set(PlayerEntity player, String className) {
        ClassType type = ClassType.fromString(className);
        setClass(player, type);
    }

    public static void setClass(PlayerEntity player, ClassType newClass) {
        player.setAttached(PLAYER_CLASS_ATTACHMENT, newClass.name());
        ClassAttributeManager.applyAttributes(player, newClass);
        float maxHealth = player.getMaxHealth();
        player.setHealth(maxHealth);
        if (newClass == ClassType.WARRIOR) {
            setMana(player, 50);
        }
        if (newClass == ClassType.ROGUE) {
            setMana(player, 100);
        }
        if (newClass == ClassType.MAGE) {
            setMana(player, 250);
        }
        if (newClass == ClassType.CLERIC) {
            setMana(player, 200);
        } else {
            setMana(player, 0);
        }

    }

    public static int getMana(PlayerEntity player) {
        return player.getAttachedOrSet(PLAYER_MANA_ATTACHMENT, 0);
    }

    public static void setMana(PlayerEntity player, int value) {
        int maxMana = get(player).getMaxMana();
        int currentMana = Math.max(0, Math.min(value, maxMana));

        // Salva no Attachment (Servidor)
        player.setAttached(AthenaMod.PLAYER_MANA_ATTACHMENT, currentMana);
    }

    public static void addMana(PlayerEntity player, int value) {
        setMana(player, getMana(player) + value);
    }

    public static boolean consumeMana(PlayerEntity player, int cost) {
        int current = getMana(player);
        if (current < cost) return false;
        setMana(player, current - cost);
        return true;
    }

    public static void tick(ServerPlayerEntity player) {
        ClassType type = get(player);
        if (!type.usesMana()) return;
        int current = getMana(player);
        int max = type.getMaxMana();
        if (current < max) {
            addMana(player, 1);
        }
    }
}