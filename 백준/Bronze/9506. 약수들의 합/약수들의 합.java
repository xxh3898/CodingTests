import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Main {

    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder(); // 전체 출력을 모으기 위한 StringBuilder

        while (true) {
            int n = Integer.parseInt(br.readLine());

            if (n == -1) {
                break; // -1 입력 시 종료
            }

            int sum = 0;
            ArrayList<Integer> factors = new ArrayList<>(); // 약수들을 저장할 리스트

            // 1. 약수 구하기 (자기 자신 제외 n 전까지만)
            for (int i = 1; i < n; i++) {
                if (n % i == 0) {
                    sum += i;
                    factors.add(i);
                }
            }

            // 2. 완전수 판별 및 출력 문자열 생성
            if (sum == n) {
                // 완전수일 경우 포맷 생성 (예: 6 = 1 + 2 + 3)
                sb.append(n).append(" = ");
                for (int i = 0; i < factors.size(); i++) {
                    sb.append(factors.get(i));
                    // 마지막 요소가 아니라면 " + " 추가
                    if (i < factors.size() - 1) {
                        sb.append(" + ");
                    }
                }
                sb.append("\n");
            } else {
                // 완전수가 아닐 경우
                sb.append(n).append(" is NOT perfect.\n");
            }
        }

        System.out.print(sb);
    }
}