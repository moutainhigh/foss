package com.deppon.foss.module.pickup;

import java.util.LinkedList;
import java.util.Queue;

public class TestOne {
	
	public static void main(String[] ss){
		System.out.println("1");
		Queue<String> queue = new LinkedList<String>();  
        queue.offer("a");  
        queue.offer("b!");  
        queue.offer("c！");  
        System.out.println(queue.size());  
        String str;  
        while((str=queue.poll())!=null){  
            System.out.print(str);  
        }  
        System.out.println();  
        System.out.println(queue.size());  
	}
}
