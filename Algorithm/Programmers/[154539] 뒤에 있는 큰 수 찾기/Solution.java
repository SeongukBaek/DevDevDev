import java.util.*;

class Solution {
    public int[] solution(int[] numbers) {
        int size = numbers.length;
        int[] answer = new int[size];
        
        Arrays.fill(answer, -1);
        
        // 우선 제일 마지막 값을 최대값으로 저장
        int max = numbers[size - 1];
        for (int index = size - 2; index >= 0; index--) {
            int current = numbers[index];
            
            // 뒤에 있는 가장 큰 값보다 현재가 크거나 같은 경우, max 갱신
            if (current >= max) {
                max = current;
                continue;
            }
            
            // 뒤에 있는 가장 큰 값보다 현재가 작은 경우
            // 무조건 max로 갱신하면 안되는 경우가 존재함
            for (int back = index + 1; back < size; back++) {
                // 현재 숫자보다 뒤에 큰 수가 나오면 해당 숫자로 갱신
                if (current < numbers[back]) {
                    answer[index] = numbers[back];
                    break;
                }
                // 만약 뒤에 있는 수들의 뒷 큰수보다 현재가 작다면 해당 숫자로 갱신
                if (current < answer[back]) {
                    answer[index] = answer[back];
                    break;
                }
            }
        }
        
        return answer;
    }
}