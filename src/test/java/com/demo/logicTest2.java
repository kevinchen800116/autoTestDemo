package com.demo;

import java.util.Arrays;
import java.util.LinkedList;

public class logicTest2 {

    public static void main(String[] args) {
    	Q1();
    	Q2_count("Hello welcome to Cathay 60th year anniversary");
    	
        int numberOfPeople = 100;
        int result = Q3_sort(numberOfPeople);
        System.out.println("最後留下同事的順位: " + result);

    }
    
    public static int Q3_sort(int numberOfPeople) {
        // 建立循環
        LinkedList<Integer> circle = new LinkedList<>();
        for (int i = 1; i <= numberOfPeople; i++) {
            circle.add(i);
        }

        int count = 0;
        int index = 0;

        
        while (circle.size() > 1) {
            count++;
            if (count == 3) {
            	// 如果數到3就移除該位置的數字
                circle.remove(index);
                count = 0;
            } else {
                // 移動到下一個位置
                index = (index + 1) % circle.size();
            }
        }

        // 返回最後剩下的位置
        return circle.get(0);
    }


    
    public static void Q2_count(String input) {
		String lowerString =input.toLowerCase();
		
		// 初始化數組 紀錄字母頻率
        int[] letterFrequency = new int[36];

		for (int i = 0; i < lowerString.length(); i++) {
			char charType=lowerString.charAt(i);
			if (Character.isLetter(charType)) {
	            int index = charType - 'a';
	            if (index >= 0 && index < 26) { 
	            	// 確保索引在範圍內
	                letterFrequency[index] = letterFrequency[index] + 1;
	            }
			}else if (Character.isDigit(charType)) {
	            int index = charType - '0' + 26;
	            letterFrequency[index] = letterFrequency[index] + 1;
			}
		}
        // 印出字母頻率
        for (int i = 0; i < letterFrequency.length; i++) {
        	char currentChar;
        	if (i<26) {
                currentChar = (char) ('a' + i);
			}else {
				// 數字
			    currentChar = (char) ('0' + (i - 26));
			}
        	
            int frequency = letterFrequency[i];

            if (frequency > 0) {
                System.out.println(currentChar + ": " + frequency);
            }
        }

	}
    
    
    
    public static void Q1() {
        int[] originalArray = {53, 64, 75, 19, 92};
        int[] correctArray = correctfunction(originalArray);

        System.out.print("原本: ");
        System.out.println(Arrays.toString(originalArray));

        System.out.print("修正後: ");
        System.out.println(Arrays.toString(correctArray));
	}

    public static int[] correctfunction(int[] array) {
        int[] changeArray = new int[array.length];

        for (int i = 0; i < array.length; i++) {
            int number = array[i];
            // 取個位數乘以10將其變成十位數
            // 取十位數除以10將其變成個位數
            // 兩數相加得到交換的數字
            int newNumber = ((number % 10) * 10) + (number / 10);
            changeArray[i] = newNumber;
        }

        return changeArray;
    }

}
