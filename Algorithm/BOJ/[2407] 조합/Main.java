import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigInteger;
import java.util.StringTokenizer;

public class Main {
	private static BigInteger[][] combiDp;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	int n = Integer.parseInt(st.nextToken());
    	int m = Integer.parseInt(st.nextToken());
    	
    	combiDp = new BigInteger[n + 1][n + 1];
    	
    	computeCombination(n, m);
    	
    	System.out.println(combiDp[n][m]);
    }
    
    private static void computeCombination(int n, int m) {
    	for (int i = 1; i <= n; i++) {
    		for (int j = 0; j <= i; j++) {
    			if (i == j || j == 0) {
    				combiDp[i][j] = BigInteger.ONE;
    			} else {
    				combiDp[i][j] = combiDp[i - 1][j - 1].add(combiDp[i - 1][j]);
    			}
    		}
    	}
    }
}