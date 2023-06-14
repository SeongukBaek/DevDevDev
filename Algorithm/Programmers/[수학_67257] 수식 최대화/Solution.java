import java.util.*;

class Solution {
    long answer = 0;
    ArrayList<Long> nums = new ArrayList<>();
    ArrayList<Character> perm = new ArrayList<>();
    StringBuilder operands = new StringBuilder();
    
    public long solution(String expression) {
        StringBuilder tmp = new StringBuilder();
        
        for (int i = 0; i < expression.length(); i++) {
            char ch = expression.charAt(i);
            if (48 <= ch && ch <= 57) tmp.append(ch); 
            else {
                nums.add((long) Integer.parseInt(String.valueOf(tmp)));
                if (!perm.contains(ch)) perm.add(ch);
                operands.append(ch);
                tmp.delete(0, tmp.length());
            }
        }
        nums.add((long) Integer.parseInt(String.valueOf(tmp)));
        
        // 순열 생성
        makePerm(0, perm.size(), perm.size());
        
        return answer;
    }
    
    private void makePerm(int depth, int n, int r) {
        if (depth == r) {
            compute(n);
            return;
        }

        for (int i = depth; i < n; i++) {
            Collections.swap(perm, depth, i);
            makePerm(depth + 1, n, r);
            Collections.swap(perm, depth, i);
        }
    }
    
    private void compute(int n) {
        ArrayList<Long> numbers = new ArrayList<>(nums);
        // 연산자 임시 배열이 필요
        // string은 각 문자별 위치 조회가 가능하지만, 요소 삭제가 안됨
        // arrayList는 요소 삭제가 용이하지만, 위치 조회가 안됨
        StringBuilder tmpOps = new StringBuilder(operands);

        for (char p : perm) {
            int idx = tmpOps.indexOf(String.valueOf(p));
            while(idx != -1) {
                long n1 = numbers.get(idx);
                long n2 = numbers.get(idx+1);

                numbers.set(idx, calculator(n1,n2,p));
                numbers.remove(idx+1);
                tmpOps.deleteCharAt(idx);

                idx = tmpOps.indexOf(String.valueOf(p));
            }
        }

        long sum = Math.abs(numbers.get(0));
        if (answer < sum) answer = sum;
    }
    
    private long calculator(long n1, long n2, char op) {
        return switch (op) {
            case '-' -> n1 - n2;
            case '+' -> n1 + n2;
            case '*' -> n1 * n2;
            default -> 0;
        };
    }
}