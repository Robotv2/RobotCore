package fr.robotv2.robotcore.shared.item;

import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

public class HeadUtil {

    public static ItemStack getHead(EntityType type) {
        String url = switch (type) {
            case AXOLOTL -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzJiZDA0NWE5M2IzMGMwYzk1MDlhNmQ5ZTJkOWJkMGY2MWQ5ZDQwZjY1NWY3NDkxMmUwYTY2NmIwYTRiMWMzMiJ9fX0=";
            case BAT -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNjY4MWE3MmRhNzI2M2NhOWFlZjA2NjU0MmVjY2E3YTE4MGM0MGUzMjhjMDQ2M2ZjYjExNGNiM2I4MzA1NzU1MiJ9fX0=";
            case BEE -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMjU5MDAxYTg1MWJiMWI5ZTljMDVkZTVkNWM2OGIxZWEwZGM4YmQ4NmJhYmYxODhlMGFkZWQ4ZjkxMmMwN2QwZCJ9fX0=";
            case BLAZE -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYjIwNjU3ZTI0YjU2ZTFiMmY4ZmMyMTlkYTFkZTc4OGMwYzI0ZjM2Mzg4YjFhNDA5ZDBjZDJkOGRiYTQ0YWEzYiJ9fX0=";
            case CAT -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDAzYTJlMzc0MThlMGNmZmFhMmI1MTM5MTBjNTI4MmI5YmIwNmMzNWExZDQ3MDM5YTVjYzUxYjIzNGE1NDJmMyJ9fX0=";
            case CAVE_SPIDER -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTYxN2Y3ZGQ1ZWQxNmYzYmQxODY0NDA1MTdjZDQ0MGExNzAwMTViMWNjNmZjYjJlOTkzYzA1ZGUzM2YifX19";
            case CHICKEN -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMWZlNGNjOTYzZjI2MTcyYmFkNTM2NWFjYTIwOTExZGVkNzQ0YTJiNzQzNjc2YjExMmQ5YThjNDNlZjY1OSJ9fX0=";
            case COD -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzg5MmQ3ZGQ2YWFkZjM1Zjg2ZGEyN2ZiNjNkYTRlZGRhMjExZGY5NmQyODI5ZjY5MTQ2MmE0ZmIxY2FiMCJ9fX0=";
            case COW -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYmU4NDU2MTU1MTQyY2JlNGU2MTM1M2ZmYmFmZjMwNGQzZDljNGJjOTI0N2ZjMjdiOTJlMzNlNmUyNjA2N2VkZCJ9fX0=";
            case CREEPER -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvODMxYmEzOWExNmNhNDQyY2FiOTM1MDQwZGRjNzI2ZWRhZDlkZDI3NmVjZmFmNmZlZTk5NzY3NjhiYjI3NmJjMCJ9fX0=";
            case DOLPHIN -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOGU5Njg4Yjk1MGQ4ODBiNTViN2FhMmNmY2Q3NmU1YTBmYTk0YWFjNmQxNmY3OGU4MzNmNzQ0M2VhMjlmZWQzIn19fQ==";
            case DONKEY -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWVjNzgyZGNkMmJmYjc2M2VkZTZkNDRkNmI0ZTNhZjU0NzE4N2QxYjYxYmY4MTY0ODIyOWQ3MGU2ZWI1ODI1NCJ9fX0=";
            case DROWNED -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYzNmN2NjZjYxZGJjM2Y5ZmU5YTYzMzNjZGUwYzBlMTQzOTllYjJlZWE3MWQzNGNmMjIzYjNhY2UyMjA1MSJ9fX0=";
            case ELDER_GUARDIAN -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDM0MGEyNjhmMjVmZDVjYzI3NmNhMTQ3YTg0NDZiMjYzMGE1NTg2N2EyMzQ5ZjdjYTEwN2MyNmViNTg5OTEifX19";
            case ENDER_DRAGON -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNzM0ODA1OTIyNjZkZDdmNTM2ODFlZmVlZTMxODhhZjUzMWVlYTUzZGE0YWY1ODNhNjc2MTdkZWViNGY0NzMifX19";
            case ENDERMAN -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvYWFjYjM1NzcwOWQ4Y2RmMWNkOWM5ZGJlMzEzZTdiYWIzMjc2YWU4NDIzNDk4MmU5M2UxMzgzOWFiN2NjNWQxNiJ9fX0=";
            case ENDERMITE -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNWJjN2I5ZDM2ZmI5MmI2YmYyOTJiZTczZDMyYzZjNWIwZWNjMjViNDQzMjNhNTQxZmFlMWYxZTY3ZTM5M2EzZSJ9fX0=";
            case EVOKER -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTc5ZjEzM2E4NWZlMDBkM2NmMjUyYTA0ZDZmMmViMjUyMWZlMjk5YzA4ZTBkOGI3ZWRiZjk2Mjc0MGEyMzkwOSJ9fX0=";
            case FOX -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZDg5NTRhNDJlNjllMDg4MWFlNmQyNGQ0MjgxNDU5YzE0NGEwZDVhOTY4YWVkMzVkNmQzZDczYTNjNjVkMjZhIn19fQ==";
            case GHAST -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZGU4YTM4ZTlhZmJkM2RhMTBkMTliNTc3YzU1YzdiZmQ2YjRmMmU0MDdlNDRkNDAxN2IyM2JlOTE2N2FiZmYwMiJ9fX0=";
            case GLOW_SQUID -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNTVlMmI0NmU1MmFjOTJkNDE5YTJkZGJjYzljZGNlN2I0NTFjYjQ4YWU3MzlkODVkNjA3ZGIwNTAyYTAwOGNlMCJ9fX0=";
            case GOAT -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZjAzMzMwMzk4YTBkODMzZjUzYWU4YzlhMWNiMzkzYzc0ZTlkMzFlMTg4ODU4NzBlODZhMjEzM2Q0NGYwYzYzYyJ9fX0=";
            case GUARDIAN -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvNDk1MjkwZTA5MGMyMzg4MzJiZDc4NjBmYzAzMzk0OGM0ZDAzMTM1MzUzM2FjOGY2NzA5ODgyM2I3ZjY2N2YxYyJ9fX0=";
            case HOGLIN -> "";
            case HORSE -> "";
            case HUSK -> "";
            case LLAMA -> "";
            case MAGMA_CUBE -> "";
            case MUSHROOM_COW -> "";
            case MULE -> "";
            case OCELOT -> "";
            case PANDA -> "";
            case PARROT -> "";
            case PHANTOM -> "";
            case PIG -> "";
            case PIGLIN -> "";
            case PILLAGER -> "";
            case POLAR_BEAR -> "";
            case PUFFERFISH -> "";
            case RABBIT -> "";
            case RAVAGER -> "";
            case SALMON -> "";
            case SHEEP -> "";
            case SHULKER -> "";
            case SILVERFISH -> "";
            case SKELETON -> "";
            case SKELETON_HORSE -> "";
            case SLIME -> "";
            case SPIDER -> "";
            case SQUID -> "";
            case STRAY -> "";
            case STRIDER -> "";
            case TRADER_LLAMA -> "";
            case TROPICAL_FISH -> "";
            case TURTLE -> "";
            case VEX -> "";
            case VILLAGER -> "";
            case VINDICATOR -> "";
            case WANDERING_TRADER -> "";
            case WITCH -> "";
            case WITHER_SKELETON -> "";
            case WOLF -> "";
            case ZOGLIN -> "";
            case ZOMBIE -> "";
            case ZOMBIE_HORSE -> "";
            case ZOMBIE_VILLAGER -> "";
            case ZOMBIFIED_PIGLIN -> "";
            default -> "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZmUyNTRjN2FlNTBmMzk5ODljZDYzYmQ1OWI1OTY2NDMzYmUxYmE4ZjE3NDUwNDAxMDkxMzI5MTVlNjRlMjcyZSJ9fX0=";
        };
        return ItemAPI.createSkull(url);
    }
}
