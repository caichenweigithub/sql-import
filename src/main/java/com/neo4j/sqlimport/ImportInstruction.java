package com.neo4j.sqlimport;

import java.util.Map;

public class ImportInstruction {

	private static final String COMMA = ",";
	private final String aggregationNodeName;
	private final Field[] names;
	private final Map<Field, String> indexes;
	private final String statementStart;

	public ImportInstruction(String aggregationNodeName, String statementStart,
			Field[] names, Map<Field, String> indexes) {
		this.aggregationNodeName = aggregationNodeName;
		this.statementStart = statementStart;
		this.names = names;
		this.indexes = indexes;
	}

	public String getAggregationNodeName() {
		return aggregationNodeName;
	}


	public Field[] getNames() {
		return names;
	}

	public Map<Field, String> getIndexes() {
		return indexes;
	}

	public String getStatementStart() {
		return statementStart;
	}

	public String[] parse(String substring) {
		String[] values = new String[names.length];
		try {
		String line = substring.trim();

		for (int i = 0; i < names.length; i++) {
			StringBuffer value = new StringBuffer();
			Field field = names[i];
			boolean lastField = (i==names.length-1);
			switch (field.type) {
			case INTEGER:
				int indexOf = line.indexOf(lastField?");":COMMA, 2);
				value.append(line.subSequence(1, indexOf));
				line = line.substring(indexOf);
				break;
			case STRING:
				int indexOf2 = line.indexOf(lastField?"');":"',", 2);
				value.append(line.substring(2, indexOf2));
				line = line.substring(indexOf2+1);
				break;
			case INTEGER_AS_STRING:
				int indexOf3 = line.indexOf(lastField?"');":"',", 2);
				value.append(line.substring(2, indexOf3));
				line = line.substring(indexOf3+1);
				break;
			}
			values[i] = value.toString();
		}
		} catch (Exception e) {
			System.out.println("Could not parse " + substring);
			//e.printStackTrace();
		}
		return values;

	}
}
