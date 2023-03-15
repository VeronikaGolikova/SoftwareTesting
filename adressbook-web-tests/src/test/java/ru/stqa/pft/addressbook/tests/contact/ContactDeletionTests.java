package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().all().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData()
                    .withFirstname("VeronikaDel").withMiddlename("IgorevnaDel").withLastname("GolikovaDel").withNick("nickDel").withEmail("someDel@mail.ru"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testDeleteSelectedContact() throws Exception {
        Contacts before = app.contact().all();
        ContactData contactForDeletion = before.iterator().next();
        app.contact().delete(contactForDeletion);
        app.goTo().homePage();
        Contacts after = app.contact().all();
        assertThat(after.size(), equalTo(before.size()-1));
        assertThat(after, equalTo(before.without(contactForDeletion)));
    }
}
