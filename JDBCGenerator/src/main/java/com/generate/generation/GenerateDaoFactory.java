package com.generate.generation;

import java.io.IOException;
import java.util.Set;

import javax.lang.model.element.Modifier;

import org.reflections.Reflections;

import com.dataAccess.util.GenUtil;
import com.dataAccess.util.StringUtil;
import com.generate.marker.JDBC;
import com.generate.parce.bean.Wrapper.JdbcTypeWrapper;
import com.squareup.javapoet.FieldSpec;
import com.squareup.javapoet.TypeSpec;

public class GenerateDaoFactory
{
	private JdbcTypeWrapper jdbcType;

	public GenerateDaoFactory(JdbcTypeWrapper jdbcType) throws IOException
	{
		super();
		this.jdbcType = jdbcType;
	}
	
	public void define()
	{
		TypeSpec.Builder typeBuilder = defineFactory();

		GenUtil.buildAndSave(jdbcType.getPackage("factory"), typeBuilder);
	}

	public void generate() throws IOException
	{
		TypeSpec.Builder typeBuilder = defineFactory();

		
		Reflections r = new Reflections();
		Set<Class<?>> maps = r.getTypesAnnotatedWith(JDBC.class);
		
		Class<?> clazz = jdbcType.getDataAccessClass(jdbcType.getDataSourceFactoryName());
		String daoFactory = StringUtil.unCapitalizeIndex(jdbcType.getDataSourceFactoryName(), 0);
		FieldSpec.Builder field = FieldSpec.builder(clazz, daoFactory, Modifier.PRIVATE)
				.initializer("new $L()", clazz.getSimpleName());
		typeBuilder.addField(field.build());
		
		for(Class<?> m : maps)
		{
			Class<?> c = jdbcType.getDataSource(null);
			Class<?> interfc = jdbcType.getRootDao(m);
			field = FieldSpec.builder(interfc, interfc.getSimpleName(), Modifier.PRIVATE)
				.initializer("new $T($L.get$L())", m, daoFactory, jdbcType.getDataSource(null).getSimpleName());
			typeBuilder.addField(field.build());
			GenUtil.addGetterAndSetter(typeBuilder, interfc.getSimpleName(), interfc);
		}

		GenUtil.buildAndSave(jdbcType.getPackage("factory"), typeBuilder);
	}

	private TypeSpec.Builder defineFactory()
	{
		TypeSpec.Builder typeBuilder = TypeSpec.classBuilder(jdbcType.getDaoFactoryName()).addModifiers(Modifier.PUBLIC);
		return typeBuilder;
	}
}
