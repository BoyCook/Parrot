package org.cccs.parrot;

import org.junit.AfterClass;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

/**
 * User: boycook
 * Date: 24/06/2012
 * Time: 13:47
 */
public class DataDrivenTestEnvironment  {

    @Autowired
    protected DataSource dataSource;
    @Autowired
    protected EntityManagerFactory entityManagerFactory;
    private DBUnitDataInstaller installer;
    private String[] dataFileNames = new String[]{};
    private String[] deleteTables = new String[]{};
    //TODO: move defaults out of here
    private boolean tearDown = false;
    private static boolean dataInstalled = false;

    @Before
    public void beforeEach() throws Exception {
        //The data is installed either for each test or for the entire test run
        if (isInstall()) {
            installer = new DBUnitDataInstaller(dataSource, getDataFileNames(), getDeleteTables());
            installer.installClean();
            dataInstalled = true;
        }
    }

    @AfterClass
    public static void afterAll() {
        dataInstalled = false;
    }

    public String[] getDataFileNames() {
        return dataFileNames;
    }

    public void setDataFileName(String dataFileName) {
        setDataFileNames(new String[]{dataFileName});
    }

    public void setDataFileNames(String[] dataFileNames) {
        this.dataFileNames = dataFileNames;
    }

    public String[] getDeleteTables() {
        return deleteTables;
    }

    public void setDeleteTables(String[] deleteTables) {
        this.deleteTables = deleteTables;
    }

    public boolean isInstall() {
        return isTearDown() || !dataInstalled;
    }

    public boolean isTearDown() {
        return tearDown;
    }

    public void setTearDown(boolean tearDown) {
        this.tearDown = tearDown;
    }

    public DBUnitDataInstaller getInstaller() {
        return installer;
    }
}
