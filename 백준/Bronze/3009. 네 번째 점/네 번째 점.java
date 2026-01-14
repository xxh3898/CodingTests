
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

        int X = 0, Y = 0;

        int x1 = Integer.parseInt(st.nextToken());
        int y1 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int x2 = Integer.parseInt(st.nextToken());
        int y2 = Integer.parseInt(st.nextToken());

        st = new StringTokenizer(br.readLine());

        int x3 = Integer.parseInt(st.nextToken());
        int y3 = Integer.parseInt(st.nextToken());

        if (x1 != x2 && x1 != x3) {
            X = x1;
        }
        if (x2 != x1 && x2 != x3) {
            X = x2;
        }
        if (x3 != x1 && x3 != x2) {
            X = x3;
        }

        if (y1 != y2 && y1 != y3) {
            Y = y1;
        }
        if (y2 != y1 && y2 != y3) {
            Y = y2;
        }
        if (y3 != y1 && y3 != y2) {
            Y = y3;
        }

        bw.write(X + " " + Y);

        bw.close();
        br.close();
    }
}
