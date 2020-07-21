package com.stackroute.datamunger.query.parser;

import java.util.List;

/* 
 * This class will contain the elements of the parsed Query String such as conditions,
 * logical operators,aggregate functions, file name, fields group by fields, order by
 * fields, Query Type
 * */

public class QueryParameter {
	private String fileName;

	public String getFileName() {
		return this.fileName;
	}

	private String baseQuery;

	public String getBaseQuery() {
		return this.baseQuery;
	}

	private List<Restriction> getConditions;

	public List<Restriction> getRestrictions() {
		return this.getGetConditions();
	}

	private List<String> getLogical;

	public List<String> getLogicalOperators() {
		return this.getGetLogical();
	}

	private List<String> getField;

	public List<String> getFields() {
		return this.getGetField();
	}

	private List<AggregateFunction> getAggregateFunction;

	public List<AggregateFunction> getAggregateFunctions() {
		return this.getGetAggregateFunction();
	}

	private List<String> getGroupBy;

	public List<String> getGroupByFields() {
		return this.getGetGroupBy();
	}

	private List<String> getOrderBy;

	public List<String> getOrderByFields() {
		return this.getGetOrderBy();
	}

	public String setFileName(String fileName) {
		this.fileName = fileName;
		return fileName;
	}

	public void setBaseQuery(String baseQuery) {
		this.baseQuery = baseQuery;
	}

	public List<String> getGetOrderBy() {
		return getOrderBy;
	}

	public void setGetOrderBy(List<String> getOrderBy) {
		this.getOrderBy = getOrderBy;
	}

	public List<String> getGetGroupBy() {
		return getGroupBy;
	}

	public void setGetGroupBy(List<String> getGroupBy) {
		this.getGroupBy = getGroupBy;
	}

	public List<String> getGetField() {
		return getField;
	}

	public void setGetField(List<String> getField) {
		this.getField = getField;
	}

	public List<String> getGetLogical() {
		return getLogical;
	}

	public void setGetLogical(List<String> getLogical) {
		this.getLogical = getLogical;
	}

	public List<AggregateFunction> getGetAggregateFunction() {
		return getAggregateFunction;
	}

	public void setGetAggregateFunction(List<AggregateFunction> getAggregateFunction) {
		this.getAggregateFunction = getAggregateFunction;
	}

	public List<Restriction> getGetConditions() {
		return getConditions;
	}

	public void setGetConditions(List<Restriction> getConditions) {
		this.getConditions = getConditions;
	}
}
