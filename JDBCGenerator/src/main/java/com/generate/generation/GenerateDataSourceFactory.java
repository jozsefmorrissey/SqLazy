package com.generate.generation;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Modifier;
import javax.sql.DataSource;

import org.reflections.Reflections;

import com.dataAccess.util.GenUtil;
import com.generate.marker.DataSourceObject;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDataSourceFactory
{
	private JdbcTypeWrapper jdbcType;

	public GenerateDataSourceFactory(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
	}
	
	public void define()
	{
		TypeSpec.Builder typeBuilder = defineMapFactory();

		GenUtil.buildAndSave(jdbcType.getPackage("factory"), typeBuilder);
	}

	public void generate() throws IOException
	{
		TypeSpec.Builder typeBuilder = defineMapFactory();

		
		Reflections r = new Reflections();
		
		Set<Class<? extends DataSource>> set = r.getSubTypesOf(DataSource.class);;
		
		for(Class<?> clazz : set)
		{
			//TODO: organize and fix wrappers.
			if(jdbcType.getDataAccessClass(clazz.getSimpleName()) != null)
			{
				FieldSpec.Builder field = FieldSpec.builder(clazz, clazz.getSimpleName(), Modifier.PRIVATE)
						.initializer("new $T()", clazz);
				typeBuilder.addField(field.build());
				GenUtil.addGetterAndSetter(typeBuilder, clazz.getSimpleName(), clazz);
			}

		}

		GenUtil.buildAndSave(jdbcType.getPackage(jdbcType.getFactoryPkg()), typeBuilder);
	}

	private TypeSpec.Builder defineMapFactory()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getDataSourceFactoryName()).addModifiers(Modifier.PUBLIC);
		return typeBuilder;
	}
}
