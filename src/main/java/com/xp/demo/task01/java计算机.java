package com.xp.demo.task01;

public class java计算机 {
    public static void main(String[] args) {
    int start = 1;
    int end = 100;
    System.out.println((start+end)*(end-start+1)/2);
    int result=0;
    for (int i=start;i<=end;i++){
        result+=i;
    }
    System.out.println(result);

}
}
