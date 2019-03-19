import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Task3_TDD_1 {
	
private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	@Test
	public void test1() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1,2,4-7,10");
		List l = parser.getIntegerList("list");
		assertEquals(l, Arrays.asList(1, 2, 4, 5, 6, 7, 10));
	}
	
	@Test
	public void test2() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1,2,-4--7,10");
		List l = parser.getIntegerList("list");
		assertEquals(l, Arrays.asList(-7, -6, -5, -4, 1, 2, 10));
	}
	
	@Test
	public void test3() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("--list=1,2,-4-7,10");
		List l = parser.getIntegerList("list");
		assertEquals(l, Arrays.asList(-4, -3, -2, -1, 0, 1, 1, 2, 2, 3, 4, 5, 6, 7, 10));
	}
	
	@Test
	public void test_spec1_1() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("-l \"1,2,-4-7,10\"");
		
		parser.add("l", "L", Parser.STRING);
		parser.parse("--l \'1,2,-4--7,10\'");
		
		assertEquals(parser.getIntegerList("list"), Arrays.asList(-4, -3, -2, -1, 0, 1, 1, 2, 2, 3, 4, 5, 6, 7, 10));
		assertEquals(parser.getIntegerList("l"), Arrays.asList(-7, -6, -5, -4, 1, 2, 10));
	}
	
	@Test
	public void test_spec1_2() {
		parser.add("list", "l", Parser.STRING);
		parser.parse("-l 1,2,-4-7,10");
		
		parser.add("l", "L", Parser.STRING);
		parser.parse("--l 1,2,-4--7,10");
		
		assertEquals(parser.getIntegerList("list"), Arrays.asList(-4, -3, -2, -1, 0, 1, 1, 2, 2, 3, 4, 5, 6, 7, 10));
		assertEquals(parser.getIntegerList("l"), Arrays.asList(-7, -6, -5, -4, 1, 2, 10));
	}
	
	@Test
	public void test_spec2() {
		parser.add("list", "l", Parser.STRING);		
		parser.parse("--list");
		List l = parser.getIntegerList("list");
		assertEquals(l, Arrays.asList());
	}
	
	@Test
	public void test_spec3() {
		String[] list_possible = {"1,2.4", "{}1<2>4({)","'1,2,4'", "2,1,4", "1m2m4"};

		parser.add("list", "l", Parser.STRING);	
		
		for (String item : list_possible) {
			parser.parse("--list=" + item);
			List l = parser.getIntegerList("list");
			assertEquals(l, Arrays.asList(1,2,4));
		}

	}
	
	@Test
	public void test_spec4() {
		String[] list_possible = {"1-2,4-8", "1,2,4,5,6-8", "'8-4 2-1'", "4-8m1-2"};

		parser.add("list", "l", Parser.STRING);	
		
		for (String item : list_possible) {
			parser.parse("--list=" + item);
			List l = parser.getIntegerList("list");
			assertEquals(l, Arrays.asList(1,2,4,5,6,7,8));
		}

	}
	

	@Test
	public void test_spec5() {
		String[] list_possible = {"-7--4,-3-1", "1,0,-1--3,-4--7"};
		parser.add("list", "l", Parser.STRING);	
		for (String item : list_possible) {
			parser.parse("--list=" + item);
			List l = parser.getIntegerList("list");
			assertEquals(l, Arrays.asList(-7, -6, -5, -4, -3, -2, -1, 0, 1));
		}
	}
	
	
	@Test
	public void test_spec6() {
		String[] list_possible = {"3-","-7{--5", "-3-", };
		parser.add("list", "l", Parser.STRING);	
		for (String item : list_possible) {
			parser.parse("--list=" + item);
			List l = parser.getIntegerList("list");
			assertEquals(l, Arrays.asList());
		}
	}

}
