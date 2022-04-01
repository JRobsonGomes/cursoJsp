package br.com.robson.utils;

public class Util {

	public static String formatTelefone(Long numero) {
		return numero.toString().replaceAll("(\\d{2})(\\d+)(\\d{4})", "($1) $2-$3");
	}
	
	public static String formatCep(Integer cep) {
		String cepStr = String.format("%08d", cep);
		return cepStr.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
	}
}
