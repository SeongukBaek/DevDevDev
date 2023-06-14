import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Main {	
	private static int N;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	N = Integer.parseInt(br.readLine());
    	List<List<Integer>> children = new ArrayList<>();
    	for (int index = 0; index < N; index++) {
    		children.add(new ArrayList<>());
    	}
    	
    	int[] parents = Arrays.stream(br.readLine().split(" ")).mapToInt(Integer::parseInt).toArray();
    	
    	for (int index = 0; index < N; index++) {
    		if (parents[index] == -1) {
    			continue;
    		}
    		
    		children.get(parents[index]).add(index);
    	}

    	int deletion = Integer.parseInt(br.readLine());
    	
    	Queue<Integer> deleteNodes = new LinkedList<>();
    	deleteNodes.add(deletion);
    	while (!deleteNodes.isEmpty()) {
    		int delete = deleteNodes.poll();
    		for (int child : children.get(delete)) { 
        		deleteNodes.add(child);
    		}
    		children.get(delete).clear();
    		children.get(delete).add(-1);
    	}
    	
    	int count = 0;
    	for (int index = 0; index < children.size(); index++) {
    		for (int child = 0; child < children.get(index).size(); child++) {
    			if (children.get(index).get(child) == deletion) {
    				children.get(index).remove((Integer) deletion);
    			}
    		}
    		if (children.get(index).size() == 0) {
    			count++;
    		}
    	}
    	
    	System.out.println(count);
    }
}