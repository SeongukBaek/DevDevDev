import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

public class Main {
	private static int minLength = 100001;
	private static int N;
	private static int S;
	private static int[] numbers;

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int[] line = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        N = line[0];
        S = line[1];
        
        numbers = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
        
        findMinLength();
        
        System.out.println(getAnswer());
    }
    
    private static void findMinLength() {
    	int left = 0;
    	int right = 0;
    	int sum = numbers[right];
    	
    	while (left <= right) {
    		if (sum >= S) {
    			minLength = Math.min(minLength, right - left + 1);
    			sum -= numbers[left++];
    			continue;
    		}
    		
    		if (right + 1 == N) {
    			break;
    		}
    		sum += numbers[++right];
    	}
    }
    
    private static int getAnswer() {
    	if (minLength == 100001) {
        	return 0;
        }
        return minLength;
    }
}