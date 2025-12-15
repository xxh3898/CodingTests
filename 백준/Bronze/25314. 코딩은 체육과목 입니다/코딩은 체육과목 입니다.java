
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = sc.nextInt();
        if (n % 4 == 0) {
            n = n / 4;
        } else {
            n = n / 4 + 1;
        }
        for (int i = 0; i < n; i++) {
            System.out.print("long ");
        }
        System.out.println("int");
    }
}
