package com.poc.h2.demo;

import java.util.ArrayList;
import java.util.List;

public class SplitList {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
	     

	}
	
	public static <T> List<List<T>> split(List<T> list, int numberOfParts) {
	      List<List<T>> numberOfPartss = new ArrayList<>(numberOfParts);
	      int size = list.size();
	      int sizePernumberOfParts = (int) Math.ceil(((double) size) / numberOfParts);
	      int leftElements = size;
	      int i = 0;
	      while (i < size && numberOfParts != 0) {
	          numberOfPartss.add(list.subList(i, i + sizePernumberOfParts));
	          i = i + sizePernumberOfParts;
	          leftElements = leftElements - sizePernumberOfParts;
	          sizePernumberOfParts = (int) Math.ceil(((double) leftElements) / --numberOfParts);
	       }
	       return numberOfPartss;
	   }

}
