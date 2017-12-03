package com.generate.parce.query;

import com.generate.parce.QueryParcerAbs;
import com.generate.parce.field.SqlStringFieldParcer;
import com.generate.parce.parameter.SqlStringParamParcer;

public class SqlStringQueryParcer extends QueryParcerAbs
{
	public SqlStringQueryParcer()
	{
		super.setColParcer(new SqlStringFieldParcer());
		super.setParamParcer(new SqlStringParamParcer());
		setTagName("query");
	}
}
