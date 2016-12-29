import java.util.*;

public class WordLadder {
    public static int ladderLength(String start, String end,
        HashSet<String> dict) {
        int result = 0;
        if (dict.size() == 0) {
            return result;
        }

        dict.add(start);
        dict.add(end);

        result = BFS(start, end, dict);

        return result;
    }

    private static int BFS(String start, String end, HashSet<String> dict) {
        Queue<String> queue = new LinkedList<String>();
        Queue<Integer> length = new LinkedList<Integer>();
        queue.add(start);
        length.add(1);

        while (!queue.isEmpty()) {
            String word = queue.poll();
            int len = length.poll();

            if (match(word, end)) {
                return len;
            }

            for (int i = 0; i < word.length(); i++) {
                char[] arr = word.toCharArray();
                for (char c = 'a'; c <= 'z'; c++) {
                    if (c == arr[i])
                        continue;

                    arr[i] = c;
                    String str = String.valueOf(arr);
                    if (dict.contains(str)) {
                        queue.add(str);
                        length.add(len + 1);
                        dict.remove(str);
                    }
                }
            }
        }

        return 0;
    }

    private static boolean match(String word, String end) {
        if (word.equals(end)) {
            return true;
        }
        return false;
    }
}
