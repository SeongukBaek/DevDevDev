import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
	static class Location {
		int x;
		int y;
		Location(int x, int y) {
			this.x = x;
			this.y = y;
		}
	}
	private static char[][] map;
	private static int N;
	private static int M;
	private static final String GAME_OVER = "GAME OVER";
	private static Queue<Location> devils;
	private static int[][] directions = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};

    public static void main(String[] args) throws IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		StringTokenizer st;
		StringBuilder answer = new StringBuilder();
		
		for (int test = 1; test <= T; test++) {
			st = new StringTokenizer(br.readLine());
			N = Integer.parseInt(st.nextToken());
			M = Integer.parseInt(st.nextToken());
			devils = new LinkedList<>();
			
			int startX = 0;
			int startY = 0;
			
			map = new char[N][M];
			for (int x = 0; x < N; x++) {
				String line = br.readLine();
				for (int y = 0; y < M; y++) {
					char current = line.charAt(y);
					
					if (current == 'S') {
						startX = x;
						startY = y;
					}
					
					if (current == '*') {
						devils.add(new Location(x, y));
					}
					
					map[x][y] = current;
				}
			}
			
			answer.append("#").append(test).append(" ");
			int time = moveToAngel(startX, startY);
			if (time == -1) {
				answer.append(GAME_OVER);
			} else {
				answer.append(time);
			}
			answer.append("\n");
		}
		
		System.out.print(answer);
	}
    
    private static int moveToAngel(int startX, int startY) {
    	int time = 1;
    	Queue<Location> suyeon = new LinkedList<>();
    	suyeon.add(new Location(startX, startY));
    	
    	while (time <= N * M) {
        	// 수연 이동해보기
    		int suyeonCount = suyeon.size();
    		if (suyeonCount == 0) {
    			return -1;
    		}
    		
    		boolean isMove = false;
    		for (int count = 0; count < suyeonCount; count++) {
    			Location currentSuyeon = suyeon.poll();
        		int x = currentSuyeon.x;
        		int y = currentSuyeon.y;
            	
            	if (map[x][y] == '*') {
            		continue;
            	}
        		
        		for (int[] direction : directions) {
        			int nx = x + direction[0];
        			int ny = y + direction[1];
        			
        			if (!isInBoundary(nx, ny) || map[nx][ny] == 'X' || map[nx][ny] == '*' || map[nx][ny] == 'S') {
        				continue;
        			}
        			
        			if (map[nx][ny] == 'D') {
        				return time;
        			}
        			
        			isMove = true;
        			map[nx][ny] = 'S';
        			suyeon.add(new Location(nx, ny));
        		}
    		}
    		if (isMove) {
    			time++;
    		}
    		
    		// 악마 확장
        	int devilCount = devils.size();
        	for (int count = 0; count < devilCount; count++) {
        		Location currentDevil = devils.poll();
        		int x = currentDevil.x;
        		int y = currentDevil.y;
        		
        		for (int[] direction : directions) {
        			int nx = x + direction[0];
        			int ny = y + direction[1];
        			
        			if (!isInBoundary(nx, ny) || map[nx][ny] == 'X' || map[nx][ny] == '*' || map[nx][ny] == 'D') {
        				continue;
        			}
        			
        			map[nx][ny] = '*';
        			devils.add(new Location(nx, ny));
        		}
         	}
    	}
    	
    	return time;
    }
    
    private static boolean isInBoundary(int x, int y) {
    	return x >= 0 && x < N && y >= 0 && y < M;
    }
}