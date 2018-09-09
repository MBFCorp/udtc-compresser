package me.mateusakino.udtc;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedList;

/**
 * Compresses data by turning bytes of the text into variable-width bytes, as
 * pre-defined in each data's dictionary. <br>
 * Example:
 * 
 * <pre>
 * dict| byte
 * ----|---------
 *  00 | 01100101
 *  01 | 01110011
 *  10 | 01110100
 * 
 * Turns: 01110100 01100101 01110011 01110100
 * Into : dict + 10 00 01 10
 * 
 * (Works better for huge texts)
 * </pre>
 * 
 * The more distinct bytes, the greater the result. <br>
 * The more bytes overall, the smaller the result. <br>
 * <br>
 * P.B. returns the string itself if the compressor wasn't efficient (dictionary too large for short texts).
 * 
 * @author Mateus de Aquino
 */

public class Compressor {

	/** 
	 * Compresses the string. <br>

	 * @param str
	 *            - Uncompressed string
	 * @param bin
	 *            - Define return as a <i>binary string</i> instead of encoded
	 *            text
	 * @return Compressed text (encoded)
	 * @author Mateus de Aquino
	 */

	public static String toUDTC(String str, boolean bin) {
		if (str.isEmpty())
			return str; 
		String result = toUDTC(str);
		return (bin) ? result : unbin(result.getBytes());
	}

	/** 
	 * Compresses the string and returns binary value. <br>

	 * @param str
	 *            - Uncompressed string
	 * @return Compressed text (encoded)
	 * @author Mateus de Aquino
	 */
	public static String toUDTC(String str) {
		if (str.isEmpty())
			return str;
		int dfltSize = bin(str).length();
		StringBuilder ubpc = new StringBuilder();
		LinkedList<Integer> dict, bytes = new LinkedList<Integer>();
		for (byte c : str.getBytes(StandardCharsets.UTF_8)) {
			int unsignedByte = c & 0xff;
			bytes.add(unsignedByte);
		}
		dict = new LinkedList<Integer>(Arrays.asList(bytes.stream().distinct().sorted().toArray(Integer[]::new)));
		// TODO CHANGE CHARS TO BYTES
		int tempLog = (int) str.chars().distinct().count();
		int bytelength = (tempLog < 2) ? tempLog : (int) (Math.log10(tempLog - 1) / Math.log10(2)) + 1;

		ubpc.append(bin(bytelength));
		ubpc.append("1110"); // [GS]
		// dicionario
		dict.stream().distinct().sorted().forEach(b -> ubpc.append(pad(Integer.parseInt(bin(b)), 8)));
		ubpc.append(pad(Integer.parseInt(bin(dict.get(0))), 8)); // [GS]
		// dados
		bytes.forEach(e -> ubpc.append(pad(Integer.parseInt(bin(dict.indexOf(e))), bytelength)));
		return (dfltSize > ubpc.length()) ? ubpc.toString() : bin(str);
	}

	public static String decompress(int[] unsignedBytes) {
		// int bytelength = -1, max_bin_length = -1; // deve ser par else
		// (0+str),
		// depois divide str ao meio
		byte[] bytes = new byte[unsignedBytes.length];
		for (int i = 0; i < unsignedBytes.length; i++) {
			bytes[i] = (byte) (unsignedBytes[i] & 0xff);
		}
		return new String(bytes, StandardCharsets.UTF_8);
	}

	public static String bin(String txt) {
		byte[] bytes = txt.getBytes();
		StringBuilder binary = new StringBuilder();
		for (byte b : bytes) {
			int val = b;
			for (int i = 0; i < 8; i++) {
				binary.append((val & 128) == 0 ? 0 : 1);
				val <<= 1;
			}
		}
		return binary.toString();
	}

	public static String bin(int num) {
		return Integer.toBinaryString(num);
	}

	public static String unbin(byte[] bytes) {
		CharBuffer cb = ByteBuffer.wrap(bytes).asCharBuffer();
		return cb.toString();
	}

	public static String pad(int num, int pad) {
		return String.format("%0" + pad + "d", num);
	}
}
