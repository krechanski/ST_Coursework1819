package st;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
	public static final int INTEGER = 1;
	public static final int BOOLEAN = 2;
	public static final int STRING = 3;
	public static final int CHAR = 4;
	
	private OptionMap optionMap;
	
	public Parser() {
		optionMap = new OptionMap();
	}
	
	public void add(String option_name, String shortcut, int value_type) {
		optionMap.store(option_name, shortcut, value_type);
	}
	
	public void add(String option_name, int value_type) {
		optionMap.store(option_name, "", value_type);
	}

	public int getInteger(String option) {
		String value = getString(option);
		int type = getType(option);
		int result;
		switch (type) {
		case INTEGER:
			try {
				result = Integer.parseInt(value);
			} catch (Exception e) {
		        try {
		            new BigInteger(value);
		        } catch (Exception e1) {
		            result = 0;
		        }
		        result = 0;
		    }
			break;
		case BOOLEAN:
			if (getBoolean(option) == false) {
				result = 0;
			} else {
				result = 1;
			}
			break;
		case STRING:
		    int power = 1;
		    result = 0;
		    char c;
		    for (int i = value.length() - 1; i >= 0; --i){
		        c = value.charAt(i);
		        if (!Character.isDigit(c)) return 0;
		        result = result + power * (c - '0');
		        power *= 10;
		    }
		    break;
		case CHAR:
			result = (int)getChar(option);
			break;
		default:
			result = 0;
		}
		return result;
	}
	
	public boolean getBoolean(String option) {
		String value = getString(option);
		boolean result;
		if (value.toLowerCase().equals("false") || value.equals("0") || value.equals("")) {
			result = false;
		} else {
			result = true;
		}
		return result;
	}
	
	public String getString(String option) {
		String result = optionMap.getValue(option);
		return result;
	}
	
	public char getChar(String option) {
		String value = getString(option);
		char result;
		if (value.equals("")) {
			result = '\0';
		} else {
			result = value.charAt(0);
		}
		return result;
	}
	
	public int parse(String command_line_options) {
		if (command_line_options == null) {
			return -1;
		}
		int length = command_line_options.length();
		if (length == 0) {
			return -2;
		}
		
		int char_index = 0;
		while (char_index < length) {
			char current_char = command_line_options.charAt(char_index);

			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (current_char != ' ') {
					break;
				}
				char_index++;
			}
			
			boolean isShortcut = true;
			String option_name = "";
			String option_value = "";
			if (current_char == '-') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
				if (current_char == '-') {
					isShortcut = false;
					char_index++;
				}
			} else {
				return -3;
			}
			current_char = command_line_options.charAt(char_index);
			
			while (char_index < length) {
				current_char = command_line_options.charAt(char_index);
				if (Character.isLetterOrDigit(current_char) || current_char == '_') {
					option_name += current_char;
					char_index++;
				} else {
					break;
				}
			}
			
			boolean hasSpace = false;
			if (current_char == ' ') {
				hasSpace = true;
				while (char_index < length) {				
					current_char = command_line_options.charAt(char_index);
					if (current_char != ' ') {
						break;
					}
					char_index++;
				}
			}

			if (current_char == '=') {
				char_index++;
				current_char = command_line_options.charAt(char_index);
			}
			if ((current_char == '-'  && hasSpace==true ) || char_index == length) {
				if (getType(option_name) == BOOLEAN) {
					option_value = "true";
					if (isShortcut) {
						optionMap.setValueWithOptioShortcut(option_name, option_value);
					} else {
						optionMap.setValueWithOptionName(option_name, option_value);
					}
				} else {
					return -3;
				}
				continue;
			} else {
				char end_symbol = ' ';
				current_char = command_line_options.charAt(char_index);
				if (current_char == '\'' || current_char == '\"') {
					end_symbol = current_char;
					char_index++;
				}
				while (char_index < length) {
					current_char = command_line_options.charAt(char_index);
					if (current_char != end_symbol) {
						option_value = option_value + current_char;
						char_index++;
					} else {
						break;
					}
				}
			}
			
			if (isShortcut) {
				optionMap.setValueWithOptioShortcut(option_name, option_value);
			} else {
				optionMap.setValueWithOptionName(option_name, option_value);
			}
			char_index++;
		}
		return 0;
	}
	
	private int getType(String option) {
		int type = optionMap.getType(option);
		return type;
	}
	
	@Override
	public String toString() {
		return optionMap.toString();
	}
	
	/*
	 * Task 3 function
	 */
	public List<Integer> getIntegerList(String option)  {
		List<Integer> list_numbers = new ArrayList<Integer>(); 
		String value = getString(option);
		
		if (value.equals("")) {
			return list_numbers;
		}
		
		// Split the string on non-number characters except hyphen
		List<String> numbers = Arrays.asList(value.split("[^-\\d]"));
		
		// Initialise first number and second number in the case that the element in list numbers is a range rather than a number
		int firstNumber=0;
		int secondNumber=0;
		
		for (String n:numbers) {
			// Ignore empty strings (a result of two separators being next to each other)
			if (n.equals("")) {
				continue;
			}
			
			// Check if the element is a range
			if (n.contains("-")) {
				// Find the first number in the range
				Pattern pattern_firstNumber = Pattern.compile("^[-]?[1-9]+");
		        Matcher matcher_firstNumber = pattern_firstNumber.matcher(n);
		        if(matcher_firstNumber.find()) {
		            firstNumber = Integer.parseInt(matcher_firstNumber.group());
		        }
		        else {
		        	// First number is not found
		        	list_numbers = new ArrayList<Integer>();
		        	return list_numbers;
		        }

		        // Find the hyphen together with the second number in the case that the second number is negative
		        Pattern pattern_secondNumber = Pattern.compile("[-][-][1-9]+$");
		        Matcher matcher_secondNumber = pattern_secondNumber.matcher(n);
		        if(matcher_secondNumber.find()) {
		        	// removes the hyphen and converts the number to a string
		            secondNumber = Integer.parseInt(matcher_secondNumber.group()
		            		.replaceFirst("-", "")); 
		        }
		        else {
		        	// Find the hyphen together with the second number in the case that the second number is positive
		        	pattern_secondNumber = Pattern.compile("[-][1-9]+$");
			        matcher_secondNumber = pattern_secondNumber.matcher(n);
			        if(matcher_secondNumber.find()) {
			        	// removes the hyphen and converts the number to a string
			            secondNumber = Integer.parseInt(matcher_secondNumber.group()
			            		.replaceFirst("-", ""));
			        }
			        else {
			        	// Second number is not found
			        	list_numbers = new ArrayList<Integer>();
			        	return list_numbers;
			        }
		        }
		        
		        // Add the numbers in the range to the list
		        for (int i=Math.min(firstNumber, secondNumber); i<=Math.max(firstNumber, secondNumber); i++) {
		        	list_numbers.add(i);
		        }
			}
			
			else {
				// If the element doesn't contain a hyphen then it is a number and we add it to the list
				list_numbers.add(Integer.parseInt(n));
			}
		}
		
		// Sort the list in ascending order
		Collections.sort(list_numbers);
		
		return list_numbers;
	}

}

