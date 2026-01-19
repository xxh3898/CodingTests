
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.StringTokenizer;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));
        StringTokenizer st;
        while (true) {
            String reesult = "";
            st = new StringTokenizer(br.readLine());
            int a = Integer.parseInt(st.nextToken());
            int b = Integer.parseInt(st.nextToken());
            int c = Integer.parseInt(st.nextToken());
            if (a == 0 && b == a && c == a) {
                bw.close();
                br.close();
                return;
            }
            if (a == b && b == c && c == a) {
                reesult = "Equilateral";
                bw.write(reesult + "\n");
            } else if (Math.max(a, Math.max(c, b)) >= (a + b + c) - Math.max(a, Math.max(c, b))) {
                reesult = "Invalid";
                bw.write(reesult + "\n");
            } else if (a != b && b != c && c != a) {
                reesult = "Scalene";
                bw.write(reesult + "\n");
            } else {
                reesult = "Isosceles";
                bw.write(reesult + "\n");
            }
        }
    }
}
