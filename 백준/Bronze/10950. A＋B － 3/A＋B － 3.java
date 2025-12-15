
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);
        int t = sc.nextInt();
        int i = 0;
        while (i < t) {
            int a = sc.nextInt();
            int b = sc.nextInt();
            System.out.println(a + b);
            i++;
        }
    }
}
