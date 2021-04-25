package com.magicsweet.lib.magiclib.key;

import org.bukkit.potion.PotionEffectType;

public enum EnumPotion implements IGettable<PotionEffectType> {
	SPEED,
	SLOWNESS,
	HASTE,
	MINING_FATIGUE,
	STRENGTH,
	INSTANT_HEALTH,
	INSTANT_DAMAGE,
	JUMP_BOOST,
	NAUSEA,
	REGENERATION,
	RESISTANCE,
	FIRE_RESISTANCE,
	WATER_BREATHING,
	INVISIBILITY,
	BLINDNESS,
	NIGHT_VISION,
	HUNGER,
	WEAKNESS,
	POISON,
	WITHER,
	HEALTH_BOOST,
	ABSORPTION,
	SATURATION,
	GLOWING,
	LEVITATION,
	LUCK,
	UNLUCK,
	SLOW_FALLING,
	CONDUIT_POWER,
	DOLPHINS_GRACE,
	BAD_OMEN,
	HERO_OF_THE_VILLAGE;
	
	@Override
	public PotionEffectType get() {
		switch (this) {
			case SPEED:
				return PotionEffectType.SPEED;
			case SLOWNESS:
				return PotionEffectType.SLOW;
			case HASTE:
				return PotionEffectType.FAST_DIGGING;
			case MINING_FATIGUE:
				return PotionEffectType.SLOW_DIGGING;
			case STRENGTH:
				return PotionEffectType.INCREASE_DAMAGE;
			case INSTANT_HEALTH:
				return PotionEffectType.HEAL;
			case INSTANT_DAMAGE:
				return PotionEffectType.HARM;
			case JUMP_BOOST:
				return PotionEffectType.JUMP;
			case NAUSEA:
				return PotionEffectType.CONFUSION;
			case REGENERATION:
				return PotionEffectType.REGENERATION;
			case RESISTANCE:
				return PotionEffectType.DAMAGE_RESISTANCE;
			case FIRE_RESISTANCE:
				return PotionEffectType.FIRE_RESISTANCE;
			case WATER_BREATHING:
				return PotionEffectType.WATER_BREATHING;
			case INVISIBILITY:
				return PotionEffectType.INVISIBILITY;
			case BLINDNESS:
				return PotionEffectType.BLINDNESS;
			case NIGHT_VISION:
				return PotionEffectType.NIGHT_VISION;
			case HUNGER:
				return PotionEffectType.HUNGER;
			case WEAKNESS:
				return PotionEffectType.WEAKNESS;
			case POISON:
				return PotionEffectType.POISON;
			case WITHER:
				return PotionEffectType.WITHER;
			case HEALTH_BOOST:
				return PotionEffectType.HEALTH_BOOST;
			case ABSORPTION:
				return PotionEffectType.ABSORPTION;
			case SATURATION:
				return PotionEffectType.SATURATION;
			case GLOWING:
				return PotionEffectType.GLOWING;
			case LEVITATION:
				return PotionEffectType.LEVITATION;
			case LUCK:
				return PotionEffectType.LUCK;
			case UNLUCK:
				return PotionEffectType.UNLUCK;
			case SLOW_FALLING:
				return PotionEffectType.SLOW_FALLING;
			case CONDUIT_POWER:
				return PotionEffectType.CONDUIT_POWER;
			case DOLPHINS_GRACE:
				return PotionEffectType.DOLPHINS_GRACE;
			case BAD_OMEN:
				return PotionEffectType.BAD_OMEN;
			case HERO_OF_THE_VILLAGE:
				return PotionEffectType.HERO_OF_THE_VILLAGE;
			default:
				return null;
		}
	}
	
}
