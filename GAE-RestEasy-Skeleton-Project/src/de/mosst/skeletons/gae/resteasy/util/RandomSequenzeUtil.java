package de.mosst.skeletons.gae.resteasy.util;

import java.security.SecureRandom;

public class RandomSequenzeUtil {

	private final static String  POSIBLE_SYMBOLS = "qwertzuiopasdfghjklyxcvbnmMNBCXYLKJHGFDSAPOIUZTRWQ1234567890";

	public static String createRandomCharSequenze(int size) {

		String randomSymbols = "";
		SecureRandom randomizer = new SecureRandom();
		for(int i = 0; i < size; i++){
			int index = randomizer.nextInt(POSIBLE_SYMBOLS.length());
			char nextSymbol = POSIBLE_SYMBOLS.charAt(index);
			randomSymbols += nextSymbol;
		}
		return randomSymbols;
    }

}