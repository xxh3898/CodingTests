
import java.util.Scanner;

class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int a, b, c;
        int result = 0;
        int max = 0;
        a = sc.nextInt();
        b = sc.nextInt();
        c = sc.nextInt();

        if (a == b && a == c) {
            result = 10000 + a * 1000;
        } else if (a == b || a == c) {
            result = 1000 + a * 100;
        } else if (a == b || b == c) {
            result = 1000 + b * 100;
        } else {
            max = b;
            if (a >= b) {
                max = a;
            }
            if (max <= c) {
                max = c;
            }
            result = 100 * max;
        }

        System.out.print(result);
    }
}
