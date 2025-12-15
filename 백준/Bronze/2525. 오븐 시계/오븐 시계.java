
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int h, m, plus;
        h = sc.nextInt();
        m = sc.nextInt();
        plus = sc.nextInt();

        if (plus >= 60) {
            h += plus / 60;
            m += plus % 60;
        } else {
            m += plus;
        }

        if (m >= 60) {
            h++;
            m = m % 60;
        }

        if (h >= 24) {
            h = h - 24;
        }

        System.out.printf("%d %d", h, m);
    }
}
