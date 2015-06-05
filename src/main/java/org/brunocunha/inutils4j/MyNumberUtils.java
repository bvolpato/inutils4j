package org.brunocunha.inutils4j;

import java.util.Random;

public class MyNumberUtils {

	// https://stackoverflow.com/questions/363681/generating-random-integers-in-a-range-with-java
	public static int randomIntBetween(int min, int max) {
		Random rand = new Random();
		return rand.nextInt((max - min) + 1) + min;
	}

	//https://stackoverflow.com/questions/13725478/java-random-long-value-in-an-interval
	public static long randomLongBetween(long min, long max) {
		Random rand = new Random();
		return min + (long) (rand.nextDouble() * (max - min));
	}
}
