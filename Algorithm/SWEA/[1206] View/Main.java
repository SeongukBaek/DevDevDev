import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

class Solution {
    static List<Integer> buildingHeights;
    static int buildings;
    static int[] left;
    static int[] right;

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        for (int test = 1; test <= 10; test++) {
            System.out.print("#" + test + " ");

            buildings = Integer.parseInt(br.readLine());
            buildingHeights = new ArrayList<>();
            left = new int[buildings];
            right = new int[buildings];

            String[] tokens = br.readLine().split(" ");

            for (int count = 0; count < buildings; count++) {
                buildingHeights.add(Integer.parseInt(tokens[count]));
            }

            fillLeft();
            fillRight();

            System.out.println(findView());
        }

        br.close();
    }

    private static void fillLeft() {
        for (int index = 2; index < buildings - 2; index++) {
            int prev = Math.max(buildingHeights.get(index - 2), buildingHeights.get(index - 1));
            int current = buildingHeights.get(index);

            if (current > prev) {
                left[index] = current - prev;
            }
        }
    }

    private static void fillRight() {
        for (int index = buildings - 3; index >= 2; index--) {
            int prev = Math.max(buildingHeights.get(index + 1), buildingHeights.get(index + 2));
            int current = buildingHeights.get(index);

            if (current > prev) {
                right[index] = current - prev;
            }
        }
    }

    private static int findView() {
        int view = 0;

        for (int index = 2; index < buildings - 2; index++) {
            view += Math.min(left[index], right[index]);
        }

        return view;
    }
}