package com.generate.parce.field;

import java.util.ArrayList;
import java.util.List;

import com.dataAccess.bean.Field;
import com.generate.parce.TextContentParcerAbs;

public class SqlStringFieldParcer extends TextContentParcerAbs<Field>
{

	@Override
	protected List<Field> parceString(String query)
	{
	    query = query.replaceAll("//s+", "");
	    query = query.toUpperCase();
	 
	    int afterSelect = query.indexOf("SELECT") + 6;
	    int startFrom = query.indexOf("FROM");
	    
	    String commaSepFields = query.substring(afterSelect, startFrom);
	    String[] fieldArr = commaSepFields.split(",");
	    
	    List<Field> retMap = new ArrayList<Field>();
	    for(String fieldName : fieldArr) {
	      if(!fieldName.isEmpty())
	        retMap.add(new Field(fieldName.trim()));
	    }
	    
	    return retMap;
	}
	
}
