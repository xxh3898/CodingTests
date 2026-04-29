class Solution {
    public String solution(String X, String Y) {
        int[] xCount = new int[10];
        int[] yCount = new int[10];

        for (int i = 0; i < X.length(); i++) {
            int num = X.charAt(i) - '0';
            xCount[num]++;
        }

        for (int i = 0; i < Y.length(); i++) {
            int num = Y.charAt(i) - '0';
            yCount[num]++;
        }

        StringBuilder sb = new StringBuilder();

        for (int k = 9; k >= 0; k--) {
            int count = Math.min(xCount[k], yCount[k]);

            for (int i = 0; i < count; i++) {
                sb.append(k);
            }
        }

        if (sb.length() == 0) {
            return "-1";
        }

        if (sb.charAt(0) == '0') {
            return "0";
        }

        return sb.toString();
    }
}