import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Deque;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
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
        
        int count = 0;
        if (numbers.size() == 1) {
        	System.out.println(count);
        	return;
        }
        
        numbers.sort(Comparator.naturalOrder());
        Deque<Integer> list = new ArrayDeque<>(numbers);
        
        while (list.size() >= 2) {
        	int sum = list.peekFirst() + list.peekLast();
        	if (sum == x) {
        		count++;
        		list.pollFirst();
        		list.pollLast();
        	} else if (sum < x) {
        		list.pollFirst();
        	} else {
        		list.pollLast();
        	}
        }
        
        System.out.println(count);
    }
}