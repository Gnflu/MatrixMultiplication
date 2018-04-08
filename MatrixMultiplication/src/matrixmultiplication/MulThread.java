package matrixmultiplication;


public class MulThread implements Runnable {
	private int [][] A;
	private int [][] B;
	private int [][] C; //result matrix
	private int row1;
	private int col1;
	private int row2;
	private int col2;
	// each thread calculates a sub-matrix (row2 - row1)x(col2 - col1) of matrix C
	public void run(){
		for (int i = row1; i < row2; i++)
            for (int j = col1; j < col2; j++)
                for (int k = 0; k < A[0].length; k++)
                	C[i][j] += A[i][k] * B[k][j];
	}
	// MulThread constructor
    public MulThread(int row1, int col1, int row2, int col2, int [][] A, int [][] B, int [][] C){
    	this.row1 = row1;
    	this.col1 = col1;
    	this.row2 = row2;
    	this.col2 = col2;
		this.A = A;
		this.B = B;
		this.C = C;
	}

}
