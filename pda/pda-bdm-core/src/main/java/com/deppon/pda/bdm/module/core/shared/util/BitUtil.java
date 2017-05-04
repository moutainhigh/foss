package com.deppon.pda.bdm.module.core.shared.util;

public class BitUtil {
	public static final int thirtyOne = 31;
	public static final int thirtyTwo = 32;
	public static long setBit(long source, long index){
		return setBit(source,index,true);
	}
	
	public static long setBit(long source, long index, boolean bitValue){
		return bitValue?(source|(1L<<(index-1L))):(source&~(1L<<(index-1L)));
	}
	

	public static void main(String[] args) {
		long l = 2147483648l;
		
		System.out.println(Long.toBinaryString(l));
		
//		l = setBit(l, 3);
		l = setBit(l, thirtyOne);
		l = setBit(l, thirtyTwo);
//		l = setBit(l, 64);
//		l = setBit(l, 33, false);
		
		System.out.println(Long.toBinaryString(l));
		System.out.println(l);
		
		
		/*int i = Integer.valueOf("0101001", 2);
		System.out.println(i);
		
		String is = Integer.toBinaryString(i);
		System.out.println(is);

		int j=setBit(setBit(setBit(0,1),4),6);
		System.out.println(Integer.toBinaryString(j));
		j=setBit(j,2,false);
		System.out.println(Integer.toBinaryString(j));
		j=setBit(j,3,false);
		System.out.println(Integer.toBinaryString(j));
		j=setBit(j,4,false);
		System.out.println(Integer.toBinaryString(j));
		//int j = setBit
*/	}
}
