import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;

public class Main {
	private static final int SIZE = 9;
	private static final int SQUARE_SIZE = 3;
	private static int[][] board = new int[SIZE][SIZE];
	private static boolean isDone;
	
    public static void main(String[] args) throws IOException {
    	BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    	StringTokenizer st;
    	
    	for (int x = 0; x < SIZE; x++) {
			st = new StringTokenizer(br.readLine());
    		for (int y = 0; y < SIZE; y++) {
    			board[x][y] = Integer.parseInt(st.nextToken());
    		}
    	}

    	fillBoard(0, 0);
    }
    
    private static void fillBoard(int row, int col) {
    	if (isDone) {
    		return;
    	}

		// 다음 0 위치 찾아서 재귀
		int[] next = findZero(row);
		// 만약 0이 더 없다면, 스도쿠를 다 채운 상태이므로 종료
		if (next[0] == -1) {
	    	print();
			isDone = true;
			return;
		}
    	
    	for (int number = 1; number <= SIZE; number++) {
    		// 해당 행, 열에 해당 숫자를 넣을 수 있는지 확인
    		// 해당 네모에 해당 숫자를 넣을 수 있는지 확인
    		if (!hasSameInRow(next[0], number) && !hasSameInCol(next[1], number) && !hasSameInSquare(next[0], next[1], number)) {
    			board[next[0]][next[1]] = number;
    			fillBoard(next[0], next[1]);
    			board[next[0]][next[1]] = 0;
    		}
    	}
    }
    
    private static int[] findZero(int row) {
    	for (int x = row; x < SIZE; x++) {
    		for (int y = 0; y < SIZE; y++) {
    			if (board[x][y] == 0) {
    				return new int[] {x, y};
    			}
    		}
    	}
    	return new int[] {-1, -1};
    }
    
    private static boolean hasSameInRow(int row, int number) {
    	for (int y = 0; y < SIZE; y++) {
    		if (board[row][y] == number) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private static boolean hasSameInCol(int col, int number) {
    	for (int x = 0; x < SIZE; x++) {
    		if (board[x][col] == number) {
    			return true;
    		}
    	}
    	return false;
    }
    
    private static boolean hasSameInSquare(int row, int col, int number) {
    	int squareX = findIndex(row);
    	int squareY = findIndex(col);
    	
    	for (int x = squareX; x < squareX + SQUARE_SIZE; x++) {
    		for (int y = squareY; y < squareY + SQUARE_SIZE; y++) {
    			if (board[x][y] == number) {
    				return true;
    			}
    		}
    	}
    	return false;
    }
    
    private static int findIndex(int index) {
    	return index / SQUARE_SIZE * SQUARE_SIZE;
    }
    
    private static void print() {
    	StringBuilder answer = new StringBuilder();
    	for (int x = 0; x < SIZE; x++) {
    		for (int y = 0; y < SIZE; y++) {
    			answer.append(board[x][y]).append(" ");
    		}
    		answer.append("\n");
    	}
    	System.out.println(answer);
    }
}