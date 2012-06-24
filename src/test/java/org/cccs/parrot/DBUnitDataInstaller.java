package org.cccs.parrot;

import org.apache.commons.io.IOUtils;
import org.dbunit.DataSourceDatabaseTester;
import org.dbunit.DatabaseUnitException;
import org.dbunit.IDatabaseTester;
import org.dbunit.database.DatabaseConfig;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.CompositeDataSet;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatDtdDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.dataset.xml.XmlDataSet;
import org.dbunit.ext.hsqldb.HsqldbConnection;
import org.dbunit.operation.DatabaseOperation;

import javax.sql.DataSource;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * User: boycook
 * Date: 11/05/2012
 * Time: 08:59
 */
public class DBUnitDataInstaller {

    protected DataSource dataSource;
    private String[] dataFileNames = new String[]{};
    private String[] deleteTables;
    protected IDatabaseTester tester;

    public DBUnitDataInstaller(final DataSource dataSource, final String[] dataFileNames) {
        this(dataSource, dataFileNames, new String[]{});
    }

    public DBUnitDataInstaller(final DataSource dataSource, final String[] dataFileNames, final String[] deleteTables) {
        this.dataSource = dataSource;
        this.tester = new DataSourceDatabaseTester(getDataSource());
        this.dataFileNames = dataFileNames;
        this.deleteTables = deleteTables;
    }

    public void install() throws Exception {
        IDatabaseConnection conn = tester.getConnection();
        conn.getConfig().setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        conn.getConfig().setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, 1000);
//        tester.getConnection().getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
//        tester.getConnection().getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);
//        tester.getConnection().getConfig().setProperty(DatabaseConfig.PROPERTY_DATATYPE_FACTORY, new HsqldbDataTypeFactory());
        tester.setDataSet(getDataSet(getDataFileNames()));
        tester.onSetup();
        conn.close();
    }

    public void tearDown() throws Exception {
        tester.onTearDown();
    }

    /**
     * Database is wiped before data is installed
     * @throws java.sql.SQLException
     * @throws DatabaseUnitException
     * @throws java.io.IOException
     */
    public void installClean() throws SQLException, DatabaseUnitException, IOException {
        IDatabaseConnection conn = new HsqldbConnection(getDataSource().getConnection(), "PUBLIC");
        conn.getConfig().setProperty(DatabaseConfig.FEATURE_BATCHED_STATEMENTS, true);
        conn.getConfig().setProperty(DatabaseConfig.PROPERTY_BATCH_SIZE, 100);
//        conn.getConfig().setProperty(DatabaseConfig.FEATURE_QUALIFIED_TABLE_NAMES, false);
//        conn.getConfig().setProperty(DatabaseConfig.FEATURE_CASE_SENSITIVE_TABLE_NAMES, false);
//        DatabaseOperation.DELETE_ALL.execute(conn, getDataSet(new String[]{
//                "/db/ticket_types.xml",
//                "/db/tickets.xml",
//                "/db/product_instances.xml"
//        }));
//        DatabaseOperation.CLEAN_INSERT.execute(conn, getDataSet(getDataFileNames()));
        deleteAll();
        DatabaseOperation.CLEAN_INSERT.execute(conn, getDataSet(getDataFileNames()));
        conn.close();
    }

    public void deleteAll() throws SQLException {
        Connection conn = dataSource.getConnection();
        for (String table : getDeleteTables()) {
            delete(table, conn);
        }
        conn.close();
    }

    private void delete(final String name, final Connection conn) throws SQLException {
        PreparedStatement pst = conn.prepareStatement("DELETE FROM " + name);
        pst.execute();
    }

    public void executeSQLFile(final String fileName) throws IOException, SQLException {
        Connection conn = getDataSource().getConnection();
        System.out.println("Installing data file: " + fileName);
        String sql = resourceAsString(fileName);
        try {
            PreparedStatement statement = conn.prepareStatement(sql);
            statement.execute();
            conn.commit();
        } catch (SQLException e) {
            conn.rollback();
            e.printStackTrace();
        } finally {
            conn.close();
        }
    }

    public void generateDTD() throws SQLException, DatabaseUnitException, IOException {
        IDatabaseConnection connection = new DatabaseConnection(getDataSource().getConnection());
        FlatDtdDataSet.write(connection.createDataSet(), new FileOutputStream("src/test/sql/inventory.dtd"));
    }

    private FlatXmlDataSetBuilder getBuilder(final String fileName) throws IOException, DataSetException {
        System.out.println("Getting FlatXmlDataSetBuilder: " + fileName);
        FlatXmlDataSetBuilder builder = new FlatXmlDataSetBuilder();
        builder.setCaseSensitiveTableNames(false);
        builder.setColumnSensing(true);
        return builder;
    }

    private IDataSet getDataSet(final String[] fileNames) throws IOException, DataSetException {
        IDataSet[] dataSets = new IDataSet[fileNames.length];
        System.out.println("Data file count: " + fileNames.length);
        for (int i=0; i<fileNames.length; i++) {
            dataSets[i] = getDataSet(fileNames[i]);
        }
        return new CompositeDataSet(dataSets);
    }

    private IDataSet getDataSet(final String fileName) throws IOException, DataSetException {
        System.out.println("Getting DataSet: " + fileName);
        return new XmlDataSet(DBUnitDataInstaller.class.getResourceAsStream(fileName));
    }

    public String[] getDataFileNames() {
        return dataFileNames;
    }

    public String[] getDeleteTables() {
        return deleteTables;
    }

    private DataSource getDataSource() {
        return dataSource;
    }

    private String resourceAsString(final String fileName) throws IOException {
        final InputStream stream = DBUnitDataInstaller.class.getResourceAsStream(fileName);
        return IOUtils.toString(stream);
    }
}
