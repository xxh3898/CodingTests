
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
        int n = Integer.parseInt(st.nextToken());
        String s = "";

        for (int i = 0; i < n; i++) {
            s = br.readLine();
            int l = s.length();

            if (s.length() == 1) {
                bw.write("" + s + s + "\n");
            } else {
                bw.write("" + s.charAt(0) + s.charAt(l - 1) + "\n");
            }
        }
        bw.close();
        br.close();
    }
}
