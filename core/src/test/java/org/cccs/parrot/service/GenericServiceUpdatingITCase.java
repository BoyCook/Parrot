package org.cccs.parrot.service;

import org.cccs.parrot.DataDrivenTestEnvironment;
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
 * Time: 15:13
 */
@ContextConfiguration(locations = "classpath:parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class GenericServiceUpdatingITCase extends DataDrivenTestEnvironment {

    @Rule
    public ExpectedException thrown = ExpectedException.none();
    private GenericService service;
    private GenericFinder finder;

    @Before
    public void beforeEach() throws Exception {
        service = new GenericService(entityManagerFactory);
        finder = new GenericFinder(entityManagerFactory);
        setDataFileNames(DEFAULT_TABLES);
        setTearDown(true);
        super.beforeEach();
    }

    @Test
    public void updateShouldWork() {
        Person original = finder.find(Person.class, "name", "Craig Cook");
        assertCraigWithRelations(original);
        original.setEmail("updated@craigcook.co.uk");
        original.setPhone("999");

        service.update(original);

        Person updated = finder.find(Person.class, "name", "Craig Cook");
        assertThat(updated.getId(), is(greaterThanOrEqualTo(1l)));
        assertThat(updated.getName(), is(equalTo("Craig Cook")));
        assertThat(updated.getEmail(), is(equalTo("updated@craigcook.co.uk")));
        assertThat(updated.getPhone(), is(equalTo("999")));
        assertThat(updated.getCats().size(), is(equalTo(2)));
        assertThat(updated.getDogs().size(), is(equalTo(2)));
    }
}
