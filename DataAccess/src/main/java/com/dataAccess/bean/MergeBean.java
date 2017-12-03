package com.dataAccess.bean;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import com.dataAccess.marker.Merge;
	
@SuppressWarnings("unchecked")
public abstract class MergeBean<T extends MergeBean<T>> implements Merge<T>, Comparable<T>
{	
	//TODO: Split up into multiple objects
	public int compareTo(T other)
	{
		if(other == null)
			return -1;
		
		return this.hashCode() - other.hashCode();
	}
	
	public List<T> merge(Collection<T> collect1, Collection<T> collect2)
	{
		List<T> mergedList = new ArrayList<T>();
		
		List<T> copy1 = new ArrayList<T>(collect1);
		List<T> copy2 = new ArrayList<T>(collect2);
		boolean subtract = false;
		for(int i = 0; indexInBounds(i, copy1); i++)
		{
			subtract = false;
			for(int j = 0; !subtract && indexInBounds(j, copy2, i, copy1); j++)
			{
				if(indexInBounds(j, copy2, i, copy1))
				{
					T one = copy1.get(i);
					T two = copy2.get(j);
					
					if(one != null && one.shouldMerge(two))
					{
						mergedList.add(one.merge(two));
						copy1.remove(one);
						copy2.remove(two);
						
						subtract = true;
						
						i--;
						j--;
					}
				}
			}
		}
		
		addRemaining(mergedList, copy1);
		addRemaining(mergedList, copy2);
		
		Collections.sort(mergedList);
		
		return mergedList;
	}

	private <U> boolean indexInBounds(int index1, Collection<U> collection1,
				int index2, Collection<U> collection2) {
		return indexInBounds(index1, collection1) && 
				indexInBounds(index2, collection2);
	}
	
	private <U> boolean indexInBounds(int index, Collection<U> collection) {
		return index < collection.size() && index >= -1;
	}
	
	private void addRemaining(List<T> mergedList, List<T> remaining)
	{
		for(T obj : remaining)
		{
			if(obj != null)
				mergedList.add(obj.merge((T)null));
		}
	}
	public <U> U mergeParam(U field1, U field2)
	{
		if(isEmpty(field1) && isEmpty(field2))
			return getReturnValue(field1);	

		if(!isEmpty(field1) && isEmpty(field2))
			return field1;
		
		if(isEmpty(field1) && !isEmpty(field2))
			return field2;
		
		return getTieBreaker(field1, field2);
	}

	private boolean isEmpty(Object field1)
	{
		if(field1 == null)
			return true;
				
		if(field1.getClass().equals(String.class) && stringIsEmpty(field1))
			return true;
		
		if(Integer.class.isInstance(field1) && integerIsEmpty(field1))
				return true;

		if(Long.class.isInstance(field1) && longIsEmpty(field1))
				return true;
		
		if(Short.class.isInstance(field1) && shortIsEmpty(field1))
			return true;
		
		if(Boolean.class.isInstance(field1) && booleanIsEmpty(field1))
			return true;
		
		return false;
	}
	
	private <U> U getReturnValue(U field1)
	{
		if(field1 == null)
			return null;
		
		if(field1.getClass().equals(String.class))
			return stringGetValue((String)field1);
		
		if(Integer.class.isInstance(field1))
				return integerGetValue((Integer)field1);

		if(Long.class.isInstance(field1))
				return longGetValue((Long)field1);
		
		if(Short.class.isInstance(field1))
				return shortGetValue((Short)field1);

		if(Float.class.isInstance(field1))
			return floatGetValue((Float)field1);
	
		if(Double.class.isInstance(field1))
			return doubleGetValue((Double)field1);
	
		if(Boolean.class.isInstance(field1))
			return booleanGetValue((Boolean)field1);
		
		if(Character.class.isInstance(field1))
			return characterGetValue((Character)field1);
		
		return null;
	}
	
	private <U> U getTieBreaker(U field1, U field2)
	{
		if(field1.getClass().equals(String.class))
			return stringGetTieBreaker((String)field1, (String)field2);
		
		if(Integer.class.isInstance(field1))
				return integerGetTieBreaker((Integer)field1, (Integer)field2);

		if(Long.class.isInstance(field1))
				return longGetTieBreaker((Long)field1, (Long)field2);
		
		if(Short.class.isInstance(field1))
				return shortGetTieBreaker((Short)field1, (Short)field2);

		if(Float.class.isInstance(field1))
			return floatGetTieBreaker((Float)field1, (Float)field2);
	
		if(Double.class.isInstance(field1))
			return doubleGetTieBreaker((Double)field1, (Double)field2);
	
		if(Boolean.class.isInstance(field1))
			return booleanGetTieBreaker((Boolean)field1, (Boolean)field2);
		
		if(Character.class.isInstance(field1))
			return characterGetTieBreaker((Character)field1, (Character)field2);
		
		return null;
	}

	public <U> U characterGetTieBreaker(Character field1, Character field2)
	{
		return (U) new Character((char) (field1 + field2));
	}

	public <U> U booleanGetTieBreaker(Boolean field1, Boolean field2)
	{
		return (U) new Boolean(field1 && field2);
	}

	public <U> U doubleGetTieBreaker(Double field1, Double field2)
	{
		return (U) new Double(field1 + field2);
	}

	public <U> U floatGetTieBreaker(Float field1, Float field2)
	{
		return (U) new Float(field1 + field2);
	}

	public <U> U shortGetTieBreaker(Short field1, Short field2)
	{
		return (U) new Short((short) (field1 + field2));
	}

	public <U> U longGetTieBreaker(Long field1, Long field2)
	{
		return (U) new Long(field1 + field2);
	}

	public <U> U integerGetTieBreaker(Integer field1, Integer field2)
	{
		return (U) new Integer(field1 + field2);
	}

	public <U> U stringGetTieBreaker(String field1, String field2)
	{
		return (U) (field1 + field2);
	}

	public <U> U floatGetValue(Float field1)
	{
		return (U) new Float(-1);
	}

	public <U> U doubleGetValue(Double field1)
	{
		return (U) new Double(-1);
	}

	public <U> U stringGetValue(String field1)
	{
		return null;
	}

	public <U> U integerGetValue(Integer field1)
	{
		return (U) new Integer(-1);
	}

	public <U> U longGetValue(Long field1)
	{
		return (U) new Long(-1);
	}

	public <U> U shortGetValue(Short field1)
	{
		return (U) new Short((short) -1);
	}

	public <U> U characterGetValue(Character field1)
	{
		return (U) new Character('`');
	}

	public <U> U booleanGetValue(Boolean field1)
	{
		// TODO Auto-generated method stub
		return null;
	}



	public boolean booleanIsEmpty(Object field1)
	{
		return (Boolean)field1 == false;
	}

	public boolean shortIsEmpty(Object field1)
	{
		return (Short)field1 == -1;
	}

	public boolean longIsEmpty(Object field1)
	{
		return (Long)field1 == -1;
	}

	public boolean integerIsEmpty(Object field1)
	{
		return (Integer)field1 == -1;
	}

	public boolean stringIsEmpty(Object field1)
	{
		if(field1 == null)
			return true;
		
		return "".equals(field1.toString());
	}
}
