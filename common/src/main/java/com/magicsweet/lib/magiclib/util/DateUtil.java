package com.magicsweet.lib.magiclib.util;

import lombok.experimental.UtilityClass;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.format.TextStyle;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

@UtilityClass
public class DateUtil {
	
	@UtilityClass
	public static class Translations {
		public String LESS_THAN_SECOND = "less than 1s";
		public String SECONDS = "s";
		public String MINUTES = "m";
		public String HOURS = "h";
		public String DAYS = "d";
	}
	
	public String format(long unixTimestamp) {
		return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
			.withLocale(new Locale("ru")).withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(unixTimestamp)) + " &8(" + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, new Locale("ru")) + ")";
	}
	
	public String formatnc(long unixTimestamp) {
		return DateTimeFormatter.ofLocalizedDateTime(FormatStyle.LONG, FormatStyle.SHORT)
			.withLocale(new Locale("ru")).withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(unixTimestamp)) + " (" + ZoneId.systemDefault().getDisplayName(TextStyle.SHORT, new Locale("ru")) + ")";
	}
	
	public String convert(long seconds) {
		
		long day = TimeUnit.SECONDS.toDays(seconds);
		long hours = TimeUnit.SECONDS.toHours(seconds) - (day * 24L);
		long minute = TimeUnit.SECONDS.toMinutes(seconds) - (TimeUnit.SECONDS.toHours(seconds)* 60);
		long second = TimeUnit.SECONDS.toSeconds(seconds) - (TimeUnit.SECONDS.toMinutes(seconds) *60);
		
		if (day <= 0) {
			if (hours <= 0) {
				if (minute <= 0) {
					if (second <= 0) {
						return Translations.LESS_THAN_SECOND;
					} else {
						return + second + Translations.SECONDS;
					}
				} else {
					return + minute + Translations.MINUTES;
				}
			} else {
				return hours + Translations.HOURS;
			}
		} else {
			return day + Translations.DAYS;
		}
	}
	
	public String convert(int seconds) {
		return convert((long) seconds);
	}
}
