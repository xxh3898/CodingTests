
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
        String name = st.nextToken();

        int l = name.length();
        String num = "";

        for (int i = 0; i < name.length(); i++) {
            switch (name.charAt(i)) {
                case 'A':
                case 'B':
                case 'C':
                    num += "2";
                    break;
                case 'D':
                case 'E':
                case 'F':
                    num += "3";
                    break;
                case 'G':
                case 'H':
                case 'I':
                    num += "4";
                    break;
                case 'J':
                case 'K':
                case 'L':
                    num += "5";
                    break;
                case 'M':
                case 'N':
                case 'O':
                    num += "6";
                    break;
                case 'P':
                case 'Q':
                case 'R':
                case 'S':
                    num += "7";
                    break;
                case 'T':
                case 'U':
                case 'V':
                    num += "8";
                    break;
                case 'W':
                case 'X':
                case 'Y':
                case 'Z':
                    num += "9";
                    break;
                default:
                    break;
            }
        }

        int Num = 0;

        for (int j = 0; j < num.length(); j++) {
            Num += num.charAt(j) - '0';
        }

        Num += l;

        bw.write(Num + "");

        br.close();
        bw.close();
    }
}
