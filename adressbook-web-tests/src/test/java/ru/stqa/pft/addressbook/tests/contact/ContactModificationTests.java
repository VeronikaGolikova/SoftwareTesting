package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.db().contacts().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData()
                    .withFirstname("VeronikaDel").withLastname("GolikovaDel").withMiddlename("IgorevnaDel").withNick("nickDel").withEmail("someDel@mail.ru"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testModifySelectedContact() {
        Contacts before = app.db().contacts();
        ContactData contactForModification = before.iterator().next();
        ContactData contact = new ContactData()
                .withId(contactForModification.getId()).withFirstname("VeronikaEdit").withMiddlename("Igorevna").withLastname("Golikova").withNick("Shoco.Arts").withEmail("someemail@mail.ru");
        app.contact().edit(contactForModification);
        app.contact().fillContactForm(contact);
        app.contact().submitContactModification();
        app.goTo().homePage();

        assertThat(app.contact().count(), equalTo(before.size()));
        Contacts after = app.db().contacts();
        assertThat(after, equalTo(before.without(contactForModification).withAdded(contact)));
        verifyContactListInUi();
    }
}
