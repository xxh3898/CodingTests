
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        String n = sc.next();
        int b = sc.nextInt();

        int result = 0;
        int power = 1;

        for (int i = n.length() - 1; i >= 0; i--) {
            char c = n.charAt(i);
            int num;

            if (c >= '0' && c <= '9') {
                num = c - '0';
            } else {
                num = c - 'A' + 10;
            }

            result += num * power;

            power *= b;
        }

        System.out.println(result);
        sc.close();
    }
}
