import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

public class Main {
    // 상 우 하 좌
    private static final int[][] moves = {{-1, 0}, {0, 1}, {1, 0}, {0, -1}};
    private static final int DISTANCE = 3;
    // 도망자 저장
    private static int[][][] runners;
    // 나무 정보 저장, 있다 없다만 저장
    private static boolean[][] isTree;
    // 술래 이동 방향 저장
    private static int[] taggerDirection;
    private static int taggerX;
    private static int taggerY;
    private static int taggerD;
    private static int taggerDirectionIndex;

    private static int N;
    
    private static int score;

    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st = new StringTokenizer(br.readLine());
    	
    	N = Integer.parseInt(st.nextToken());
    	int m = Integer.parseInt(st.nextToken());
    	int h = Integer.parseInt(st.nextToken());
    	int k = Integer.parseInt(st.nextToken());
    	
    	runners = new int[N][N][4];
    	isTree = new boolean[N][N];
    	taggerX = N / 2;
    	taggerY = N / 2;
    	
    	for (int index = 0; index < m; index++) {
    		st = new StringTokenizer(br.readLine());
    		
    		int x = Integer.parseInt(st.nextToken()) - 1;
    		int y = Integer.parseInt(st.nextToken()) - 1;
    		int d = Integer.parseInt(st.nextToken());

			runners[x][y][d]++;
    	}

    	for (int index = 0; index < h; index++) {
    		st = new StringTokenizer(br.readLine());
    		
    		int x = Integer.parseInt(st.nextToken()) - 1;
    		int y = Integer.parseInt(st.nextToken()) - 1;

			isTree[x][y] = true;
    	}
    	
    	// 달팽이 방향으로 이동하기 위한 방향 배열 생성
    	makeTaggerDirection();
    	    	
    	for (int turn = 1; turn <= k; turn++) {
    		// 도망자 이동
            // 술래랑 거리를 저장해야 함. -> 이동 시 계산할 지, 술래 이동 후 저장할지 생각
            // 격자 밖으로 나가는 경우
            // - 술래가 없어야 이동, 있으면 이동 불가

            // 격자 밖으로 나가지 않는 경우
            // - 방향을 반대로 ! 0 <-> 2, 1 <-> 3
            // - 술래가 없으면 이동, 있으면 이동 불가
    		moveRunners();

            // 술래 이동
            // - 방향 설정을 어떻게 할 것인가?
            // - 이동 후, 방향을 틀어야하는 지점이면 틀기
    		moveTagger();

            // 도망자 잡기
            // - 나무가 있는 지점은 확인 X, 나무가 없고 범위 내라면 잡기
            // 점수 계산
            // - 턴 * 이번에 잡은 도망자 수
    		computeScore(catchRunners(), turn);
    	}
    	
    	System.out.println(score);
    }

    private static void makeTaggerDirection() {
    	int size = N * N - 1;
        taggerDirection = new int[size * 2];
        
        int zero = 1;
        int one = 1;
        int two = 2;
        int three = 2;
        
        int index = 0;
        while (index < size) {
        	int temp = 0;
        	while (temp++ < zero && index < size) {
        		taggerDirection[index++] = 0;
        	}
        	temp = 0;
        	while (temp++ < one && index < size) {
        		taggerDirection[index++] = 1;
        	}
        	temp = 0;
        	while (temp++ < two && index < size) {
        		taggerDirection[index++] = 2;
        	}
        	temp = 0;
        	while (temp++ < three && index < size) {
        		taggerDirection[index++] = 3;
        	}
        	
        	zero += 2;
        	one += 2;
        	two += 2;
        	three += 2;
        }
        
        int prev = index;
        for (; index < size * 2; index++) {
            taggerDirection[index] = rotateOppoosite(taggerDirection[--prev]);
        }
    }

    // 반대 방향 반환
    private static int rotateOppoosite(int d) {
        if (d > 1) {
        	return d - 2;
        }
        return d + 2;
    }
    
    private static void moveRunners() {
    	List<int[]> moveInfo = new ArrayList<>();
    	
    	for (int x = 0; x < N; x++) {
    		for (int y = 0; y < N; y++) {
    			if (computeDistance(x, y, taggerX, taggerY) > DISTANCE) {
    				continue;
    			}
    			
    			for (int d = 0; d < 4; d++) {
        			if (runners[x][y][d] == 0) {
        				continue;
        			}

        			int nx = x + moves[d][0];
        			int ny = y + moves[d][1];
        			int nd = d;
        			
        			// 격자 밖이라면 반대 방향으로 돌려서 이동
        			if (!isIn(nx, ny)) {
        				nd = rotateOppoosite(d);
        				
        				nx = x + moves[nd][0];
        				ny = y + moves[nd][1];
        			}
        			
        			// 만약 이동한 위치에 술래가 있다면, 이동 불가
        			// runners 갱신을 위해 기존 좌표를 저장
        			if (isTagger(nx, ny)) {
        				moveInfo.add(new int[] {x, y, d, x, y, nd, runners[x][y][d]});
        				continue;
        			}

        			// 술래가 없다면 이동이 가능하므로 새로운 좌표를 저장
    				moveInfo.add(new int[] {x, y, d, nx, ny, nd, runners[x][y][d]});
    			}
    		}
    	}
    	
    	for (int[] move : moveInfo) {
    		runners[move[0]][move[1]][move[2]] -= move[6];
    		runners[move[3]][move[4]][move[5]] += move[6];
    	}
    }

    private static int computeDistance(int runnerX, int runnerY, int taggerX, int taggerY) {
        return Math.abs(runnerX - taggerX) + Math.abs(runnerY - taggerY);
    }
    
    private static boolean isIn(int x, int y) {
    	return x >= 0 && x < N && y >= 0 && y < N;
    }
    
    private static boolean isTagger(int x, int y) {
    	return taggerX == x && taggerY == y;
    }
    
    private static void moveTagger() {
		taggerX += moves[taggerD][0];
		taggerY += moves[taggerD][1];
		
		taggerDirectionIndex++;
		
		if (taggerDirectionIndex == taggerDirection.length) {
			taggerDirectionIndex = 0;
		}
		
		// 이동 후 방향 갱신
		taggerD = taggerDirection[taggerDirectionIndex];
    }
    
    private static int catchRunners() {
    	int count = 0;
    	
    	for (int distance = 0; distance < DISTANCE; distance++) {
    		int nx = taggerX + moves[taggerD][0] * distance;
    		int ny = taggerY + moves[taggerD][1] * distance;
    		
    		if (!isIn(nx, ny) || isTree[nx][ny]) {
    			continue;
    		}
    		
    		for (int d = 0; d < 4; d++) {
    			count += runners[nx][ny][d];
    			runners[nx][ny][d] = 0;
    		}
    	}
    	
    	return count;
    }
    
    private static void computeScore(int count, int turn) {
    	score += turn * count;
    }
}