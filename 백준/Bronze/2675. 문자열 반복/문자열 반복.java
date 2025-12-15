
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

        int n = Integer.parseInt(br.readLine());
        StringTokenizer st;

        for (int i = 0; i < n; i++) {
            st = new StringTokenizer(br.readLine());

            int N = Integer.parseInt(st.nextToken());
            String str = st.nextToken();

            int l = str.length();

            for (int k = 0; k < l; k++) {
                for (int j = 0; j < N; j++) {
                    bw.write(str.charAt(k));
                }
            }
            bw.write("\n");
        }
        br.close();
        bw.close();
    }
}
