import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(System.out));

        String str = br.readLine();
        int count = 1;

        for (int i = 1; i < str.length() - 1; i++) {
            char s = str.charAt(i);

            if (s == ' ') {
                count++;
            }
        }

        if (str.length() == 1 && str.charAt(0) == ' ') {
            count = 0;
        }

        bw.write(count + "");
        br.close();
        bw.close();
    }
}
