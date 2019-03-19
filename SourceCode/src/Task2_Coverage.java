import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;

public class Task2_Coverage {
	private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	@Test
	public void test_null() {		
		assertEquals(parser.parse(null), -1);
	}
	
	@Test
	public void test_int_bool_true() {		
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=true");
		assertEquals(parser.getInteger("output"), 1);
	}
	
	@Test
	public void test_int_bool_false() {		
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=false");
		assertEquals(parser.getInteger("output"), 0);
	}
	
	@Test
	public void test_bigInt() {		
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output=23412342342334124123421341234213412342134");
		assertEquals(parser.getInteger("output"), 0);
	}
	
	@Test
	public void test_string() {		
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=1234");
		assertEquals(parser.getInteger("output"), 1234);
	}
	
	@Test
	public void test_string_no_digit() {		
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=abc");
		assertEquals(parser.getInteger("output"), 0);
	}
	
	@Test
	public void test_char() {		
		parser.add("output", "o", Parser.CHAR);
		parser.parse("--output=a");
		assertEquals(parser.getInteger("output"), 97);
	}
	
	@Test
	public void test_char_index() {		
		parser.add("output", "o", Parser.STRING);
		parser.parse(" --output ");
		assertEquals(parser.getString("output"), "");
	}
	
	@Test
	public void test_char_space() {		
		parser.add("output", "o", Parser.STRING);
		parser.parse("     ");
		assertEquals(parser.getString("output"), "");
	}
	
	@Test
	public void test_char_emptyString() {		
		parser.add("output", "o", Parser.STRING);
		parser.parse("     a");
		assertEquals(parser.getString("output"), "");
	}
	
	@Test
	public void test_toString() {		
		parser.add("output", "o", Parser.STRING);
		assertEquals(parser.toString(), "OptionMap [options=\n" + 
				"	{name=output, shortcut=o, type=3, value=}\n" + 
				"]");
	}
	
	@Test
	public void test_char_index_2() {		
		parser.add("output", "o", Parser.STRING);
		parser.parse("a");
		assertEquals(parser.getString("output"), "");
	}
	
	@Test
	public void test_parse_strings() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-k-");
		parser.parse("-daf ");
	}
	
	
	@Test(expected = RuntimeException.class)
	public void test_isOptionValid_over_4() {
		parser.add("output", "o", 5);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_isOptionValid_type_lower_1() {
		parser.add("output", "o", 0);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_isOptionValid_null() {
		parser.add(null, "o", Parser.STRING);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_isOptionValid_empty() {
		parser.add("", "o", Parser.STRING);
	}
	
	@Test(expected = RuntimeException.class)
	public void test_isOptionValid_shortcut_null() {
		parser.add("sad", null, Parser.STRING);
	}
	
	public void test_coverBranch() {
		parser.add("o", "o", Parser.STRING);
		parser.parse("o");
	}
}
