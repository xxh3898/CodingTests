
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int a = 2;

        for (int i = 0; i < n; i++) {
            a = a + (int) Math.pow(2, i);
        }

        System.out.println(a * a);
    }
}
