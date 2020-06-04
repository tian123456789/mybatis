/*
 * Copyright 2002-2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package Zorg.springframework.util;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.UUID;
import java.util.stream.Stream;

/**
 * An {@link IdGenerator} that uses {@link SecureRandom} for the initial seed and
 * {@link Random} thereafter, instead of calling {@link UUID#randomUUID()} every
 * time as {@link org.springframework.util.JdkIdGenerator JdkIdGenerator} does.
 * This provides a better balance between securely random ids and performance.
 *
 * @author Rossen Stoyanchev
 * @author Rob Winch
 * @since 4.0
 */
public class AlternativeJdkIdGenerator implements IdGenerator {

	private final Random random;


	public AlternativeJdkIdGenerator() {
		SecureRandom secureRandom = new SecureRandom();
		byte[] seed = new byte[8];
		secureRandom.nextBytes(seed);
		this.random = new Random(new BigInteger(seed).longValue());
	}
	
	public static void main(String[] args) {
		SecureRandom secureRandom = new SecureRandom();
		byte[] seed = new byte[8];
		secureRandom.nextBytes(seed);
		System.err.println(new BigInteger(seed).longValue());
		Random random = new Random(new BigInteger(seed).longValue());
		
		byte[] randomBytes = new byte[16];
		random.nextBytes(randomBytes);//生成随机字节并将它们放入用户提供的字节数组中。产生的随机字节数等于字节数组的长度。
		Stream.of(Arrays.asList(randomBytes)).forEach((x) ->{
			System.out.print(x);
		});
		
		for (int i = 8; i < 16; i++) {
			System.err.println(randomBytes[i] /*& 0xff*/);
		}

	}


	@Override
	public UUID generateId() {
		byte[] randomBytes = new byte[16];
		this.random.nextBytes(randomBytes);

		long mostSigBits = 0;
		for (int i = 0; i < 8; i++) {
			mostSigBits = (mostSigBits << 8) | (randomBytes[i] & 0xff);
		}

		long leastSigBits = 0;
		for (int i = 8; i < 16; i++) {
			leastSigBits = (leastSigBits << 8) | (randomBytes[i] & 0xff);
		}

		return new UUID(mostSigBits, leastSigBits);//使用指定的数据构造新的UUID。mostSigBits用于UUID的最高有效64位，leastSigBits成为UUID的最低有效64位。
	}

}
