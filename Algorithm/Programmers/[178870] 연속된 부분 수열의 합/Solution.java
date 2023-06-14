class Solution {
    // 이진 탐색으로, 부분 수열의 시작 가능한 인덱스를 먼저 찾는다.
    // k / 2 >= middle인 경우 시작 인덱스
    // 만약 k / 2 < middle이면 right를 줄여서 다시 이진 탐색
    // 이후 투 포인터로 범위 찾기
    
    public int[] solution(int[] sequence, int k) {
        int half = k / 2;
        int left = 0;
        int right = sequence.length - 1;
        
        while (left < right) {
            int mid = (left + right) / 2;
            
            if (sequence[mid] == half) {
            	left = mid;
            	right = mid;
                break;
            }
            right = mid;
        }
        
        int answerLeft = 0;
        int answerRight = sequence.length;
        int sum = sequence[left];
        while (left <= right && right < sequence.length) {
        	if (sum < k) {
        		if (right == sequence.length - 1) {
        			break;
        		}
        		sum += sequence[++right];
        		continue;
        	}
        	if (sum > k) {
        		sum -= sequence[left++];
        		continue;
        	}
        	if (sum == k) {
        		if (answerRight - answerLeft > right - left) {
            		answerLeft = left;
            		answerRight = right;
        		}
        		sum -= sequence[left++];
        	}
        }
        
        return new int[] {answerLeft, answerRight};
    }
}