import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Solution {
    static List<Integer> boxes;
    static int[] box;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int test = 1; test <= 10; test++) {
            System.out.print("#" + test + " ");

            int dump = Integer.parseInt(br.readLine());
            boxes = new ArrayList<>();

            String[] tokens = br.readLine().split(" ");

            for (int count = 0; count < 100; count++) {
                boxes.add(Integer.parseInt(tokens[count]));
            }

            System.out.println(doDump(dump));
        }

        br.close();
    }

    private static int doDump(int dump) {
        int difference = 0;

        while (dump > 0) {
            Collections.sort(boxes, Collections.reverseOrder());

            int maxHeight = boxes.get(0);
            maxHeight--;
            boxes.set(0, maxHeight);

            int minHeight = boxes.get(boxes.size() - 1);
            minHeight++;
            boxes.set(boxes.size() - 1, minHeight);

            Collections.sort(boxes, Collections.reverseOrder());

            maxHeight = boxes.get(0);
            minHeight = boxes.get(boxes.size() - 1);

            difference = maxHeight - minHeight;

            dump--;
        }

        return difference;
    }
}