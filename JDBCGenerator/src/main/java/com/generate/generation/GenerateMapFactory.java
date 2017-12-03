package com.generate.generation;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Modifier;

import org.reflections.Reflections;

import com.dataAccess.util.GenUtil;
import com.generate.marker.ObjectMap;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateMapFactory
{
	private JdbcTypeWrapper jdbcType;

	public GenerateMapFactory(JdbcTypeWrapper jdbcType) throws IOException
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
		Set<Class<?>> maps = r.getTypesAnnotatedWith(ObjectMap.class);
		
		for(Class<?> m : maps)
		{
			if(!m.getSimpleName().equals("DAOMapAbs"))
			{
				FieldSpec.Builder field = FieldSpec.builder(m, m.getSimpleName(), Modifier.PRIVATE)
					.initializer("new $T()", m);
				typeBuilder.addField(field.build());
				GenUtil.addGetterAndSetter(typeBuilder, m.getSimpleName(), m);
			}
		}

		GenUtil.buildAndSave(jdbcType.getPackage("factory"), typeBuilder);
	}

	private TypeSpec.Builder defineMapFactory()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getMapFactoryName()).addModifiers(Modifier.PUBLIC);
		return typeBuilder;
	}
}
