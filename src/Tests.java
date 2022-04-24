import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class Tests {
	
	//Part tests
	
	@Test//test part ID
	void Parttest1() {
		Part p = new Part("ID123","67");
		
		assertEquals("ID123",p.getID());
	}
	
	@Test //test part colour
	void Parttest2() {
		Part p = new Part("ID123","col67");
		
		assertEquals("col67",p.getColour());
	}
	
	@Test //test number of sets part appears in 
	void Parttest3() {
		Part p = new Part("ID123","col67");
		p.SetNum(10);
		assertEquals(10,p.GetnumSet());
	}
	
	@Test //test used flag
	void Parttest4() {
		Part p = new Part("ID123","col67");
		p.Setused(true);;
		assertTrue(p.Getused());
	}
	
	@Test //test method checking if parts have same ID and colour
	void Parttest5() {
		Part p = new Part("123","67");
		Part p2 = new Part("123","67");
		assertTrue(p.same(p2));
	}
	
	
	//Set Tests
	
	@Test 
	void Settest1() {// test method checking if two versions of same set
		Set s = new Set("IDset","set1",345);
		Set s2 = new Set("IDset","set1",345);
		assertTrue(s.check(s2));
	}
	
	@Test 
	void Settest2() {// test count (how set keeps track of total parts found) 
		Set s = new Set("IDset","set1",345);
		s.addCount();
		s.addCount();
		s.addCount();
		assertEquals(4,s.getCount());
	}
	
	@Test 
	void Settest3() {// test count (how set keeps track of total parts found) 
		Set s = new Set("IDset","set1",345);
		assertEquals(1,s.getCount());//starts at one
	}
	
	@Test 
	void Settest4() {// test probability
		Set s = new Set("IDset","set1",345);
		Part p = new Part("123","67");
		p.SetNum(3);
		s.addPart(p);
		assertEquals(0.33,s.probability(),0.01);
	}
	
	@Test 
	void Settest5() {// test probability (1 part)
		Set s = new Set("IDset","set1",345);
		Part p = new Part("123","67");
		p.SetNum(1);
		s.addPart(p);
		assertEquals(1,s.probability(),0.01);
	}
	
	@Test 
	void Settest6() {// test probability (unique part)
		Set s = new Set("IDset","set1",345);
		Part p = new Part("123","67");
		p.SetNum(1);
		s.addPart(p);
		assertEquals(1,s.probability(),0.01);
	}
	
	@Test 
	void Settest7() {// test probability (multiple parts, 1 unique)
		Set s = new Set("IDset","set1",345);
		
		Part p = new Part("123","67");
		p.SetNum(10);
		s.addPart(p);
		
		Part p2 = new Part("123","67");
		p2.SetNum(1);
		s.addPart(p2);
		
		Part p3 = new Part("123","67");
		p3.SetNum(50);
		s.addPart(p3);
		
		assertEquals(1,s.probability(),0.01);
	}
	
	@Test 
	void Settest8() {// test probability (multiple parts)
		Set s = new Set("IDset","set1",345);
		
		Part p = new Part("123","67");
		p.SetNum(2);
		s.addPart(p);
		
		Part p2 = new Part("123","67");
		p2.SetNum(14);
		s.addPart(p2);
		
		Part p3 = new Part("123","67");
		p3.SetNum(4);
		s.addPart(p3);
		
		assertEquals(0.65,s.probability(),0.01);
	}
	
	@Test 
	void Settest9() {// test confirm (sets part to used)
		Set s = new Set("IDset","set1",345);
		Part p = new Part("123","67");
		p.SetNum(1);
		s.addPart(p);
		s.confirm();
		assertTrue(p.Getused());
	}
	
	@Test 
	void Settest10() {// test confirm (probability as 0)
		Set s = new Set("IDset","set1",345);
		Part p = new Part("123","67");
		p.SetNum(1);
		s.addPart(p);
		s.confirm();
		assertEquals(0,s.probability(),0.01);
	}
	
	@Test 
	void Settest11() {// test confirm (removes parts from other sets calculations)
		Set s = new Set("IDset","set1",345);
		Set s2 = new Set("IDset2","set2",735);
		
		
		Part p = new Part("123","67");
		p.SetNum(10);
		
		Part p2 = new Part("123","67");
		p2.SetNum(3);
		
		s.addPart(p);
		
		s2.addPart(p);
		s2.addPart(p2);
		
		s.confirm();
		assertEquals(0.33,s2.probability(),0.01);
	}
	
	@Test 
	void Settest12() {// test comparisons
		Set s = new Set("IDset","set1",345);
		Set s2 = new Set("IDset2","set2",735);
		
		
		Part p = new Part("123","67");
		p.SetNum(10);
		
		Part p2 = new Part("123","67");
		p2.SetNum(3);
		
		s.addPart(p);
		
		s2.addPart(p);
		s2.addPart(p2);

		assertEquals(1,s.compareTo(s2));//s2 more likely
	}
	
	@Test 
	void Settest13() {// test image
		Set s = new Set("IDset","set1",345,"imagepath");
		

		assertEquals("imagepath",s.getimg());//s2 more likely
	}
}
