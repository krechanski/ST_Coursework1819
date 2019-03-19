import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

public class Task3_TDD_N {
	
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

}
