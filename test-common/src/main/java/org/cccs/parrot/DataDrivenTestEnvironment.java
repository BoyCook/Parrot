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
    private String[] dataFileNames = new String[]{};
    //TODO: move defaults out of here
    protected static final String[] DEFAULT_TABLES = new String[] {
            "/db/countries.xml",
            "/db/people.xml",
            "/db/cats.xml",
            "/db/dogs.xml"

    };
    private static final String[] DELETE_TABLES = new String[] {
            "country",
            "cat",
            "dog",
            "person"
    };
    private boolean tearDown = false;
    private static boolean dataInstalled = false;
    protected DBUnitDataInstaller installer;

    @Before
    public void beforeEach() throws Exception {
        //The data is installed either for each test or for the entire test run
        if (isInstall()) {
            installer = new DBUnitDataInstaller(dataSource, getDataFileNames(), DELETE_TABLES);
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

    public boolean isInstall() {
        return (isTearDown() || !dataInstalled) && getDataFileNames().length > 0;
    }

    public boolean isTearDown() {
        return tearDown;
    }

    public void setTearDown(boolean tearDown) {
        this.tearDown = tearDown;
    }
}
