package com.generate.parce.bean.Wrapper;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.dataAccess.bean.Field;
import com.dataAccess.bean.JdbcType;
import com.dataAccess.bean.Query;
import com.dataAccess.enums.JAVA_TYPE;
import com.dataAccess.util.StringUtil;
import com.squareup.javapoet.ParameterizedTypeName;
import com.squareup.javapoet.TypeName;

public class JdbcTypeWrapper extends JdbcType
{
	private String beanPkg = "bean";
	private String daoPkg = "dao";
	private String mapPkg = "map";
	private String jdbcPkg = packageString(daoPkg,"jdbc");
	private String extractorPkg = "extractor";
	private String dataSource = "dataSource";

	private String[] packageExts;
	private String mapExt = "DAOMap";
	public String getExtractorPkg()
	{
		return extractorPkg;
	}

	public void setExtractorPkg(String extractorPkg)
	{
		this.extractorPkg = extractorPkg;
	}

	public String[] getPackageExts()
	{
		return packageExts;
	}

	public void setPackageExts(String[] packageExts)
	{
		this.packageExts = packageExts;
	}

	public String getMapExt()
	{
		return mapExt;
	}

	public void setMapExt(String mapExt)
	{
		this.mapExt = mapExt;
	}

	public String getFactoryPkg()
	{
		return factoryPkg;
	}

	public void setFactoryPkg(String factoryPkg)
	{
		this.factoryPkg = factoryPkg;
	}

	public String getImplPkg()
	{
		return implPkg;
	}

	public void setImplPkg(String implPkg)
	{
		this.implPkg = implPkg;
	}

	public String getFactorySuffix()
	{
		return factorySuffix;
	}

	public void setFactorySuffix(String factorySuffix)
	{
		this.factorySuffix = factorySuffix;
	}

	private String factoryPkg = "factory";
	private String implPkg = "impl";
	private String factorySuffix = "Factory";
	
	public JdbcTypeWrapper(String name, String abstractName, String pckage, List<Query> queries)
	{
		super(name, abstractName, pckage, queries);
		
		packageExts = new String[]{beanPkg, daoPkg, 
				mapPkg, jdbcPkg, extractorPkg, factoryPkg,
				dataSource};
	}

	public String getAbstractName()
	{
		String userName = super.getAbstractName();
		if(userName != null && !userName.isEmpty())
			return userName;

		return "GenerateDaoImplAbs";
	}
	
	public String getPackage(String extention)
	{
//		String userPackage = super.getPackage();
//		if(userPackage != null && !userPackage.isEmpty())
//			return packageString(super.getPackage(), extention);
		
		return packageString("com.dataAccess", extention);
	}
	
	public String getImplPackage(String extension)
	{
		return packageString(getPackage(extension), implPkg);
	}
	
	public String getPackage()
	{
		return getPackage("");
	}
	
	public static void main(String...args)
	{
		JdbcTypeWrapper j = new JdbcTypeWrapper(null, null, null, null);
		System.out.println(j.getDataAccessClass("ProviderServiceLocation"));
		System.out.println(new ArrayList<Integer>().getClass().isInstance(new ArrayList<String>()));
	}

	public Class<?> getClass(String className, Class<?> defaultClass)
	{
		Class<?> c = StringUtil.getClass(className);
		if(c != null)
			return c;		
		
		c = getDataAccessClass(className);
		if(c != null)
			return c;
		
		return defaultClass;
	}
	
	public Class<?> getDataAccessClass(String simpleClassName)
	{
		for(String pkg : packageExts)
		{
			Class<?> c = getDataAccessClass(pkg, simpleClassName);
			if(c != null)
				return c;
			
			c = getDataAccessClass("generated.".concat(pkg), simpleClassName);
			if(c != null)
				return c;
		}
		return null;
	}
	
	public Class<?> getDataAccessClass(String subPkg, String simpleClassName)
	{
		String className = packageString(getPackage(subPkg), simpleClassName);
		Class<?> nonGenClass = StringUtil.getClass(className);
		if(nonGenClass != null)
			return nonGenClass;
		
		className = packageString(getImplPackage(subPkg), simpleClassName);
		Class<?> genClass = StringUtil.getClass(className);
		if(genClass != null)
			return genClass;
		
		return null;
	}
	
	public String getAbstractNameFQ()
	{
		String userAbsName = packageString(getPackage(daoPkg), super.getAbstractName());
		if(StringUtil.getClass(userAbsName) != null)
			return userAbsName;

		if(StringUtil.getClass(packageString(getPackage(daoPkg),"GenerateDaoImplAbs")) != null)
			return packageString(getPackage(daoPkg),"GenerateDaoImplAbs");
		
		return null;
	}
	
	public String createFQName(String name, String subPkg) {
		return packageString(getPackage(subPkg), name);
	}
	
	public String getJdbcName()
	{
		return "JDBC".concat(super.getName()).concat("DAO");
	}

	public String getJdbcNameFQ()
	{
		return createFQName(getJdbcName(), jdbcPkg);
	}

	public String getDaoName()
	{
		return super.getName().concat("DAO");
	}
	
	public String getDaoNameFQ()
	{
		return createFQName(getDaoName(), daoPkg);
	}

	public String getMapName()
	{
		return getMapName(super.getName());
	}
	
	public String getMapName(String rootName)
	{
		return rootName.concat(mapExt );
	}
	
	public Class<?> getMapClass(String javaType)
	{
		Class<?> clazz = this.getClass(javaType, null);
		if(clazz != null)
			return clazz;
		
		String mapName = "";
		if(javaType != null)
			mapName = javaType.concat(mapExt);
		
		clazz = this.getClass(mapName, null);
		if(clazz != null)
			return clazz;
		
		clazz = JAVA_TYPE.getClass(javaType);
		if(clazz != null)
			mapName = clazz.getSimpleName().toString().concat(mapExt);
		
		return this.getClass(mapName, getClass("StringDAOMap", null));
	}
	
	public String getMapMethod(String javaType)
	{
		return "mapRow";
	}
	
	public String getMapMethod(Object javaType)
	{
		if(javaType instanceof Collection)
			return "mapResults";
		
		return "mapRow";
	}
	public String getMapNameFQ()
	{
		return createFQName(getMapName(), packageString(mapPkg, implPkg));
	}
	
	private String packageString(String...pkgs)
	{
		String retVal = "";
		for(String p : pkgs)
			retVal += p.concat(".");

		return retVal.substring(0, retVal.length() - 1);
	}

	public String getBeanName()
	{
		return super.getName();
	}
	
	public String getBeanNameFQ()
	{
		return createFQName(getBeanName(), beanPkg);
	}
	
	public Collection<Field> getFields()
	{
		List<Field> uF = new ArrayList<Field>();
		for(Query q : this.getQueries())
		{
			uF = new Field().merge(q.getFields(), uF);
		}
		
		return uF;
	}

	public String getBeanPkg()
	{
		return beanPkg;
	}

	public void setBeanPkg(String beanPkg)
	{
		this.beanPkg = beanPkg;
	}

	public String getDaoPkg()
	{
		return daoPkg;
	}

	public void setDaoPkg(String daoPkg)
	{
		this.daoPkg = daoPkg;
	}

	public String getMapPkg()
	{
		return mapPkg;
	}

	public void setMapPkg(String mapPkg)
	{
		this.mapPkg = mapPkg;
	}

	public String getJdbcPkg()
	{
		return jdbcPkg;
	}

	public void setJdbcPkg(String jdbcPkg)
	{
		this.jdbcPkg = jdbcPkg;
	}
	
	
	public String getMapInterfaceName() {
		return this.getMapName("");
	}
	
	public String getMapSuperClassName() {
		return this.getMapName("").concat("Abs");
	}
	
	public TypeName getMapInterface(Class<?> retType) {
		Class<?> clazz = getMapClass(getMapInterfaceName());
		return ParameterizedTypeName.get(clazz, retType);
	}
	
	public TypeName getMapSuperClass(Class<?> retType) {
		Class<?> clazz = getMapClass(getMapSuperClassName());
		return ParameterizedTypeName.get(clazz, retType);
	}

	public String getMapFactoryName()
	{
		return "Map" + factorySuffix ;
	}
	
	public String getDaoFactoryName()
	{
		return "Dao" + factorySuffix ;
	}
	
	public String getDataSourceFactoryName()
	{
		return "DataSource" + factorySuffix ;
	}
	
	public String getMapFactoryNameFQ()
	{
		return packageString(getPackage(factoryPkg ), getMapFactoryName());
	}

	public Class<?> getRootDao(Class<?> m)
	{
		String simpleName = m.getSimpleName();
		if(simpleName.contains("JDBC")){
			return getDataAccessClass(simpleName.replace("JDBC", ""));
		}
		return null;
	}
	
	public Class<?> getDataSource(String name) {
		return getClass("com.dataAccess.dataSource.JdbcSuperClass", null);
	}
}


















