import java.util.*;

class Solution {
    public int solution(int[] sides) {
   
        Arrays.sort(sides);
        
        int a=sides[0];
        int b=sides[1];
        int answer=0;
        
        // b-a+1 ~ b, b+1 ~ b+a-1

        for(int i=b-a+1;i<=b;i++){
            answer++;
        }
        
        for(int j= b+1;j<=b+a-1;j++){
            answer++;
        }
            
        return answer;
    }
}