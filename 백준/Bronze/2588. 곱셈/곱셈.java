
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        double a = sc.nextDouble();
        double b = sc.nextDouble();

        System.out.println((int) (a * (b % 10)));
        System.out.println((int) ((a * ((b % 100) - (b % 100) % 10)) / 10));
        System.out.println((int) (a * (int) (b * 0.01)));
        System.out.println((int) (a * b));
    }
}
