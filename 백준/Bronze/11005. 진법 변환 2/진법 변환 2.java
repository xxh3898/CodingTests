
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        StringBuilder sb = new StringBuilder();

        int n = sc.nextInt();
        int b = sc.nextInt();

        String result = "";

        while (n > 0) {
            if (n % b >= 0 && n % b <= 9) {
                sb.append((char) ('0' + n % b));
            } else {
                sb.append((char) ('A' + (n % b - 10)));
            }

            n /= b;
        }

        System.out.println(sb.reverse());
    }
}
