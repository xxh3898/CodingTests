
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        int m = sc.nextInt();
        char[][] board = new char[n][m];

        for (int i = 0; i < n; i++) {
            String line = sc.next();
            for (int j = 0; j < m; j++) {
                board[i][j] = line.charAt(j);
            }
        }

        int minCount = 64;

        for (int i = 0; i <= n - 8; i++) {
            for (int j = 0; j <= m - 8; j++) {
                int curCount = getPaintCount(i, j, board);
                if (curCount < minCount) {
                    minCount = curCount;
                }
            }
        }

        System.out.println(minCount);
        sc.close();
    }

    public static int getPaintCount(int startRow, int startCol, char[][] board) {
        int count = 0;
        char color = 'W';

        for (int i = startRow; i < startRow + 8; i++) {
            for (int j = startCol; j < startCol + 8; j++) {
                if (board[i][j] != color) {
                    count++;
                }
                color = (color == 'W') ? 'B' : 'W';
            }
            color = (color == 'W') ? 'B' : 'W';
        }

        return Math.min(count, 64 - count);
    }
}
