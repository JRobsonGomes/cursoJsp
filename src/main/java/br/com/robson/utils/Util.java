package br.com.robson.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;

public class Util {

	public static String formatTelefone(Long numero) {
		return numero.toString().replaceAll("(\\d{2})(\\d+)(\\d{4})", "($1) $2-$3");
	}
	
	public static String formatCep(Integer cep) {
		String cepStr = String.format("%08d", cep);
		return cepStr.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
	}

	public static LocalDate parseStringTolocalDateFromPattern(String localDate, String pattern) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		TemporalAccessor parse = formatter.parse(localDate);
		return LocalDate.from(parse);
	}
	
	private static LocalDate parseStringTolocalDateFromIsoDate(String localDate) {
		DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE;
		TemporalAccessor parse = formatter.parse(localDate);
		return LocalDate.from(parse);
	}
	
	public static String formatLocalDateToPattern(String localDate, String pattern) {
		if (localDate.isBlank() || pattern.isBlank()) {
			return "";
		}
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
		LocalDate date = parseStringTolocalDateFromIsoDate(localDate);
		return date.format(formatter);
	}
	
	public static Double parseStringToRendaMensal(String str) {
		try {
			String[] arr = str.split(",");
			String renda = arr[0].replaceAll("[^0-9]", "") + "." + arr[1];
			return Double.valueOf(renda);
		} catch (Exception e) {
			e.printStackTrace();
			return 0.0;
		}
	}
}
