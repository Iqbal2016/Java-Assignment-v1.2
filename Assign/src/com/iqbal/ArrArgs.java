/**
 * 
 */
package com.iqbal;

/**
 * @author iqbal
 */
public class ArrArgs {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0) {
			System.out.println("No arguments provided.");
			return;
		}

		try {
			int k = 0;
			while (k < args.length) {
				System.out.println("Value of input is " + k + " and argument is " + args[k]);
				k++;
			}
		} catch (ArrayIndexOutOfBoundsException errorOc) {
			System.err.println("Error occurred: " + errorOc.toString());
		}

	}

}
