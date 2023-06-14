class Solution {
    private static int[][] dp;

    public int solution(int[][] board) {
        int row = board.length;
        int col = board[0].length;
        this.dp = new int[row][col];

        int maxSize = 0;
        for (int x = 0; x < row; x++) {
            for (int y = 0; y < col; y++) {
                if (x == 0 || y == 0) {
                    dp[x][y] = board[x][y];
                } else if (board[x][y] == 1) {
                    dp[x][y] = checkAround(x, y);
                }
                maxSize = Math.max(maxSize, dp[x][y]);
            }
        }

        return maxSize * maxSize;
    }

    private static int checkAround(int x, int y) {
        int one = dp[x - 1][y - 1];
        int two = dp[x - 1][y];
        int three = dp[x][y - 1];

        if (one == two && two == three) {
            return one + 1;
        }

        return Math.min(Math.min(one, two), three) + 1;
    }
}