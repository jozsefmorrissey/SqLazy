package com.generate.parce.query;

import com.generate.parce.QueryParcerAbs;
import com.generate.parce.field.DefaultFieldParcer;
import com.generate.parce.parameter.DefaultParameterParser;

public class DefaultTagQueryParcer extends QueryParcerAbs
{
	public DefaultTagQueryParcer()
	{
		super.setColParcer(new DefaultFieldParcer());
		super.setParamParcer(new DefaultParameterParser());
		setTagName("query");
	}
}
