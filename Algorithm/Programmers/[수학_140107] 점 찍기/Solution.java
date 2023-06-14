class Solution {
    public long solution(int k, int d) {
        long answer = 0;
        long distance = squareInt(d);

        int x = 0;
        long squareX = squareInt(x);
        while (distance >= squareX) {
            long diff = distance - squareX;
            int maxY = (int) Math.sqrt(diff);

            answer += maxY / k + 1;

            x += k;
            squareX = squareInt(x);
        }

        return answer;
    }

    private static long squareInt(int n) {
        return (long) n * n;
    }
}