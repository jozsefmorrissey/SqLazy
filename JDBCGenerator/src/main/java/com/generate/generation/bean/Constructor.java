package com.generate.generation.bean;

import java.util.List;

import com.dataAccess.bean.Parameter;

public class Constructor
{
	List<Parameter> params;
	public Constructor(List<Parameter> params)
	{
		super();
		this.params = params;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + ((params == null) ? 0 : params.hashCode());
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Constructor other = (Constructor) obj;

		if (params == null)
		{
			if (other.params != null)
				return false;
		} else if (!params.equals(other.params))
			return false;
		return true;
	}

	public List<Parameter> getParams()
	{
		return params;
	}
	public void setParams(List<Parameter> params)
	{
		this.params = params;
	}
}
