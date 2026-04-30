class Solution {
    public int solution(int n) {
                int answer = 0;

        for(int i=0;i<=n;){
            answer+=i;
    i=i+2;
        }
        return answer;
    }
}