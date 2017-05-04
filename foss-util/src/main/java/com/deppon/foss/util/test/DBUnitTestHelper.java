package com.deppon.foss.util.test;

import java.io.File;
import java.io.FileOutputStream;
import java.sql.Connection;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.commons.lang.StringUtils;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.database.IMetadataHandler;
import org.dbunit.database.QueryDataSet;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.csv.CsvDataSet;
import org.dbunit.dataset.datatype.IDataTypeFactory;
import org.dbunit.dataset.excel.XlsDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.springframework.core.io.Resource;
import org.springframework.jdbc.datasource.DataSourceUtils;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

public class DBUnitTestHelper {
	public static final String EXPORT_SUBFOLDER_NAME = "dataset";
	
	private Resource[] datasetFiles;
	
	private Resource[] csvDirectories;
	
	private DataSource dataSource;
	
	private Map replacements;
	
	private boolean commit;
	
	private String schema;
	
	private Properties properties;
	
	public List exportTables;
	
	public Resource exportDirectory;
	
	/**
	 * Close the specified connection. Override this method of you want to keep your connection alive between tests.
	 */
	protected void closeConnection(IDatabaseConnection connection) throws Exception {
		connection.close();
	}
	
	private void executeOperation(DatabaseOperation operation) throws Exception {
		if (operation != DatabaseOperation.NONE) {
			IDatabaseConnection connection = getConnection();
			
			DatabaseConfig config = connection.getConfig();
			
			if ( properties != null ) {
				String dataTypeFactory = properties.getProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY);
				if ( StringUtils.isNotBlank(dataTypeFactory) ) {
					IDataTypeFactory factory = (IDataTypeFactory) Class.forName(dataTypeFactory).newInstance();
					if ( factory != null ) {
						config.setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, factory);
					}
					
				}
				
				String metadataHandler = properties.getProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER);
				if ( StringUtils.isNotBlank(metadataHandler)) {
					IMetadataHandler handler = (IMetadataHandler)Class.forName(metadataHandler).newInstance();
					
					if ( handler != null ) {
						config.setProperty(DatabaseConfig.PROPERTY_METADATA_HANDLER, handler);
					}
				}
			}
			
			operation.execute(connection, getDataSet());
		}
	}

	/**
	 * Returns the test database connection.
	 */
	public IDatabaseConnection getConnection() throws Exception {
		return new DatabaseConnection(this.getJdbcConnection(), schema);
	}

	/**
	 * Returns the test dataset.
	 */
	protected IDataSet getDataSet() throws Exception {
		CompositeDataSet result = null;
		
		List datasets = new ArrayList();
		
		if ( csvDirectories != null && csvDirectories.length > 0 ) {
			for ( int i = 0; i<csvDirectories.length; i++ ) {
				Resource directory = csvDirectories[i];
				
				ReplacementDataSet dataset = new ReplacementDataSet(new CsvDataSet(directory.getFile()));
				
				if ( replacements != null ) {
					Iterator iterator = replacements.entrySet().iterator();
					while ( iterator.hasNext() ) {
						Map.Entry entry = (Map.Entry)iterator.next();
						
						dataset.addReplacementObject(entry.getKey(), entry.getValue());
					}
				}
				
				datasets.add(dataset);
				
				result = new CompositeDataSet((IDataSet[])datasets.toArray(new IDataSet[0]));
			}
			
		} else if ( datasetFiles != null && datasetFiles.length > 0 ) {
			
			for(int i=0; i<datasetFiles.length; i++) {
				Resource file = datasetFiles[i];
				
				ReplacementDataSet dataset = null;
				
				String filename = file.getFilename();
				if ( filename.endsWith("xml")) {
					FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
					builder.setColumnSensing(true);
					
					dataset = new ReplacementDataSet(builder.build(file.getInputStream()));
				} else if ( filename.endsWith("xls") ) {
					dataset = new ReplacementDataSet(new XlsDataSet(file.getInputStream()));
				} else {
					//Assert.fail("Unsupported file type for DBUnit test! " + filename);
				}
				
				if ( replacements != null ) {
					Iterator iterator = replacements.entrySet().iterator();
					while ( iterator.hasNext() ) {
						Map.Entry entry = (Map.Entry)iterator.next();
						
						dataset.addReplacementObject(entry.getKey(), entry.getValue());
					}
				}
				
				datasets.add(dataset);
			}
			
			result = new CompositeDataSet((IDataSet[])datasets.toArray(new IDataSet[0]));
		}
		
		return result;
	}

	public Connection getJdbcConnection() throws Exception {
		return DataSourceUtils.getConnection(dataSource);
	}

	/**
	 * Returns the database operation executed in test setup.
	 */
	protected DatabaseOperation getSetUpOperation() throws Exception {
		return DatabaseOperation.CLEAN_INSERT;
	}

	/**
	 * Returns the database operation executed in test cleanup.
	 */
	protected DatabaseOperation getTearDownOperation() throws Exception {
		return DatabaseOperation.NONE;
	}

	public void setUp() {
		try {
			executeOperation(getSetUpOperation());
			
			//this.alreadySetUp = true;
		} catch (Exception e) {
			throw new RuntimeException("error when doing dbunit setUp", e);
		}
	}

	public void tearDown() {
		try {
			executeOperation(getTearDownOperation());
		} catch (Exception e) {
			throw new RuntimeException("error when doing dbunit tearDown", e);
		}
	}

	public DataSource getDataSource() {
		return dataSource;
	}

	/**
	 * ����ݿ⵼��dataset�ļ���
	 * @throws Exception
	 */
	public void export() throws Exception {
        // partial database export
        Assert.notNull(exportDirectory);
        
		if ( !CollectionUtils.isEmpty(exportTables)) {
        	if ( !exportDirectory.getFile().exists() ) {
        		exportDirectory.getFile().mkdir();
	        }
        	
        	String subfolder = exportDirectory.getFile().getAbsolutePath() + EXPORT_SUBFOLDER_NAME;
	        File folder = new File(subfolder);
	        
	        if ( !folder.exists() ) {
	        	folder.mkdir();
	        }

	        for(int i=0; i<exportTables.size(); i++) {
				String exportTable = (String)exportTables.get(i);
				QueryDataSet partialDataSet = new QueryDataSet(this.getConnection());
				
				//partialDataSet.addTable("FOO", "SELECT * FROM TABLE WHERE COL='VALUE'");
		        partialDataSet.addTable(exportTable);
		        
		        File dataset = new File(subfolder + "/" + exportTable + ".xml");
		        if ( dataset.exists() ) {
		        	dataset.delete();
		        }
		        
		        dataset.createNewFile();
		        
		        FlatXmlDataSet.write(partialDataSet, new FileOutputStream(dataset));
			}
		}
	}
	
	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	public Resource[] getDatasetFiles() {
		return datasetFiles;
	}

	public void setDatasetFiles(Resource[] datasetFiles) {
		this.datasetFiles = datasetFiles;
	}

	public Map getReplacements() {
		return replacements;
	}

	public void setReplacements(Map replacements) {
		this.replacements = replacements;
	}

	public boolean isCommit() {
		return commit;
	}

	public void setCommit(boolean commit) {
		this.commit = commit;
	}

	public String getSchema() {
		return schema;
	}

	public void setSchema(String schema) {
		this.schema = schema;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public List getExportTables() {
		return exportTables;
	}

	public void setExportTables(List exportTables) {
		this.exportTables = exportTables;
	}

	public Resource getExportDirectory() {
		return exportDirectory;
	}

	public void setExportDirectory(Resource exportDirectory) {
		this.exportDirectory = exportDirectory;
	}

	public Resource[] getCsvDirectories() {
		return csvDirectories;
	}

	public void setCsvDirectories(Resource[] csvDirectories) {
		this.csvDirectories = csvDirectories;
	}

}
