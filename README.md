# MatrixMultiplication
<h4>Matrix multiplication program using java concurrancy </h4>
<div>This program implements 4 algorithms for square matrix mutiplication:</div>
<div>1) Iterative algorithm</div>
<div>2) Iterative algorithm with outer loop in parallel</div>
<div>partitioning algorithms: (nxn matrix)</div>
<div>3) Dividing each matrix to t matrices (t is number of threads) - each thread calculates n/t rows </div>
<div>4) Dividing each matrix to t^2 matrices (t^2 is number of threads) - each thread calculates n^2/t^2 values</div>
<div>The porpose of this application is to implement an efficient algotithm for matrix multiplication using Java concurrency.</div>
<div>The conclusion - there is no significant improvment in performance for t bigger than 2 (t is #threads and #processors).</div>
