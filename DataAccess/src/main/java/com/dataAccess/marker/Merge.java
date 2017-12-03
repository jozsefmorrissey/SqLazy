package com.dataAccess.marker;

import java.util.Collection;
import java.util.List;

public interface Merge<T>
{
	public T merge(T obj);
	public List<T> merge(Collection<T> collect1, Collection<T> collect2);
	public boolean shouldMerge(T obj);
}
