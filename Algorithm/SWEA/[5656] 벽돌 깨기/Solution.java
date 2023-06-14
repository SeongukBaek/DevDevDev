import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.LinkedList;
import java.util.Queue;
import java.util.StringTokenizer;

class Solution {
	static class Bomb {
		int x;
		int y;
		int range;
		Bomb(int x, int y, int range) {
			this.x = x;
			this.y = y;
			this.range = range;
		}
	}
    private static int N;
    private static int W;
    private static int H;
    private static int minBlocks;
    private static int[][] map;

	public static void main(String[] args) throws IOException {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    int T = Integer.parseInt(br.readLine());
	    StringTokenizer st;
	    StringBuilder answer = new StringBuilder();
	
	    for (int test = 1; test <= T; test++) {
	        answer.append("#").append(test).append(" ");
	        st = new StringTokenizer(br.readLine());
	        N = Integer.parseInt(st.nextToken());
	        W = Integer.parseInt(st.nextToken());
	        H = Integer.parseInt(st.nextToken());
	        
	        map = new int[H][W];
	        minBlocks = Integer.MAX_VALUE;
	        
	        for (int x = 0; x < H; x++) {
	        	st = new StringTokenizer(br.readLine());
	        	for (int y = 0; y < W; y++) {
	        		map[x][y] = Integer.parseInt(st.nextToken());
	        	}
	        }
	        
	        tryBrickBreaker(copy(map), 0);
	
	        answer.append(minBlocks).append("\n");
	    }
	
	    System.out.print(answer);
	}
	
	private static int[][] copy(int[][] map) {
		int[][] temp = new int[H][W];
		for (int x = 0; x < H; x++) {
			for (int y = 0; y < W; y++) {
				temp[x][y] = map[x][y];
			}
		}
		return temp;
	}
	
	private static void tryBrickBreaker(int[][] tempMap, int count) {
		if (count == N) {
			minBlocks = Math.min(minBlocks, getRemainBlocks(tempMap));
			return;
		}
		
		for (int index = 0; index < W; index++) {
			tryBrickBreaker(moveBlocks(bomb(copy(tempMap), index)), count + 1);
		}
	}
	
	private static int[][] bomb(int[][] tempMap, int bombY) {
		Queue<Bomb> bombs = new LinkedList<>();
		bombs.add(findFirstBlock(tempMap, bombY));
		
		while (!bombs.isEmpty()) {
			Bomb currentBomb = bombs.poll();
			int x = currentBomb.x;
			int y = currentBomb.y;
			int bombRange = currentBomb.range;
			
			tempMap[x][y] = 0;
			//해당 폭탄의 범위에 있는 0이 아닌 애들을 큐에 다시 저장
			for (int range = 1; range < bombRange; range++) {
				// x축 범위 확인
				if (0 <= y - range && tempMap[x][y - range] != 0) {
					bombs.add(new Bomb(x, y - range, tempMap[x][y - range]));
				}
				if (y + range < W && tempMap[x][y + range] != 0) {
					bombs.add(new Bomb(x, y + range, tempMap[x][y + range]));
				}
				// y축 범위 확인
				if (0 <= x - range && tempMap[x - range][y] != 0) {
					bombs.add(new Bomb(x - range, y, tempMap[x - range][y]));
				}
				if (x + range < H && tempMap[x + range][y] != 0) {
					bombs.add(new Bomb(x + range, y, tempMap[x + range][y]));
				}
			}
		}
		
		return tempMap;
	}
	
	private static Bomb findFirstBlock(int[][] tempMap, int bombY) {
		for (int x = 0; x < H; x++) {
			if (tempMap[x][bombY] != 0) {
				return new Bomb(x, bombY, tempMap[x][bombY]);
			}
		}
		return new Bomb(H - 1, bombY, tempMap[H - 1][bombY]);
	}
	
	private static int[][] moveBlocks(int[][] tempMap) {
		Queue<Integer> blocks;
		for (int y = 0; y < W; y++) {
			// 하나의 열 기준으로 큐에 0이 아닌 블록들을 삽입
			int x;
			blocks = new LinkedList<>();
			for (x = H - 1; x >= 0; x--) {
				int block = tempMap[x][y];
				if (block != 0) {
					blocks.add(block);
					tempMap[x][y] = 0;
				}
			}
			
			// 하나의 열 기준으로 W - 1부터 큐에 있는 값을 poll해서 저장
			x = H - 1;
			while (!blocks.isEmpty()) {
				tempMap[x--][y] = blocks.poll();
			}
		}
		return tempMap;
	}
	
	private static int getRemainBlocks(int[][] tempMap) {
		int blocks = 0;
		for (int x = 0; x < H; x++) {
        	for (int y = 0; y < W; y++) {
        		if (tempMap[x][y] != 0) {
        			blocks++;
        		}
        	}
        }
		return blocks;
	}
}