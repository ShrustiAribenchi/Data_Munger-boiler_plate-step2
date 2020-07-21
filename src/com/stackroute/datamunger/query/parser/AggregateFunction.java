package com.stackroute.datamunger.query.parser;

/* This class is used for storing name of field, aggregate function for 
 * each aggregate function
 * generate getter and setter for this class,
 * Also override toString method
 * */

public class AggregateFunction {
	private String field, aggregateFunc;
	
	public AggregateFunction(String field, String function) {
		this.field = field;
		this.aggregateFunc = function;
	}
	public String getAggregateFunction() {
		return this.toString();
	}
	public void setAggregateFunction(String field, String aggFunction) {
		this.field = field;
		this.aggregateFunc = aggFunction;
		
	}
	public String toString() {
		return this.field +""+ this.aggregateFunc;
	}

}
