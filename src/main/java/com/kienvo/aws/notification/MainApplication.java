package com.kienvo.aws.notification;

import java.util.Stack;

public class MainApplication {

	public static void main(String[] args) {
		Stack newStack = new Stack<>();
		newStack.push(args);
		newStack.pop();
		
		StackTest stack = new StackTest();
		
		stack.push(3);
		stack.printMinValues();
		System.out.println("minValue: " + stack.minValue());
		stack.push(3);
		stack.printMinValues();
		System.out.println("minValue: " + stack.minValue());
		stack.push(3);
		stack.printMinValues();
		System.out.println("minValue: " + stack.minValue());
		stack.push(3);
		stack.printMinValues();
		System.out.println("minValue: " + stack.minValue());
		
		stack.pop();
		stack.printMinValues();
		System.out.println("minValuePOP: " + stack.minValue());
		stack.pop();
		stack.printMinValues();
		System.out.println("minValuePOP: " + stack.minValue());
		stack.pop();
		stack.printMinValues();
		System.out.println("minValuePOP: " + stack.minValue());
		stack.pop();
		stack.printMinValues();
		System.out.println("minValuePOP: " + stack.minValue());
		
		//System.out.println("minValue: " + stack.minValue()); 

	}
	
	

}
