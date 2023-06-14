class Solution {
    private static final String TARGET = "1";

    public int[] solution(String s) {
        int count = 0;
        int zeroCount = 0;

        while (!s.equals(TARGET)) {
            int removedLength = removeZero(s);
            int length = s.length();

            zeroCount += length - removedLength;
            s = intToBinaryString(removedLength);

            count++;
        }

        return new int[]{count, zeroCount};
    }

    private static int removeZero(String s) {
        int length = 0;
        for (int index = 0; index < s.length(); index++) {
            if (s.charAt(index) != '0') {
                length++;
            }
        }
        return length;
    }

    private static String intToBinaryString(int number) {
        StringBuilder result = new StringBuilder();

        while (number >= 1) {
            result.append(number % 2);
            number /= 2;
        }

        return result.reverse().toString();
    }
}