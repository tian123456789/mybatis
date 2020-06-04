
package com.tgr.springbootmybatis.NumericGenerator;

public interface RandomStringGenerator {

	int getMinLength();

	int getMaxLength();

	String getNewString();

	byte[] getNewStringAsBytes();
}
