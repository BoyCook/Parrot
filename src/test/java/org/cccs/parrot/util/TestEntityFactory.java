package org.cccs.parrot.util;

import org.cccs.parrot.domain.Person;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * User: boycook
 * Date: 27/07/2012
 * Time: 13:01
 */
@ContextConfiguration(locations = "classpath:context/parrotContext.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class TestEntityFactory {

    @Test
    public void getEntityShouldWork() {
        Person person = EntityFactory.get("Person", 1);
        assertThat(person.getId(), is(equalTo(1l)));
    }
}
