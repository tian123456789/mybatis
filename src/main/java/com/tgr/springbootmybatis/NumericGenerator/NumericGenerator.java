
package com.tgr.springbootmybatis.NumericGenerator;

public interface NumericGenerator {

	String getNextNumberAsString();

	int maxLength();

	int minLength();
}
