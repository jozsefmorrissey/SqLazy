package com.generate.parce.parameter;

import java.util.ArrayList;
import java.util.List;

import com.dataAccess.bean.Parameter;
import com.dataAccess.util.StringUtil;
import com.generate.parce.TextContentParcerAbs;

public class SqlStringParamParcer extends TextContentParcerAbs<Parameter>
{
	private String varIndicator = "$$";
	private String reverseVarIndicator = "$$";
	private String separator = ",";

	@Override
	protected List<Parameter> parceString(String sqlStr)
	{
	    int varStartIndex = 0;
	    int varEndIndex = -1;
	    
	    List<Parameter> parameters = new ArrayList<Parameter>();
	    boolean found = true;
	    while (found) {
	      //TODO: clean this mess up!
	      varStartIndex = varStartIndex + sqlStr.substring(varStartIndex).indexOf(varIndicator);
	      varEndIndex = varStartIndex + varIndicator.length() + sqlStr.substring(varStartIndex + varIndicator.length()).indexOf(reverseVarIndicator);
	      if(varStartIndex < varEndIndex - varIndicator.length() + 1) {
	        String paramName = sqlStr.substring(varStartIndex + varIndicator.length(), varEndIndex);
	        parameters.add(new Parameter(paramName));
	      }
	      else
	    	  found = false;
	      
	      varStartIndex = varEndIndex + varIndicator.length();
	    }

	    return parameters;
	}

	protected String getIndicator()
	{
		return varIndicator;
	}

	protected void setIndicator(String varIndicator)
	{
		this.varIndicator = varIndicator;
	    reverseVarIndicator = StringUtil.reverse(varIndicator);
	}

	protected String getSeparator()
	{
		return separator;
	}

	protected void setSeparator(String seporator)
	{
		this.separator = seporator;
	}
}
