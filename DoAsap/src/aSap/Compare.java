package aSap;

import java.util.Comparator;

class Compare implements Comparator  {
	
	@Override
	public int compare(Object a, Object b)   {
		int n1, n2;
		String aS = a.toString();
		if ("".equals(aS) || aS.length()<10) n1 = 0;
		else n1 = Integer.valueOf(aS.substring(0, 2))+30*Integer.valueOf(aS.substring(3, 5))+30*12*Integer.valueOf(aS.substring(6, 10));
		String bS = b.toString();
		if ("".equals(bS) || bS.length()<10) n2 = 0;
		else n2 = Integer.valueOf(bS.substring(0, 2))+30*Integer.valueOf(bS.substring(3, 5))+30*12*Integer.valueOf(bS.substring(6, 10));
		return n1 - n2;
	}
}
