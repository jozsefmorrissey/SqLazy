package com.generate.parce.query;

import com.generate.parce.QueryParcerAbs;
import com.generate.parce.field.TagFieldParcer;
import com.generate.parce.parameter.TagParameterParcer;

public class TagQueryParcer extends QueryParcerAbs
{
	public TagQueryParcer()
	{
		super.setColParcer(new TagFieldParcer());
		super.setParamParcer(new TagParameterParcer());
		setTagName("query");
	}
}
