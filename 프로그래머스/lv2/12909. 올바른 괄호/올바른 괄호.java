class Solution {
    boolean solution(String s) {
        boolean answer = true;
        int temp=0;
       for(int i=0; i<s.length();i++){
           if(s.charAt(i)==('(')){
               temp++;
           }
           if(s.charAt(i)==(')')){
               temp--;
           }
           if(temp<0){answer=false;return answer;}
       }
 answer=   temp !=0? false:true;
                if(s.charAt(0)==')'){answer=false;};

        return answer;
    }
}