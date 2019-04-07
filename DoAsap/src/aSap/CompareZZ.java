package aSap;

import java.util.Comparator;

public class CompareZZ implements Comparator {

	@Override
	public int compare(Object a, Object b) {
		int n1, n2;
		String aS = a.toString();
		if ("".equals(aS) || aS.length()<13) n1 = 0;
		else n1 = Integer.valueOf(aS.substring(6));
		
		
		String bS = b.toString();
		if ("".equals(bS) || bS.length()<10) n2 = 0;
		else n2 = Integer.valueOf(bS.substring(6));
		//System.out.println(n1+" "+n2);
		return n1 - n2;
	}

}
