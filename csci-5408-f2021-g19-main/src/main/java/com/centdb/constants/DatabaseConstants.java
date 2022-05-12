package com.centdb.constants;

import java.nio.file.Paths;

public class DatabaseConstants {

	public static final String BASE_USER_DIR_PATH = Paths
			.get(System.getProperty("user.dir"), "userDetails", "userLoginDetails.txt").toString();
	public static final String BASE_EXPORT_PATH = Paths.get(System.getProperty("user.dir"), "centdb-exports")
			.toString();
	public static final String DATABASE_PATH = Paths.get(System.getProperty("user.dir"), "centdb").toString();
	public static final String TABLE_SUFFIX = ".table";
	public static final String METADATA_SUFFIX = ".metadata";
	public static final String DELIMITER_SYMBOL = "|";
	public static final String TMP_FILE = ".tmp";

	public static class RegExConstants {

		public static final String START_SYMBOL = "^";
		public static final String END_SYMBOL = "$";
		public static final String STAR_SYMBOL = "*";
		public static final String EQUAL_SYMBOL = "=";
		public static final String OPEN_BRACKET_SYMBOL = "(";
		public static final String CLOSE_BRACKET_SYMBOL = ")";

		public static final String COMMA_REGEX = ",";
		public static final String OPEN_BRACKET_REGEX = "\\(";
		public static final String CLOSE_BRACKET_REGEX = "\\)";
		public static final String STAR_REGEX = "\\*";
		public static final String DELIMITER_REGEX = "\\|";

		public static final String REQUIRED_SPACES_REGEX = "( )+";
		public static final String OPTIONAL_SPACES_REGEX = "( )*";
		public static final String OPTINAL_CHARACTERS_REGEX = "([a-zA-Z]*)";
		public static final String REQUIRED_CHARACTERS_REGEX = "([a-zA-Z]+)";
		public static final String OPTIONAL_ANY_CHARACTERS_OR_SPECIAL_CHARACTERS_REGEX = "([a-zA-Z0-9_-]*)";
		public static final String REQUIRED_ANY_CHARACTERS_OR_SPECIAL_CHARACTERS_REGEX = "([a-zA-Z0-9_-]+)";
		public static final String VALUE_PLACEHOLDER = "('" + "([( )a-zA-Z0-9_-]*)" + "')";

		public static final String USE_REGEX = "((u|U)(s|S)(e|E))";
		public static final String CREATE_REGEX = "((c|C)(r|R)(e|E)(a|A)(t|T)(e|E))";
		public static final String SELECT_REGEX = "((s|S)(e|E)(l|L)(e|E)(c|C)(t|T))";
		public static final String DROP_REGEX = "((d|D)(r|R)(o|O)(p|P))";
		public static final String DATABASE_REGEX = "((d|D)(a|A)(t|T)(a|A)(b|B)(a|A)(s|S)(e|E))";
		public static final String TABLE_REGEX = "((t|T)(a|A)(b|B)(l|L)(e|E))";
		public static final String TRUNCATE_REGEX = "((t|T)(r|R)(u|U)(n|N)(c|C)(a|A)(t|T)(e|E))";
		public static final String FROM_REGEX = "((f|F)(r|R)(o|O)(m|M))";
		public static final String WHERE_REGEX = "((w|W)(h|H)(e|E)(r|R)(e|E))";
		public static final String INSERT_REGEX = "((i|I)(n|N)(s|S)(e|E)(r|R)(t|T))";
		public static final String INTO_REFEX = "((i|I)(n|N)(t|T)(o|O))";
		public static final String VALUES_REGEX = "((v|V)(a|A)(l|L)(u|U)(e|E)(s|S))";
		public static final String UPDATE_REGEX = "((u|U)(p|P)(d|D)(a|A)(t|T)(e|E))";
		public static final String SET_REGEX = "((s|S)(e|E)(t|T))";
		public static final String DELETE_REGEX = "((d|D)(e|E)(l|L)(e|E)(t|T)(e|E))";

		public static final String PLACEHOLDER = "([a-zA-Z]+[a-zA-Z0-9_-]*)";

		public static final String PRIMARY_KEY_REGEX = "((p|P)(r|R)(i|I)(m|M)(a|A)(r|R)(y|Y)(( )+)(k|K)(e|E)(y|Y))";
		public static final String FOREIGN_KEY_REGEX = "((f|F)(o|O)(r|R)(e|E)(i|I)(g|G)(n|N))(( )+)((k|K)(e|E)(y|Y))";
		public static final String REFERENCES_REGEX = "((r|R)(e|E)(f|F)(e|E)(r|R)(e|E)(n|N)(c|C)(e|E)(s|S))";

		public static final String PRIMARY_KEY_EXPRESSION = "(" + OPTIONAL_SPACES_REGEX + COMMA_REGEX
				+ OPTIONAL_SPACES_REGEX + PRIMARY_KEY_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + ")?";

		public static final String FOREIGN_KEY_EXPRESSION = "(" + OPTIONAL_SPACES_REGEX + COMMA_REGEX
				+ OPTIONAL_SPACES_REGEX + FOREIGN_KEY_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER
				+ REQUIRED_SPACES_REGEX + REFERENCES_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPEN_BRACKET_REGEX
				+ PLACEHOLDER + CLOSE_BRACKET_REGEX + OPTIONAL_SPACES_REGEX + ")*";

		public static final String SELECT_QUERY_COLUMN_REGEX = "((" + STAR_REGEX + ")|((" + PLACEHOLDER + "))("
				+ OPTIONAL_SPACES_REGEX + "(" + COMMA_REGEX + ")" + OPTIONAL_SPACES_REGEX + PLACEHOLDER + ")"
				+ STAR_SYMBOL + ")";

		public static final String WHERE_CLAUSE_REGEX = "(" + REQUIRED_SPACES_REGEX + WHERE_REGEX
				+ REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX + EQUAL_SYMBOL + OPTIONAL_SPACES_REGEX
				+ VALUE_PLACEHOLDER + ")";

		public static final String OPTIONAL_WHERE_CLAUSE_REGEX = WHERE_CLAUSE_REGEX + "?";

		public static final String ROW_REGEX = "(" + OPEN_BRACKET_REGEX + VALUE_PLACEHOLDER + "("
				+ OPTIONAL_SPACES_REGEX + COMMA_REGEX + OPTIONAL_SPACES_REGEX + VALUE_PLACEHOLDER + ")*"
				+ CLOSE_BRACKET_REGEX + ")";

		public static final String MULTIPLE_ROW_REGEX = "(" + ROW_REGEX + "(" + OPTIONAL_SPACES_REGEX + COMMA_REGEX
				+ OPTIONAL_SPACES_REGEX + ROW_REGEX + ")*" + ")";

		public static final String VARCHAR_DATATYPE_REGEX = "((v|V)(a|A)(r|R)(c|C)(h|H)(a|A)(r|R))";
		public static final String INT_DATATYPE_REGEX = "((i|I)(n|N)(t|T))";
		public static final String CHAR_DATATYPE_REGEX = "((c|C)(h|H)(a|A)(r|R))";
		public static final String DOUBLE_DATATYPE_REGEX = "((d|D)(o|O)(u|U)(b|B)(l|L)(e|E))";

		public static final String SQL_DATATYPES_REGEX = "(" + VARCHAR_DATATYPE_REGEX + "|" + INT_DATATYPE_REGEX + "|"
				+ CHAR_DATATYPE_REGEX + "|" + DOUBLE_DATATYPE_REGEX + ")";

		public static final String COLUMN_NAME_REGEX = PLACEHOLDER;

		public static final String USE_DATABASE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + USE_REGEX
				+ REQUIRED_SPACES_REGEX + DATABASE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ END_SYMBOL;

		public static final String CREATE_DATABASE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + CREATE_REGEX
				+ REQUIRED_SPACES_REGEX + DATABASE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ END_SYMBOL;

		public static final String CREATE_TABLE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + CREATE_REGEX
				+ REQUIRED_SPACES_REGEX + TABLE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ OPEN_BRACKET_REGEX + OPTIONAL_SPACES_REGEX + COLUMN_NAME_REGEX + REQUIRED_SPACES_REGEX
				+ SQL_DATATYPES_REGEX + OPEN_BRACKET_SYMBOL + OPTIONAL_SPACES_REGEX + COMMA_REGEX
				+ OPTIONAL_SPACES_REGEX + COLUMN_NAME_REGEX + REQUIRED_SPACES_REGEX + SQL_DATATYPES_REGEX
				+ OPTIONAL_SPACES_REGEX + CLOSE_BRACKET_SYMBOL + STAR_SYMBOL + OPTIONAL_SPACES_REGEX
				+ PRIMARY_KEY_EXPRESSION + FOREIGN_KEY_EXPRESSION + OPTIONAL_SPACES_REGEX + CLOSE_BRACKET_REGEX;

		public static final String REMOVE_TILL_PRIMARY_KEY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + CREATE_REGEX
				+ REQUIRED_SPACES_REGEX + TABLE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ OPEN_BRACKET_REGEX + OPTIONAL_SPACES_REGEX + COLUMN_NAME_REGEX + REQUIRED_SPACES_REGEX
				+ SQL_DATATYPES_REGEX + OPEN_BRACKET_SYMBOL + OPTIONAL_SPACES_REGEX + COMMA_REGEX
				+ OPTIONAL_SPACES_REGEX + COLUMN_NAME_REGEX + REQUIRED_SPACES_REGEX + SQL_DATATYPES_REGEX
				+ OPTIONAL_SPACES_REGEX + CLOSE_BRACKET_SYMBOL + STAR_SYMBOL + OPTIONAL_SPACES_REGEX;

		public static final String REMOVE_TILL_FOREIGN_KEY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + CREATE_REGEX
				+ REQUIRED_SPACES_REGEX + TABLE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ OPEN_BRACKET_REGEX + OPTIONAL_SPACES_REGEX + COLUMN_NAME_REGEX + REQUIRED_SPACES_REGEX
				+ SQL_DATATYPES_REGEX + OPEN_BRACKET_SYMBOL + OPTIONAL_SPACES_REGEX + COMMA_REGEX
				+ OPTIONAL_SPACES_REGEX + COLUMN_NAME_REGEX + REQUIRED_SPACES_REGEX + SQL_DATATYPES_REGEX
				+ OPTIONAL_SPACES_REGEX + CLOSE_BRACKET_SYMBOL + STAR_SYMBOL + OPTIONAL_SPACES_REGEX
				+ PRIMARY_KEY_EXPRESSION;

		public static final String DROP_DATABASE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + DROP_REGEX
				+ REQUIRED_SPACES_REGEX + DATABASE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ END_SYMBOL;

		public static final String DROP_TABLE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + DROP_REGEX
				+ REQUIRED_SPACES_REGEX + TABLE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ END_SYMBOL;

		public static final String TRUNCATE_TABLE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + TRUNCATE_REGEX
				+ REQUIRED_SPACES_REGEX + TABLE_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_SPACES_REGEX
				+ END_SYMBOL;

		public static final String SELECT_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + SELECT_REGEX
				+ REQUIRED_SPACES_REGEX + SELECT_QUERY_COLUMN_REGEX + REQUIRED_SPACES_REGEX + FROM_REGEX
				+ REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_WHERE_CLAUSE_REGEX + OPTIONAL_SPACES_REGEX
				+ END_SYMBOL;

		public static final String INSERT_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + INSERT_REGEX
				+ REQUIRED_SPACES_REGEX + INTO_REFEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + REQUIRED_SPACES_REGEX
				+ VALUES_REGEX + REQUIRED_SPACES_REGEX + MULTIPLE_ROW_REGEX + OPTIONAL_SPACES_REGEX + END_SYMBOL;

		public static final String UPDATE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + UPDATE_REGEX
				+ REQUIRED_SPACES_REGEX + PLACEHOLDER + REQUIRED_SPACES_REGEX + SET_REGEX + REQUIRED_SPACES_REGEX
				+ PLACEHOLDER + OPTIONAL_SPACES_REGEX + EQUAL_SYMBOL + OPTIONAL_SPACES_REGEX + VALUE_PLACEHOLDER
				+ OPTIONAL_WHERE_CLAUSE_REGEX + OPTIONAL_SPACES_REGEX + END_SYMBOL;

		public static final String DELETE_QUERY_REGEX = START_SYMBOL + OPTIONAL_SPACES_REGEX + DELETE_REGEX
				+ REQUIRED_SPACES_REGEX + FROM_REGEX + REQUIRED_SPACES_REGEX + PLACEHOLDER + OPTIONAL_WHERE_CLAUSE_REGEX
				+ OPTIONAL_SPACES_REGEX + END_SYMBOL;
	}
}
