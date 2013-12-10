import java.util.Random;

/**
 * Author: eeroglu
 * Date: 7 Ara 2013 11:14:32
 * Package: 
 *
 * 
 */
public class App {

	private static final int NUMBER_OF_RANDOMS = 100_000_000;
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		firstMethod();
		System.gc();
		System.out.println();
		System.out.println();
		secondMethod();
		System.gc();
		System.out.println();
		System.out.println();
		thirdMethod();
	}
	
	public static double[] generateRandomNumbers(int total) {
		Random RandomGenerator = new Random();
		double[] randoms = new double[total];
		for(int i = 0; i < total; i++) {
			randoms[i] = RandomGenerator.nextDouble();
		}
		
		return randoms;
	}
	
	public static void firstMethod() {
		// first method
		int[] mappedRandoms = new int[11];
		double[] checker = {0.033, 0.050, 0.11, 0.15, 0.25, 0.262, 0.45, 0.475, 0.7, 0.8, 1.0};
		
		// Start Creating Mapped Randoms
		long start = System.nanoTime();
		double[] randoms = App.generateRandomNumbers(NUMBER_OF_RANDOMS);
		long start2 = System.nanoTime();
		for(int i = 0; i < NUMBER_OF_RANDOMS; i++) {
			for(int j = 0; j < 11; j++) {
				if(randoms[i] <= checker[j]) {
					mappedRandoms[j] += 1;
					break;
				}
			}
		}
		long end = System.nanoTime();
		System.out.printf("First method tooks:%.6f and %.6f seconds\n", (start2 - start) / 1e9, (end - start2) / 1e9);
		for(int i = 0; i < 11; i++) {
			System.out.printf("%d: %d times - distribution: %.6f \n", i + 1, mappedRandoms[i], (double) mappedRandoms[i] / NUMBER_OF_RANDOMS);
		}
	}
	
	public static void secondMethod() {
		// second method
		int[] mappedRandoms = new int[11];
		int[] checker = new int[1001];
		for(int i = 1; i <= 1000; i++) {
			if(i <= 33) checker[i] = 0;
			else if(i <= 50) checker[i] = 1;
			else if(i <= 110) checker[i] = 2;
			else if(i <= 150) checker[i] = 3;
			else if(i <= 250) checker[i] = 4;
			else if(i <= 262) checker[i] = 5;
			else if(i <= 450) checker[i] = 6;
			else if(i <= 475) checker[i] = 7;
			else if(i <= 700) checker[i] = 8;
			else if(i <= 800) checker[i] = 9;
			else checker[i] = 10;
		}
		
		// Start Creating Mapped Randoms
		long start = System.nanoTime();
		double[] randoms = App.generateRandomNumbers(NUMBER_OF_RANDOMS);
		long start2 = System.nanoTime();
		for(int i = 0; i < NUMBER_OF_RANDOMS; i++) {
			int number = (int)(1 + (1000 * randoms[i]));
			mappedRandoms[checker[number]] += 1;
		}
		long end = System.nanoTime();
		System.out.printf("Second method tooks:%.6f and %.6f seconds\n", (start2 - start) / 1e9, (end - start2) / 1e9);
		for(int i = 0; i < 11; i++) {
			System.out.printf("%d: %d times - distribution: %.6f \n", i + 1, mappedRandoms[i], (double) mappedRandoms[i] / NUMBER_OF_RANDOMS);
		}
	}
	
	public static void thirdMethod() {
		// third method
		int[] mappedRandoms = new int[11];
		double[] cutoff = {0, 0.396, 0.204, 0.72, 0.48, 0.92, 0.144, 0.94, 0.3, 0, 0, 0};
		int[] alt = {9, 11, 7, 5, 7, 10, 11, 10, 9, 9, 10, 11};
		
		// Start Creating Mapped Randoms
		long start = System.nanoTime();
		double[] randoms = App.generateRandomNumbers(NUMBER_OF_RANDOMS);
		double[] second_randoms = App.generateRandomNumbers(NUMBER_OF_RANDOMS);
		long start2 = System.nanoTime();
		for(int i = 0; i < NUMBER_OF_RANDOMS; i++) {
			int number = (int)(1 + (11 * second_randoms[i]));
			if(randoms[i] <= cutoff[number]) mappedRandoms[number - 1] += 1;
			else mappedRandoms[alt[number] - 1] += 1;			
		}
		long end = System.nanoTime();
		System.out.printf("Third method tooks:%.6f and %.6f seconds\n", (start2 - start) / 1e9, (end - start2) / 1e9);
		for(int i = 0; i < 11; i++) {
			System.out.printf("%d: %d times - distribution: %.6f \n", i + 1, mappedRandoms[i], (double) mappedRandoms[i] / NUMBER_OF_RANDOMS);
		}
	}
}
