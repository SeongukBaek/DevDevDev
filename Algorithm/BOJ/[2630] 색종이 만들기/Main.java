import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.StringTokenizer;

public class Main {	
	private static int[][] map;
	private static int whitePaper;
	private static int bluePaper;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st;
    	int N = Integer.parseInt(br.readLine());
    	map = new int[N][N];
    	
    	for (int x = 0; x < N; x++) {
    		st = new StringTokenizer(br.readLine());
    		for (int y = 0; y < N; y++) {
    			map[x][y] = Integer.parseInt(st.nextToken());
    		}
    	}
    	
    	dividePaper(0, 0, N);
    	
    	System.out.println(whitePaper);
    	System.out.println(bluePaper);
    }
    
    private static void dividePaper(int x, int y, int size) {
    	if (isSameColor(x, y, size)) {
    		int color = map[x][y];
    		
    		if (color == 1) {
    			bluePaper++;
    		} else {
    			whitePaper++;
    		}
    		
    		return;
    	}
    	
    	dividePaper(x, y, size / 2);
    	dividePaper(x + size / 2, y, size / 2);
    	dividePaper(x, y + size / 2, size / 2);
    	dividePaper(x + size / 2, y + size / 2, size / 2);
    }
    
    private static boolean isSameColor(int startX, int startY, int size) {
    	int color = map[startX][startY];
    	
    	for (int x = startX; x < startX + size; x++) {
    		for (int y = startY; y < startY + size; y++) {
    			if (map[x][y] != color) {
    				return false;
    			}
    		}
    	}
    	
    	return true;
    }
}