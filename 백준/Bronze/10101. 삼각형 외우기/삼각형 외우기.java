
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String reesult = "";
        int a = Integer.parseInt(br.readLine());
        int b = Integer.parseInt(br.readLine());
        int c = Integer.parseInt(br.readLine());

        if (a == 60 && b == a && c == a) {
            reesult = "Equilateral";
        } else if (a + b + c != 180) {
            reesult = "Error";
        } else if (a != b && b != c && c != a) {
            reesult = "Scalene";
        } else {
            reesult = "Isosceles";
        }

        bw.write(reesult);
        bw.close();
        br.close();
    }
}
