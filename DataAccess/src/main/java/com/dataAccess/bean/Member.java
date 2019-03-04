package com.dataAccess.bean;

import java.util.List;

import lombok.Data;

@Data
public class Member {
	private String name;
	private String format;
	private List<Class<?>> objectArgs;
	
	public Member (String name, String format, List<Class<?>> objectArgs) {
		this.name = name;
		this.format = format;
		this.objectArgs = objectArgs;
	}

}
