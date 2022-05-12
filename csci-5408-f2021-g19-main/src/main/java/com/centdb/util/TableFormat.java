package com.centdb.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

/**
 * @author Jay Patel
 */
public class TableFormat {

	public static final Boolean LEFT_JUSTIFIED_ROWS = Boolean.TRUE;
	public static final Integer MAX_WIDTH = 50;

	public static void printTable(List<String[]> table) {
		List<String[]> finalTableList = createFinalTableList(table, LEFT_JUSTIFIED_ROWS, MAX_WIDTH);
		String[][] finalTable = createFinalTable(finalTableList);
		Map<Integer, Integer> columnLengths = findColumnLengths(finalTable);
		String formatString = createTableFormat(LEFT_JUSTIFIED_ROWS, columnLengths);
		String lineBreak = createLineBreak(columnLengths);
		printFinalTable(lineBreak, finalTable, formatString);
	}

	private static List<String[]> createFinalTableList(List<String[]> table, Boolean leftJustifiedRows,
			Integer maxWidth) {
		List<String[]> finalTableList = new ArrayList<>();
		for (String[] row : table) {
			boolean needExtraRow = false;
			int splitRow = 0;
			int cnt = 0;
			do {
				cnt++;
				needExtraRow = false;
				String[] newRow = new String[row.length];
				for (int i = 0; i < row.length; i++) {
					// If data is less than max width, use that as it is.
					if (row[i].length() < maxWidth) {
						newRow[i] = splitRow == 0 ? row[i] : "";
					} else if ((row[i].length() > (splitRow * maxWidth))) {
						// If data is more than max width, then crop data at maxwidth.
						// Remaining cropped data will be part of next row.
						int end = row[i].length() > ((splitRow * maxWidth) + maxWidth)
								? (splitRow * maxWidth) + maxWidth
								: row[i].length();
						newRow[i] = row[i].substring((splitRow * maxWidth), end);
						needExtraRow = true;
					} else {
						newRow[i] = "";
					}
				}
				finalTableList.add(newRow);
				if (needExtraRow) {
					splitRow++;
				}
			} while (needExtraRow);
			if (cnt == 1) {
				String[] newRow = new String[row.length];
				for (int i = 0; i < row.length; i++)
					newRow[i] = "";
				finalTableList.add(newRow);
			}
		}
		return finalTableList;
	}

	private static String[][] createFinalTable(List<String[]> finalTableList) {
		String[][] finalTable = new String[finalTableList.size()][finalTableList.get(0).length];
		for (int i = 0; i < finalTable.length; i++) {
			finalTable[i] = finalTableList.get(i);
		}
		return finalTable;
	}

	private static Map<Integer, Integer> findColumnLengths(String[][] finalTable) {
		Map<Integer, Integer> columnLengths = new HashMap<>();
		Arrays.stream(finalTable).forEach(a -> Stream.iterate(0, (i -> i < a.length), (i -> ++i)).forEach(i -> {
			if (columnLengths.get(i) == null) {
				columnLengths.put(i, 0);
			}
			if (columnLengths.get(i) < a[i].length()) {
				columnLengths.put(i, a[i].length());
			}
		}));
		return columnLengths;
	}

	private static String createTableFormat(Boolean leftJustifiedRows, Map<Integer, Integer> columnLengths) {
		final StringBuilder formatString = new StringBuilder("");
		String flag = leftJustifiedRows ? "-" : "";
		columnLengths.entrySet().stream().forEach(e -> formatString.append("| %" + flag + e.getValue() + "s "));
		formatString.append("|\n");
		return formatString.toString();
	}

	private static String createLineBreak(Map<Integer, Integer> columnLengths) {
		String line = columnLengths.entrySet().stream().reduce("", (ln, b) -> {
			String templn = "+-";
			templn = templn + Stream.iterate(0, (i -> i < b.getValue()), (i -> ++i)).reduce("", (ln1, b1) -> ln1 + "-",
					(a1, b1) -> a1 + b1);
			templn = templn + "-";
			return ln + templn;
		}, (a, b) -> a + b);
		line = line + "+\n";
		return line;
	}

	private static void printFinalTable(final String lineBreak, String[][] finalTable, String formatString) {
		System.out.print(lineBreak);
		Arrays.stream(finalTable).limit(1)
				.forEach(a -> System.out.print(new Formatter().format(formatString, (Object[]) a).toString()));
		System.out.print(lineBreak);

		Stream.iterate(1, (i -> i < finalTable.length), (i -> ++i)).forEach(a -> System.out
				.print(new Formatter().format(formatString.toString(), (Object[]) finalTable[a]).toString()));

		System.out.print(lineBreak);
	}

	public static String[] createTableRow(String... cells) {
		return cells;
	}
}
