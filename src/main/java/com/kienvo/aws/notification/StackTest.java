package com.kienvo.aws.notification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;



public class StackTest {
	int curMinIndex = -1;
    List<Integer> minValues = new ArrayList<>();
    List<Integer> values = new LinkedList<>();
    
    Integer pop() {
    	if(values.size() > 0) {
    		Integer popedObj = values.get(values.size() - 1);
    		values.remove(values.size() - 1);
    		updateMinValues(popedObj);
    		return popedObj;
    	}
    	return null;
    }
    
    private void updateMinValues(Integer popedObj) {
		if(popedObj.intValue() == minValues.get(curMinIndex).intValue()) {
			curMinIndex--;
		}
	}

	void push(Integer obj) {
    	values.add(obj);
    	addObjectToMinValues(obj);
    }

    private void addObjectToMinValues(Integer obj) {
    	if(curMinIndex >= 0) {
    		Integer curMin = minValues.get(curMinIndex);
    		if(obj.intValue() <= curMin) {
    			minValues.add(obj);
    			curMinIndex++;
    		}
    	} else {
    		minValues.add(0, obj);
    		curMinIndex++;
    	}
		
		
	}

	Integer minValue() {
    	if(values.size() > 0) {
    		return minValues.get(curMinIndex);
    	}
    	return null;
    }
	
	void printMinValues() {
		System.out.print("curMinIndex" + curMinIndex + " ");
		for(Integer i : minValues) {
			System.out.print(i + " ");
		}
	}
}