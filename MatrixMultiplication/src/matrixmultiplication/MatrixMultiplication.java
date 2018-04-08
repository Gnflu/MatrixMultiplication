/**
* The MatrixMultiplication program implements an application that
* multiply two matrixes A & B using partitioning algorithm & java concurrency 
* and displays the result to the standard output.
*
* @author  nuha diab
* @since   2018-04-2 
*/
package matrixmultiplication;

import java.util.Random;
import java.util.stream.IntStream;

public class MatrixMultiplication {
	public static int N = 1000; //number of dimensions
	public static int T = 2; //number of threads (currently is set to #Processors)
	public static int [][] A = random(N);
    public static int [][] B = random(N);    
    public static int [][] C1 = new int [N][N]; // result of multiplication
    public static int [][] C2 = new int [N][N];
    public static int [][] C3 = new int [N][N];
    public static int [][] C4 = new int [N][N];
    public static int [][] C5 = new int [N][N];
    public static int [][] C6 = new int [N][N];
   
	public static void main(String[] args) {
		int cores = Runtime.getRuntime().availableProcessors();
		
		long startTime1 = System.currentTimeMillis();
		C1 = multiply(A, B, N);
		long endTime1   = System.currentTimeMillis();
		
		long startTime2 = System.currentTimeMillis();
		C2 = multiplyParallelLoop(A, B, N);
		long endTime2   = System.currentTimeMillis();
		
		long startTime3 = System.currentTimeMillis();
		C3 = multiplyTthreads(A, B, N, T);
		long endTime3   = System.currentTimeMillis();
		
		long startTime4 = System.currentTimeMillis();
		C4 = multiplyTthreads(A, B, N, 2*T);
		long endTime4   = System.currentTimeMillis();
		
		long startTime5 = System.currentTimeMillis();
		C5 = multiplyT2threads(A, B, N, T);
		long endTime5   = System.currentTimeMillis();
		
		long startTime6 = System.currentTimeMillis();
		C6 = multiplyT2threads(A, B, N, 2*T);
		long endTime6   = System.currentTimeMillis();
		
		
        System.out.println("num of cores: " + cores);
		long totalTime1 = endTime1 - startTime1;
		long totalTime2 = endTime2 - startTime2;
		long totalTime3 = endTime3 - startTime3;
		long totalTime4 = endTime4 - startTime4;
		long totalTime5 = endTime5 - startTime5;
		long totalTime6 = endTime6 - startTime6;
		System.out.println("regular multiplication: " + totalTime1);
		System.out.println("one loop in parallel: " + totalTime2);
		System.out.println(T + " (T) partitions: " + totalTime3);
		System.out.println((2*T) + " (2T) partitions: " + totalTime4);
		System.out.println((T*T) + " (T^2) partitions: " + totalTime5);
		System.out.println(((2*T)*(2*T)) + " (2T^2) partitions: " + totalTime6);

	}
	
	//multiply two matrices by dividing it to t^2 matrices (t^2 is number of threads)
	public static int[][] multiplyT2threads(int[][] a, int[][] b, int n, int t) {
		int[][] c = new int[n][n];
		Thread [] Threads = new Thread[t*t];
		//creates t^2 threads. Each thread Calculates a sub-matrix Values and sets it to C matrix
		int num = 0;
        for (int i = 0; i<t; i++){
        	int row1 = (((i)*n)/t);
        	int row2 = (((i+1)*n)/t);
            for (int j = 0; j<t; j++){ 	
        	    int col1 = (((j)*n)/t);
        	    int col2 = (((j+1)*n)/t);
                Threads[num+j] = new Thread(new MulThread(row1,col1,row2,col2,a,b,c));
                Threads[num+j].start();
        	 }
            num += t;
            
        }
        
        for(int j = 0; j < t*t ; j++){
        	for (Thread thread : Threads) {
        		try {
        			thread.join(); // wait for the threads to terminate
        		} 
        		catch (InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }
        return c;
    }
	
	//multiply two matrices by dividing it to t matrices (t is number of threads)
	public static int[][] multiplyTthreads(int[][] a, int[][] b, int n, int t) {
		int[][] c = new int[n][n];
		Thread [] Threads = new Thread[t];
		//creates t threads. Each thread Calculates n/t rows and sets it to C matrix
        for (int i = 0; i<t; i++){
        	int start_line = i*(n/t);
        	int end_line = (i+1)*(n/t);
            Threads[i] = new Thread(new MulThread(start_line,0,end_line,n,a,b,c));
            Threads[i].start();
        
        }
        
        for(int j = 0; j < t ; j++){
        	for (Thread thread : Threads) {
        		try {
        			thread.join(); // wait for the threads to terminate
        		} 
        		catch (InterruptedException e){
        			e.printStackTrace();
        		}
        	}
        }
        return c;
    }
	
	//multiply two matrices using the Iterative algorithm with first loop in parallel
	public static int[][] multiplyParallelLoop(int[][] a, int[][] b, int n) {
        int[][] c = new int[n][n];
        IntStream.range(0, n).parallel().forEach(i->{
        	for (int j = 0; j < n; j++)
                for (int k = 0; k < n; k++)
                    c[i][j] += a[i][k] * b[k][j];
        });
        return c;
    }
	
	//multiply two matrices using the Iterative algorithm
		public static int[][] multiply(int[][] a, int[][] b, int n) {
	        int[][] c = new int[n][n];
	        for (int i = 0; i < n; i++)
	            for (int j = 0; j < n; j++)
	                for (int k = 0; k < n; k++)
	                    c[i][j] += a[i][k] * b[k][j];
	        return c;
	    }
	
	//returns a random n-by-n matrix with values between 1 and 50
    public static int[][] random( int n) {
        int[][] a = new int[n][n];
        Random rand = new Random();
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                a[i][j] = rand.nextInt(50) + 1;
        return a;
    }
    //return identity matrix
    public static int[][] identity(int n) {
        int[][] a = new int[n][n];
        for (int i = 0; i < n; i++)
            a[i][i] = 1;
        return a;
    }
    //prints matrix
    public static void printMatrix(int [][] a){
    	
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) 
            	System.out.print(a[i][j] + " ");
            System.out.println();
        }
        

    }
    //add operation for matrices
    public static int[][] add(int[][] a, int[][] b) {
        int m = a.length;
        int n = a[0].length;
        int[][] c = new int[m][n];
        for (int i = 0; i < m; i++)
            for (int j = 0; j < n; j++)
                c[i][j] = a[i][j] + b[i][j];
        return c;
    }

}



