import java.util.Set;
import java.util.HashSet;

class Solution {
    static Set<String> phoneMap = new HashSet<>();

    public boolean solution(String[] phone_book) {
        for (String phone : phone_book) {
            phoneMap.add(phone);
        }

        for (int index = 0; index < phone_book.length; index++) {
            for (int range = 1; range < phone_book[index].length(); range++) {
                String compareString = phone_book[index].substring(0, range);
                if (phoneMap.contains(compareString)) {
                    return false;
                }
            }
        }

        return true;
    }
}