package diary.diaryApp.baek;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 숫자의 개수 [2577]
 */
public class Main {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        int a = Integer.parseInt(br.readLine());
        int b = Integer.parseInt(br.readLine());
        int c = Integer.parseInt(br.readLine());

        int[] digit = new int[10];

        long result = a * b * c;
        String re_result = String.valueOf(result);

        for (int i = 0; i < re_result.length(); i++) {
            char ch = re_result.charAt(i);
            int num = ch - '0';
            digit[num]++;
        }

        for (int i : digit) {
            System.out.println(i);
        }
    }
}
