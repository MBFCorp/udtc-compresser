package me.mateusakino.udtc;

public class Main {

	// Unicode Binary Phases Compresser
	public static void main(String[] args) {
		String tst = ("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt"
						+ " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation"
						+ " ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in"
						+ " reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur."
						+ " Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt"
						+ " mollit anim id est laborum.");
		String ubpc = Compresser.toUDTC(tst, true);
		System.out.println("DEFAULT: " + Compresser.bin(tst).length()/8 + "bytes");
		System.out.println("UDTC   : " + ubpc.length()/8 + "bytes");
	}
}
