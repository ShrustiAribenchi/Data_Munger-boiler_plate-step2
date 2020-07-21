package com.stackroute.datamunger.query.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/*There are total 4 DataMungerTest file:
 * 
 * 1)DataMungerTestTask1.java file is for testing following 4 methods
 * a)getBaseQuery()  b)getFileName()  c)getOrderByClause()  d)getGroupByFields()
 * 
 * Once you implement the above 4 methods,run DataMungerTestTask1.java
 * 
 * 2)DataMungerTestTask2.java file is for testing following 2 methods
 * a)getFields() b) getAggregateFunctions()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask2.java
 * 
 * 3)DataMungerTestTask3.java file is for testing following 2 methods
 * a)getRestrictions()  b)getLogicalOperators()
 * 
 * Once you implement the above 2 methods,run DataMungerTestTask3.java
 * 
 * Once you implement all the methods run DataMungerTest.java.This test case consist of all
 * the test cases together.
 */

public class QueryParser {

	private QueryParameter queryParameter = new QueryParameter();

	/*
	 * This method will parse the queryString and will return the object of
	 * QueryParameter class
	 */
	public QueryParameter parseQuery(String queryString) {
		queryParameter.setFileName(extractFileName(queryString));
		queryParameter.setBaseQuery(extractBaseQuery(queryString));
		queryParameter.setGetOrderBy(extractOrderByFields(queryString));
		queryParameter.setGetGroupBy(extractGroupByFields(queryString));
		queryParameter.setGetField(extractFields(queryString));
		queryParameter.setGetLogical(extractLogicalOperators(queryString));
		queryParameter.setGetAggregateFunction(extractAggregate(queryString));
		queryParameter.setGetConditions(extractConditions(queryString));
		return queryParameter;
	}

	/*
	 * Extract the name of the file from the query. File name can be found after the
	 * "from" clause.
	 */
	
	public String extractFileName(String queryString) {
		queryString = queryString.toLowerCase(Locale.US);
		final String file = queryString;
		final int indexOffrom = file.indexOf("from");
		final int indexOfcsv = file.indexOf("csv");
		final String filename = file.substring(indexOffrom + 5, indexOfcsv + 3);
		return queryParameter.setFileName(filename);
	}

	/*
	 * 
	 * Extract the baseQuery from the query.This method is used to extract the
	 * baseQuery from the query string. BaseQuery contains from the beginning of the
	 * query till the where clause
	 */
	
	public String extractBaseQuery(String queryString) {
		String base = null;
		queryString = queryString.toLowerCase(Locale.US);
		if (queryString.contains("where")) {
			final String[] baseQuery = queryString.split(" where");
			base = baseQuery[0];
		} else if (queryString.contains("group by")) {
			final String[] baseQuery = queryString.split(" group by");
			base = baseQuery[0];
		} else if (queryString.contains("order by")) {
			final String[] baseQuery = queryString.split(" order by");
			base = baseQuery[0];
		} else {
			base = "";
		}
		return base;
	}

	/*
	 * extract the order by fields from the query string. Please note that we will
	 * need to extract the field(s) after "order by" clause in the query, if at all
	 * the order by clause exists. For eg: select city,winner,team1,team2 from
	 * data/ipl.csv order by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one order by fields.
	 */
	public List<String> extractOrderByFields(String queryString) {
		List<String> orderByFields = null;
		String[] tempArray;
		if (queryString.contains("order by")) {
			orderByFields = new ArrayList<String>();
			tempArray = queryString.trim().split(" order by ");
			final String[] orderByArray = tempArray[1].trim().split(",");
			if (orderByArray.length == 1) {
				orderByFields.add(orderByArray[0]);
			} else {
				for (int i = 0; i < orderByArray.length; i++) {
					orderByFields.add(orderByArray[i]);
				}
			}
		}
		return orderByFields;

	}

	/*
	 * Extract the group by fields from the query string. Please note that we will
	 * need to extract the field(s) after "group by" clause in the query, if at all
	 * the group by clause exists. For eg: select city,max(win_by_runs) from
	 * data/ipl.csv group by city from the query mentioned above, we need to extract
	 * "city". Please note that we can have more than one group by fields.
	 */
	public List<String> extractGroupByFields(String queryString) {
		String[] groupTempArray;
		String[] groupByArray;
		List<String> groupByFields = null;

		if (queryString.contains("group by")) {
			groupByFields = new ArrayList<String>();
			groupTempArray = queryString.trim().split(" group by ");
			if (groupTempArray[1].contains("order by")) {
				final String[] groupOrderBySplit = groupTempArray[1].trim().split(" order by");
				groupByArray = groupOrderBySplit[0].trim().split(",");
				if (groupByArray.length == 1) {
					groupByFields.add(groupByArray[0]);
				} else {
					for (int i = 0; i < groupByArray.length; i++) {
						groupByFields.add(groupByArray[i]);
					}
				}
			}

			else {
				groupByArray = groupTempArray[1].trim().split(",");
				if (groupByArray.length == 1) {
					groupByFields.add(groupByArray[0]);
				}

				else {
					for (int i = 0; i < groupByArray.length; i++) {
						groupByFields.add(groupByArray[i]);
					}
				}
			}
		}
		return groupByFields;

	}

	/*
	 * Extract the selected fields from the query string. Please note that we will
	 * need to extract the field(s) after "select" clause followed by a space from
	 * the query string. For eg: select city,win_by_runs from data/ipl.csv from the
	 * query mentioned above, we need to extract "city" and "win_by_runs". Please
	 * note that we might have a field containing name "from_date" or "from_hrs".
	 * Hence, consider this while parsing.
	 */
	public List<String> extractFields(String queryString) {
		final List<String> field = new ArrayList<String>();
		final String file1 = queryString;
		final int indexOfselect = file1.indexOf("select ");
		final int indexOffrom1 = file1.indexOf(" from");
		String filename1 = file1.substring(indexOfselect + 7, indexOffrom1);
		// return filename;
		final String[] fields = filename1.split(",");
		for (int i = 0; i < fields.length; i++) {
			field.add(fields[i]);
		}
		return field;
	}

	/*
	 * Extract the conditions from the query string(if exists). for each condition,
	 * we need to capture the following: 1. Name of field 2. condition 3. value
	 * 
	 * For eg: select city,winner,team1,team2,player_of_match from data/ipl.csv
	 * where season >= 2008 or toss_decision != bat
	 * 
	 * here, for the first condition, "season>=2008" we need to capture: 1. Name of
	 * field: season 2. condition: >= 3. value: 2008
	 * 
	 * the query might contain multiple conditions separated by OR/AND operators.
	 * Please consider this while parsing the conditions.
	 * 
	 */
	public List<Restriction> extractConditions(String queryString) {
		List<Restriction> conditions = null;
		// queryString = queryString.toLowerCase();
		String[] whereQuery;
		// List<String> Cond = new ArrayList<String>();
		String tempString;
		String[] conditionQuery;
		String[] getCondition = null;
		if (queryString.contains("where")) {
			conditions = new ArrayList<Restriction>();
			whereQuery = queryString.trim().split("where ");
			if (whereQuery[1].contains("group by")) {
				conditionQuery = whereQuery[1].trim().split("group by");
				tempString = conditionQuery[0];
			} else if (whereQuery[1].contains("order by")) {
				conditionQuery = whereQuery[1].trim().split("order by");
				tempString = conditionQuery[0];
			} else {
				tempString = whereQuery[1];
			}
			getCondition = tempString.trim().split(" and | or ");
			// for (String s : getCondition) {
			// System.out.println(s.trim());
			// }
			String[] condSplit = null;
			if (getCondition != null) {
				for (int i = 0; i < getCondition.length; i++) {
					if (getCondition[i].contains("=")) {
						condSplit = getCondition[i].trim().split("\\W+");
						conditions.add(new Restriction(condSplit[0], condSplit[1], "="));
					} else if (getCondition[i].contains(">")) {
						condSplit = getCondition[i].trim().split("\\W+");
						conditions.add(new Restriction(condSplit[0], condSplit[1], ">"));
					} else if (getCondition[i].contains("<")) {
						condSplit = getCondition[i].trim().split("\\W+");
						conditions.add(new Restriction(condSplit[0], condSplit[1], "<"));
					}

				}
			}
			// Conditions.add(new Restriction(getCondition[0].substring(0, 6),
			// getCondition[0].substring(9, 12), getCondition[0].substring(7, 8)));

		}
		return conditions;

	}
	/*
	 * Extract the logical operators(AND/OR) from the query, if at all it is
	 * present. For eg: select city,winner,team1,team2,player_of_match from
	 * data/ipl.csv where season >= 2008 or toss_decision != bat and city =
	 * bangalore
	 * 
	 * The query mentioned above in the example should return a List of Strings
	 * containing [or,and]
	 */

	public List<String> extractLogicalOperators(String queryString) {
		List<String> logical = null;
		final String[] query = queryString.split(" ");
		String getLogic = "";
		String[] logicTemp;
		if (queryString.contains("where ")) {
			logical = new ArrayList<String>();
			for (int i = 0; i < query.length; i++) {
				if (query[i].matches("and|or|not")) {

					getLogic += query[i] + " ";
				}
			}
			logicTemp = getLogic.toString().split(" ");
			for (int i = 0; i < logicTemp.length; i++) {
				logical.add(logicTemp[i]);
			}
		}

		return logical;

	}

	/*
	 * Extract the aggregate functions from the query. The presence of the aggregate
	 * functions can determined if we have either "min" or "max" or "sum" or "count"
	 * or "avg" followed by opening braces"(" after "select" clause in the query
	 * string. in case it is present, then we will have to extract the same. For
	 * each aggregate functions, we need to know the following: 1. type of aggregate
	 * function(min/max/count/sum/avg) 2. field on which the aggregate function is
	 * being applied.
	 * 
	 * Please note that more than one aggregate function can be present in a query.
	 * 
	 * 
	 */
	public List<AggregateFunction> extractAggregate(String queryString) {
		// queryString = queryString.toLowerCase();
		final List<AggregateFunction> aggregate = new ArrayList<AggregateFunction>();
		// boolean state = false;
		// String getAggregate = "";
		final int selectIndex = queryString.toLowerCase(Locale.US).indexOf("select");
		final int fromIndex = queryString.toLowerCase(Locale.US).indexOf(" from");
		final String query = queryString.toLowerCase(Locale.US).substring(selectIndex + 7, fromIndex);
		String[] aggQuery = null;
		aggQuery = query.split(",");
		for (int i = 0; i < aggQuery.length; i++) {
			if (aggQuery[i].startsWith("max(") && aggQuery[i].endsWith(")")
					|| aggQuery[i].startsWith("min(") && aggQuery[i].endsWith(")")
					|| aggQuery[i].startsWith("avg(") && aggQuery[i].endsWith(")")
					|| aggQuery[i].startsWith("sum") && aggQuery[i].endsWith(")")) {
				aggregate.add(new AggregateFunction(aggQuery[i].substring(4, aggQuery[i].length() - 1),
						aggQuery[i].substring(0, 3)));
				// getAggregate += aggQuery[i] + " ";
				// state = true;
			} else if (aggQuery[i].startsWith("count(") && aggQuery[i].endsWith(")")) {
				aggregate.add(new AggregateFunction(aggQuery[i].substring(6, aggQuery[i].length() - 1),
						aggQuery[i].substring(0, 5)));
				// } else {
				// Aggregate = null;
				// }
			}

		}
		return aggregate;

	}
}