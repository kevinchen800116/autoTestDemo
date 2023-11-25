package com.demo;

import java.util.ArrayList;
import java.util.Arrays;

public class logicTest {
	
//	public static ArrayList<Integer> Q1(ArrayList<Integer> grade) {
//		for (int i=0, j=grade.size()-1; i<j; i++) {
//			grade.add(i,grade.remove(j));
//		}
//		return grade;
//	}
	
    public static void swapNumbers(int a, int b) {
        // 使用临时变量进行交换
        int temp = a;
        a = b;
        b = temp;
        // 打印交换后的值（这里仅在方法内打印，原始值不会改变）
        System.out.println("Inside the swapNumbers method: a = " + a + ", b = " + b);
    }

	
	public static int digit(int number, int n) {
		  return (int) (number / Math.pow(10, n) % 10);
	}

	
	public static ArrayList<Integer> Q1(ArrayList<Integer> grade) {
		for (int i = 0; i < grade.size(); i++) {
			int a= digit(grade.get(i),0);
			int b= digit(grade.get(i),1);
		}
		return grade;
	}
	
	public void Q2() {
		
	}
	
	public static void main(String[] args) {
		ArrayList<Integer> grade1=new ArrayList<Integer>(Arrays.asList(17,27,37,47,57,67,77));
		System.out.println(Q1(grade1));
	}
}
