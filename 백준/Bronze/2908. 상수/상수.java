
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

        StringTokenizer st = new StringTokenizer(br.readLine());
        String num1 = st.nextToken();
        String rnum1 = "";

        for (int i = 1; i <= num1.length(); i++) {
            rnum1 += num1.charAt(num1.length() - i);
        }

        String num2 = st.nextToken();
        String rnum2 = "";

        for (int i = 1; i <= num2.length(); i++) {
            rnum2 += num2.charAt(num2.length() - i);
        }

        int Num1 = Integer.parseInt(rnum1);
        int Num2 = Integer.parseInt(rnum2);

        if (Num1 > Num2) {
            bw.write("" + Num1);
        } else {
            bw.write("" + Num2);
        }

        br.close();
        bw.close();
    }
}
