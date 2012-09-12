package org.cccs.parrot.web;

import org.cccs.parrot.domain.Person;
import org.cccs.parrot.finder.GenericFinder;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.cccs.parrot.Assert.assertCraigWithRelations;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.greaterThanOrEqualTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 31/07/2012
 * Time: 15:19
 */
@ContextConfiguration(locations = "classpath:context/testApplicationContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class ParrotControllerUpdatingITCase extends JettyIntegrationTestEnvironment {

    private GenericFinder finder;
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void beforeEach() throws Exception {
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(DEFAULT_TABLES);
        setTearDown(true);
        super.beforeEach();
    }

    @Test
    public void updatePersonShouldWork() {
        Person original = finder.find(Person.class, "name", "Craig Cook");
        assertCraigWithRelations(original);
        original.setEmail("updated@craigcook.co.uk");
        original.setPhone("999");

        client.postForLocation(serviceBaseURL + "person/" + original.getId(), original);

        Person updated = finder.find(Person.class, "name", "Craig Cook");
        assertThat(updated.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(updated.getName(), is(equalTo("Craig Cook")));
        assertThat(updated.getEmail(), is(equalTo("updated@craigcook.co.uk")));
        assertThat(updated.getPhone(), is(equalTo("999")));
        assertThat(updated.getCats().size(), is(equalTo(2)));
        assertThat(updated.getDogs().size(), is(equalTo(2)));
    }
}
