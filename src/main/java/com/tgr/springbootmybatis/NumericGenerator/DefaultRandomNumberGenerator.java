package com.tgr.springbootmybatis.NumericGenerator;

import java.security.SecureRandom;

import org.springframework.stereotype.Component;

@Component
public final class DefaultRandomNumberGenerator implements RandomStringGenerator {

	private static final char[] PRINTABLE_CHARACTERS = "0123456789".toCharArray();

	private static final int DEFAULT_MAX_RANDOM_LENGTH = 6;

	private SecureRandom randomizer = new SecureRandom();

	private final int maximumRandomLength;

	public DefaultRandomNumberGenerator() {
		this.maximumRandomLength = DEFAULT_MAX_RANDOM_LENGTH;
	}

	public DefaultRandomNumberGenerator(final int maxRandomLength) {
		this.maximumRandomLength = maxRandomLength;
	}

	public int getMinLength() {
		return this.maximumRandomLength;
	}

	public int getMaxLength() {
		return this.maximumRandomLength;
	}

	public String getNewString() {
		final byte[] random = getNewStringAsBytes();

		return convertBytesToString(random);
	}

	public byte[] getNewStringAsBytes() {
		final byte[] random = new byte[this.maximumRandomLength];

		this.randomizer.nextBytes(random);

		return random;
	}

	private String convertBytesToString(final byte[] random) {
		final char[] output = new char[random.length];
		for (int i = 0; i < random.length; i++) {
			final int index = Math.abs(random[i] % PRINTABLE_CHARACTERS.length);//random[i] % PRINTABLE_CHARACTERS.length对长度取余 最后都在数组范围内
			output[i] = PRINTABLE_CHARACTERS[index];
		}

		return new String(output);
	}
	
}
