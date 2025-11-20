package config;

import java.net.URL;
import java.util.List;
import java.util.Properties;

import javax.sql.DataSource;

import com.zaxxer.hikari.HikariDataSource;

import jakarta.persistence.SharedCacheMode;
import jakarta.persistence.ValidationMode;
import jakarta.persistence.spi.ClassTransformer;
import jakarta.persistence.spi.PersistenceUnitInfo;
import jakarta.persistence.spi.PersistenceUnitTransactionType;

public class MyPersistenceUnitInfo implements PersistenceUnitInfo {

	@Override
	public String getPersistenceUnitName() {
		return "my-pu";
	}

	@Override
	public String getPersistenceProviderClassName() {
		return "org.hibernate.jpa.HibernatePersistenceProvider";
	}

	@Override
	public PersistenceUnitTransactionType getTransactionType() {
		return PersistenceUnitTransactionType.RESOURCE_LOCAL;
	}

	@Override // datasource, 연결정보(Connection pool, xml에서 하던거 여기로 옮김)
	public DataSource getJtaDataSource() {
		
		HikariDataSource dataSource = new HikariDataSource();
//		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/jpa_basic_12");
		dataSource.setJdbcUrl("jdbc:mysql://localhost:3306/jpa_basic_12");
		dataSource.setUsername("root");
		dataSource.setPassword("0000");
		return dataSource;
	}

	@Override
	public DataSource getNonJtaDataSource() {
		return null;
	}

	@Override
	public List<String> getMappingFileNames() {
		return null;
	}

	@Override
	public List<URL> getJarFileUrls() {
		return null;
	}

	@Override
	public URL getPersistenceUnitRootUrl() {
		return null;
	}

	@Override // JPA를 통해 다루려는 클래스 정의
	public List<String> getManagedClassNames() {
//		return List.of("entity.Product"); // 1게시물 M댓글
		return List.of("entity.Customer");
	}

	@Override
	public boolean excludeUnlistedClasses() {
		return false;
	}

	@Override
	public SharedCacheMode getSharedCacheMode() {
		return null;
	}

	@Override
	public ValidationMode getValidationMode() {
		return null;
	}

	@Override
	public Properties getProperties() {
		return null;
	}

	@Override
	public String getPersistenceXMLSchemaVersion() {
		return null;
	}

	@Override
	public ClassLoader getClassLoader() {
		return null;
	}

	@Override
	public void addTransformer(ClassTransformer transformer) {
		
	}

	@Override
	public ClassLoader getNewTempClassLoader() {
		return null;
	}

}
