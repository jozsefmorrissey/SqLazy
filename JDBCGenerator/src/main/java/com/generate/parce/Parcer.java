package com.generate.parce;

import java.util.List;

import com.generate.exception.XmlParceException;

public interface Parcer<T, U>
{
	public T parce(U obj) throws XmlParceException;
	public List<T> parceAll(U obj) throws XmlParceException;
}
