package matrixmultiplication;

import org.junit.Assert;
import org.junit.Test;

public class TestJunit {
	public static int n = 500; //dimension
	public static int t = 2; //number of threads
   //test correctness of multiplication with threads (multiplyTthreads function)
   @Test
   public void testSetup() {
	   int[][] a = MatrixMultiplication.random(n);
	   int[][] b = MatrixMultiplication.random(n);
	   int[][] c1 = new int[n][n];
	   int[][] c2 = new int[n][n];
	   c1 = MatrixMultiplication.multiply(a, b, n);
	   c2 = MatrixMultiplication.multiplyTthreads(a, b, n, t);
     
	   Assert.assertArrayEquals(c1,c2);
   }
   //test Multiplicative identity property AI=A
   @Test
   public void testIdentityMul() {
	   int[][] a = MatrixMultiplication.random(n);
	   int[][] b = MatrixMultiplication.identity(n);
	   int[][] c = new int[n][n];
	   c = MatrixMultiplication.multiplyTthreads(a, b, n, t);
     
	   Assert.assertArrayEquals(a,c);
   }
   //test Multiplicative property of zero AO=O
   @Test
   public void testZeroMul() {
	   int[][] a = MatrixMultiplication.random(n);
	   int[][] b = new int[n][n];
	   int[][] c = new int[n][n];
	   c = MatrixMultiplication.multiplyTthreads(a, b, n, t);
     
	   Assert.assertArrayEquals(b,c);
   }
   //test Associative Property (AB)C=A(BC)
   @Test
   public void testAssociativeProperty() {
	   int[][] a = MatrixMultiplication.random(n);
	   int[][] b = MatrixMultiplication.random(n);
	   int[][] c = MatrixMultiplication.random(n);
	   int[][] d1 = new int[n][n];
	   int[][] d2 = new int[n][n];
	   d1 = MatrixMultiplication.multiplyTthreads(a, b, n, t);
	   d1 = MatrixMultiplication.multiplyTthreads(d1, c, n, t);
	   d2 = MatrixMultiplication.multiplyTthreads(b, c, n, t);
	   d2 = MatrixMultiplication.multiplyTthreads(a, d2, n, t);
     
	   Assert.assertArrayEquals(d1,d2);
   }
   //test Distributive Property A(B+C)=AB+AC
   @Test
   public void testDistributiveProperty() {
	   int[][] a = MatrixMultiplication.random(n);
	   int[][] b = MatrixMultiplication.random(n);
	   int[][] c = MatrixMultiplication.random(n);
	   int[][] d1 = new int[n][n];
	   int[][] d2 = new int[n][n];
	   int[][] d3 = new int[n][n];
	   d1 = MatrixMultiplication.multiplyTthreads(a, b, n, t);
	   d3 = MatrixMultiplication.multiplyTthreads(a, c, n, t);
	   d1 = MatrixMultiplication.add(d1, d3);
	   d2 = MatrixMultiplication.add(b, c);
	   d2 = MatrixMultiplication.multiplyTthreads(a, d2, n, t);
     
	   Assert.assertArrayEquals(d1,d2);
   }
}
