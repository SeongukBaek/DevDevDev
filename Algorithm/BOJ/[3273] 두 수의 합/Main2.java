import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.StringTokenizer;

public class Main2 {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int N = Integer.parseInt(br.readLine());
        StringTokenizer st = new StringTokenizer(br.readLine());
        int x = Integer.parseInt(br.readLine());
        
        List<Integer> numbers = new ArrayList<>();
        for (int index = 0; index < N; index++) {
        	int number = Integer.parseInt(st.nextToken());
        	
        	if (number >= x) {
        		continue;
        	}
        	numbers.add(number);
        }
        
        numbers.sort(Comparator.naturalOrder());
        
        int count = 0;
        if (numbers.size() == 1) {
        	System.out.println(count);
        	return;
        }
        
        int left = 0;
        int right = numbers.size() - 1;
        
        while (left < right) {
        	int sum = numbers.get(left) + numbers.get(right);
        	if (sum == x) {
        		count++;
        		left++;
        		right--;
        	} else if (sum < x) {
        		left++;
        	} else {
        		right--;
        	}
        }
        
        System.out.println(count);
    }
}