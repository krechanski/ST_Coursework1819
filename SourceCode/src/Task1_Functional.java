import static org.junit.Assert.*;

import org.junit.Test;

import st.Parser;

import org.junit.Before;
import static org.junit.Assert.assertEquals;



public class Task1_Functional {
private Parser parser;
	
	@Before
	public void set_up() {
		parser = new Parser();
	}
	
	
	/*
	 * PART 1
	 */
	
	/*
	 * Specification 1
	 */
	@Test
	public void part1_spec1() {
		parser.add("output", "o", Parser.STRING);
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output 1");
		assertEquals(parser.getInteger("o"), 1);
	}
	
	
	/*
	 * Specification 2
	 */
	@Test(expected = RuntimeException.class)
	public void part1_spec2_1() {
		parser.add("1output", "o", Parser.STRING);
		parser.parse("--1output test");
		assertEquals(parser.getString("o"), "test");
	}
	
	@Test(expected = RuntimeException.class)
	public void part1_spec2_2() {
		parser.add("output", "1o", Parser.STRING);
		parser.parse("--output test");
		assertEquals(parser.getString("1o"), "test");
	}
	
	@Test(expected = RuntimeException.class)
	public void part1_spec2_3() {
		parser.add("output*", "o", Parser.STRING);
		parser.parse("--output* test");
		assertEquals(parser.getString("o"), "test");
	}
	
	@Test(expected = RuntimeException.class)
	public void part1_spec2_4() {
		parser.add("output", "o+", Parser.STRING);
		parser.parse("--output test");
		assertEquals(parser.getString("o+"), "test");
	}
	
	@Test
	public void part1_spec2_5() {
		parser.add("output_123", "o_123", Parser.STRING);
		parser.parse("--output_123 test");
		assertEquals(parser.getString("o_123"), "test");
	}
	
	/*
	 * Specification 3
	 */
	@Test
	public void part1_spec3() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output test1");
		
		parser.add("OUTPUT", "O", Parser.STRING);
		parser.parse("--OUTPUT test2");
		
		assertEquals(parser.getString("o"), "test1");
		assertEquals(parser.getString("O"), "test2");
	}
	
	/*
	 * Specification 4
	 */
	@Test
	public void part1_spec4_1() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o test1");
		
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o test2");
		
		assertEquals(parser.getString("output"), "test1");
		assertEquals(parser.getString("o"), "test2");
	}
	
	@Test
	public void part1_spec4_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output test1");
		
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o test2");
		
		assertEquals(parser.getString("output"), "test1");
		assertEquals(parser.getString("o"), "test2");
	}
	
	/*
	 * Specification 5
	 */
	
	//Test true
	@Test
	public void part1_spec5_1() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void part1_spec5_2() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void part1_spec5_3() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o=true");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void part1_spec5_4() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o=TRUE");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void part1_spec5_5() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o=69");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void part1_spec5_6() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=true");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	@Test
	public void part1_spec5_7() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=TRUE");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	
	@Test
	public void part1_spec5_8() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=69");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	//Test false
	@Test
	public void part1_spec5_9() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o=false");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void part1_spec5_10() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o=FALSE");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void part1_spec5_11() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=false");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void part1_spec5_12() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=FALSE");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	
	@Test
	public void part1_spec5_13() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=0");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void part1_spec5_14() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("-o=0");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void part1_spec5_15() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output -o=0");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	@Test
	public void part1_spec5_16() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	
	/*
	 * PART 2
	 */
	
	/*
	 * Specification 1
	 */
	@Test
	public void part2_spec1_1() {
		parser.add("output",  Parser.BOOLEAN);
		parser.parse("-output=true");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	/*
	 * Specification 2
	 */
	@Test
	public void part2_spec2_1() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--o=true");
		assertEquals(parser.getBoolean("output"), false);
	}

	/*
	 * Specification 3
	 */
	
	//Test Boolean
	@Test
	public void part2_spec3_1() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("-o true");
		assertEquals(parser.getBoolean("output"), true);
	}
	
	//Test String
	@Test
	public void part2_spec3_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output test");
		assertEquals(parser.getString("output"), "test");
	}
	
	//Test Integer
	@Test
	public void part2_spec3_3() {
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output 9000");
		assertEquals(parser.getInteger("output"), 9000);
	}
	
	//Test Char
	@Test
	public void part2_spec3_4() {
		parser.add("output", "o", Parser.CHAR);
		parser.parse("--output a");
		assertEquals(parser.getChar("output"), 'a');
	}
	
	/*
	 * Specification 4
	 */
	
	//String
	@Test
	public void part2_spec4_1() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=\"test\"");
		assertEquals(parser.getString("output"), "test");
	}
	
	@Test
	public void part2_spec4_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=\'test\'");
		assertEquals(parser.getString("output"), "test");
	}
	
	//Char
	@Test
	public void part2_spec4_3() {
		parser.add("output", "o", Parser.CHAR);
		parser.parse("--output=\'t\'");
		assertEquals(parser.getChar("output"), 't');
	}
	
	@Test
	public void part2_spec4_4() {
		parser.add("output", "o", Parser.CHAR);
		parser.parse("--output=\"t\"");
		assertEquals(parser.getChar("output"), 't');
	}
	
	//Integer
	@Test
	public void part2_spec4_5() {
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output=\'69\'");
		assertEquals(parser.getInteger("output"), 69);
	}
		
	@Test
	public void part2_spec4_6() {
		parser.add("output", "o", Parser.INTEGER);
		parser.parse("--output=\"69\"");
		assertEquals(parser.getInteger("output"), 69);
	}
	
	// Integer
	@Test
	public void part2_spec4_7() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=\'True\'");
		assertEquals(parser.getBoolean("output"), true);
	}

	@Test
	public void part2_spec4_8() {
		parser.add("output", "o", Parser.BOOLEAN);
		parser.parse("--output=\"false\"");
		assertEquals(parser.getBoolean("output"), false);
	}
	
	/*
	 * Specification 5
	 */
	@Test
	public void part2_spec5_1() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=\'value=\"123\"\'");
		assertEquals(parser.getString("output"), "value=\"123\"");
	}

	@Test
	public void part2_spec5_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=\"value=\'123\'\"");
		assertEquals(parser.getString("output"), "value=\'123\'");
	}
	
	/*
	 * Specification 6
	 */
	@Test
	public void part2_spec6_1() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=t");
		parser.parse("--output=hello there :)");
		parser.parse("--output=test");
		assertEquals(parser.getString("output"), "test");
	}
	
	@Test
	public void part2_spec6_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("--output=t");
		parser.parse("--output=test");
		assertNotEquals(parser.getString("output"), "t");
	}
	
	/*
	 * Specification 7
	 */
	
	//String
	@Test
	public void part2_spec7_1() {
		parser.add("output", "o", Parser.STRING);
		assertEquals(parser.getString("output"), "");
	}

	//Char
	@Test
	public void part2_spec7_2() {
		parser.add("output", "o", Parser.CHAR);
		assertEquals(parser.getChar("output"), '\0');
	}

	// String
	@Test
	public void part2_spec7_3() {
		parser.add("output", "o", Parser.INTEGER);
		assertEquals(parser.getInteger("output"), 0);
	}

	// String
	@Test
	public void part2_spec7_4() {
		parser.add("output", "o", Parser.BOOLEAN);
		assertEquals(parser.getBoolean("output"), false);
	}
	
	/*
	 * PART 3
	 */
	
	/*
	 * Specification 1
	 */
	@Test
	public void part3_spec1_1() {
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o=test1");
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=test2");
		assertEquals(parser.getString("o"), "test1");
	}
	
	@Test
	public void part3_spec1_2() {
		parser.add("output", "o", Parser.STRING);
		parser.parse("-o=test1");
		parser.add("o", "O", Parser.STRING);
		parser.parse("--o=test2");
		parser.add("o", "o", Parser.STRING);
		parser.parse("-o=test3");
		
		assertNotEquals(parser.getString("o"), "test1");
		assertNotEquals(parser.getString("o"), "test2");
	}	

	/*
	 * Specification 2
	 */
	
	//String
	@Test
	public void part3_spec2_1() {
		assertEquals(parser.getString("output"), "");
	}

	//Char
	@Test
	public void part3_spec2_2() {
		assertEquals(parser.getChar("output"), '\0');
	}

	// String
	@Test
	public void part3_spec2_3() {
		assertEquals(parser.getInteger("output"), 0);
	}

	// String
	@Test
	public void part3_spec2_4() {
		assertEquals(parser.getBoolean("output"), false);
	}
	
	
}
