package com.magicsweet.lib.magiclib.key;

import lombok.NonNull;
import org.bukkit.enchantments.Enchantment;

public enum EnumEnchant implements IGettable<Enchantment> {
	POWER,
	FLAME,
	INFINITY,
	PUNCH,
	CURSE_OF_BINDING,
	CHANNELING,
	SHARPNESS,
	BANE_OF_ARTHOROPODS,
	SMITE,
	DEPTH_STRIDER,
	EFFICIENCY,
	UNBREAKING,
	FIRE_ASPECT,
	FROST_WALKER,
	IMPALING,
	KNOCKBACK,
	LOOTING,
	FORTUNE,
	LOYALTY,
	LUCK_OF_THE_SEA,
	LURE,
	MENDING,
	MULTISHOT,
	RESPIRATION,
	PIERCING,
	PROTECTION,
	BLAST_PROTECTION,
	FEATHER_FALLING,
	FIRE_PROTECTION,
	PROJECTILE_PROTECTION,
	QUICK_CHARGE,
	RIPTIDE,
	SILK_TOUCH,
	SOUL_SPEED,
	SWEEPING_EDGE,
	THORNS,
	CURSE_OF_VANISHING,
	AQUA_AFFINITY;
	
	@Override
	public @NonNull Enchantment get() {
		switch (this) {
			case POWER:
				return Enchantment.ARROW_DAMAGE;
			case FLAME:
				return Enchantment.ARROW_FIRE;
			case INFINITY:
				return Enchantment.ARROW_INFINITE;
			case PUNCH:
				return Enchantment.ARROW_KNOCKBACK;
			case CURSE_OF_BINDING:
				return Enchantment.BINDING_CURSE;
			case CHANNELING:
				return Enchantment.CHANNELING;
			case SHARPNESS:
				return Enchantment.DAMAGE_ALL;
			case BANE_OF_ARTHOROPODS:
				return Enchantment.DAMAGE_ARTHROPODS;
			case SMITE:
				return Enchantment.DAMAGE_UNDEAD;
			case DEPTH_STRIDER:
				return Enchantment.DEPTH_STRIDER;
			case EFFICIENCY:
				return Enchantment.DIG_SPEED;
			case UNBREAKING:
				return Enchantment.DURABILITY;
			case FIRE_ASPECT:
				return Enchantment.FIRE_ASPECT;
			case FROST_WALKER:
				return Enchantment.FROST_WALKER;
			case IMPALING:
				return Enchantment.IMPALING;
			case KNOCKBACK:
				return Enchantment.KNOCKBACK;
			case LOOTING:
				return Enchantment.LOOT_BONUS_MOBS;
			case FORTUNE:
				return Enchantment.LOOT_BONUS_BLOCKS;
			case LOYALTY:
				return Enchantment.LOYALTY;
			case LUCK_OF_THE_SEA:
				return Enchantment.LUCK;
			case LURE:
				return Enchantment.LURE;
			case MENDING:
				return Enchantment.MENDING;
			case MULTISHOT:
				return Enchantment.MULTISHOT;
			case RESPIRATION:
				return Enchantment.OXYGEN;
			case PIERCING:
				return Enchantment.PIERCING;
			case PROTECTION:
				return Enchantment.PROTECTION_ENVIRONMENTAL;
			case BLAST_PROTECTION:
				return Enchantment.PROTECTION_EXPLOSIONS;
			case FEATHER_FALLING:
				return Enchantment.PROTECTION_FALL;
			case FIRE_PROTECTION:
				return Enchantment.PROTECTION_FIRE;
			case PROJECTILE_PROTECTION:
				return Enchantment.PROTECTION_PROJECTILE;
			case QUICK_CHARGE:
				return Enchantment.QUICK_CHARGE;
			case RIPTIDE:
				return Enchantment.RIPTIDE;
			case SILK_TOUCH:
				return Enchantment.SILK_TOUCH;
			case SOUL_SPEED:
				return Enchantment.SOUL_SPEED;
			case SWEEPING_EDGE:
				return Enchantment.SWEEPING_EDGE;
			case THORNS:
				return Enchantment.THORNS;
			case CURSE_OF_VANISHING:
				return Enchantment.VANISHING_CURSE;
			case AQUA_AFFINITY:
				return Enchantment.WATER_WORKER;
			default:
				return null;
		}
	}
	
}
