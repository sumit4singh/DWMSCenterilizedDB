package com.centdb.module5;

import java.io.File;
import java.io.FilenameFilter;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONObject;
import com.google.gson.*;

import com.centdb.constants.DatabaseConstants;
import com.centdb.util.Utility;

public class DataModelling {
	private File database;
	private List<File> metaDataTables;

	public DataModelling(String database) {
		this.database = getDatabase(database);
		this.metaDataTables = getMetaDataTables(this.database);
	}

	private File getDatabase(String database) {
		return new File(DatabaseConstants.DATABASE_PATH + "/" + database);
	}

	private List<File> getMetaDataTables(File database) {
		File[] files = database.listFiles((FilenameFilter) (database1, name) -> name.endsWith(".metadata"));
		Arrays.sort(files);
		return new ArrayList<>(Arrays.asList(files));
	}

	public static void showDatabases() {
		File exportsDir = new File(DatabaseConstants.DATABASE_PATH);
		for (File file : exportsDir.listFiles()) {
			if (file.isDirectory()) {
				System.out.println(file.getName());
			}
		}
	}

	public String generateERD() {
		JSONObject tableObject = new JSONObject();
		metaDataTables = getMetaDataTables(this.database);
		for (File metaDataTable : metaDataTables) {
			List<String> tableRows = Utility.readFileByLines(metaDataTable);

			String[] metaDataTableColumns = tableRows.get(0).split("\\|");
			tableRows.remove(0);

			Map<Integer, Map<String, String>> metaDataTableContent = new HashMap<Integer, Map<String, String>>();

			for (int i = 0; i < tableRows.size(); i++) {

				String[] tableRowData = tableRows.get(i).split("\\|");
				Map<String, String> tableRowContent = new HashMap<String, String>();

				for (int j = 0; j < tableRowData.length; j++) {
					tableRowContent.put(metaDataTableColumns[j], tableRowData[j]);
				}
				metaDataTableContent.put(i, tableRowContent);
			}

			JSONObject tableObjectValue = new JSONObject();

			JSONObject columnsObject = new JSONObject();
			JSONObject relationsObject = new JSONObject();
			for (Map.Entry<Integer, Map<String, String>> mapElement : metaDataTableContent.entrySet()) {

				int index = mapElement.getKey();
				Map<String, String> mapElementRow = mapElement.getValue();
				columnsObject.put(mapElementRow.get("column"), mapElementRow.get("type"));

				if (mapElementRow.get("foreign_key_table") != null) {
					relationsObject.put(mapElementRow.get("foreign_key_table"), mapElementRow.get("foreign_key_col"));
				}
			}
			tableObjectValue.put("columns", columnsObject);
			if (!relationsObject.isEmpty())
				tableObjectValue.put("relations", relationsObject);
			String tableName = metaDataTable.getName().split(".metadata")[0];
			tableObject.put(tableName, tableObjectValue);
		}

		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		JsonElement je = JsonParser.parseString(tableObject.toString());
		String prettyJsonString = gson.toJson(je);
		Utility.writeTofile(
				Paths.get(DatabaseConstants.DATABASE_PATH, this.database.getName(), "erd.txt").toString(),
				prettyJsonString);
		System.out.println("ERD created: " + database.getName() + ".txt");

		return prettyJsonString;
	}

}
