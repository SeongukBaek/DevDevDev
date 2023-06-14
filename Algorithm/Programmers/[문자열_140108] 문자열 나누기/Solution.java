class Solution {
    private static int xCount;
    private static int otherCount;
    private static String s;
    private static int size;
    private static int answer = 0;

    public int solution(String s) {
        this.s = s;
        this.size = s.length();

        int index = 0;
        while (index < size) {
            char x = s.charAt(index);
            xCount = 1;
            otherCount = 0;

            index = searchString(x, index);

            // 더 이상 읽을 문자가 없는 경우
            if (index == size - 1) {
                answer++;
                break;
            }
        }

        return answer;
    }

    private static int searchString(char x, int index) {
        for (int next = index + 1; next < size; next++) {
            if (isSame(x, s.charAt(next))) {
                xCount++;
            } else {
                otherCount++;
            }

            if (xCount == otherCount) {
                answer++;
                return next + 1;
            }

            if (next == size - 1) {
                return next;
            }
        }

        return index;
    }

    private static boolean isSame(char x, char other) {
        return x == other;
    }
}