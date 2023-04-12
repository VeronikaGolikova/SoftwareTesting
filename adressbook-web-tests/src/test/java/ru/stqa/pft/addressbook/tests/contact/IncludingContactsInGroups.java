package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Optional;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IncludingContactsInGroups  extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        if (app.db().contacts().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData()
                    .withFirstname("VeronikaWithData").withMiddlename("IgorevnaWithData").withLastname("GolikovaWithData").withNick("nickPH").withEmail("somePH@mail.ru")
                    .withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withAddress("какой-то адрес 23").withEmail3("emtyemail@ya.ru"));
            app.goTo().homePage();
        }
        if (app.db().groups().size() == 0) {
            app.goTo().groupPage();
            app.group().create( new GroupData().withName("groupForIncluding").withHeader("groupForIncluding").withFooter("groupForIncluding"));
            app.goTo().returnToGroupPage();
            app.goTo().homePage();
        }
    }

    @Test
    public void testIncludeContactInGroup() throws Exception {
        ContactData contactForIncluding = app.db().contacts().stream().findAny().get();
        GroupData groupForIncluding = app.db().groups().stream().findAny().get();
        ContactData contactAfterIncluding;
        if(contactForIncluding.getGroups()
                .stream().anyMatch(group -> group.getId()==groupForIncluding.getId())) {
            app.goTo().newContactPage();
            ContactData newContactForIncluding = app.contact().create(new ContactData()
                    .withFirstname("ContactWithoutGroup").withMiddlename("ContactWithoutGroup").withLastname("ContactWithoutGroup")
                    .withNick("nickPH").withEmail("somePH@mail.ru").withHomePhone("111").withMobilePhone("222"));
            app.goTo().homePage();
            int maxId = app.db().contacts().stream()
                    .mapToInt((value) -> {
                        return value.getId();
                    })
                    .summaryStatistics()
                    .getMax();
            newContactForIncluding.withId(maxId);
            contactForIncluding = newContactForIncluding;
        }
        app.contact().addContactInGroup(contactForIncluding, groupForIncluding);
        ContactData finalContactForIncluding = contactForIncluding;
        contactAfterIncluding = app.db().contacts().stream().filter(c -> c.getId()== finalContactForIncluding.getId()).findAny().get();
        assertTrue(contactAfterIncluding.getGroups()
                .stream().anyMatch(group -> group.getId()==groupForIncluding.getId()));
    }

    @Test
    public void testDeleteContactFromGroup() throws Exception {
        GroupData groupForExcluding = app.db().groups().stream().findFirst().get();
        Optional<ContactData>  contact = groupForExcluding.getContacts().stream().findAny();
        ContactData contactForExcluding;
        if(contact.isPresent()){
            contactForExcluding = contact.get();
        } else {
            app.goTo().newContactPage();
            contactForExcluding = app.contact().create(new ContactData()
                    .withFirstname("ContactWithoutGroup").withMiddlename("ContactWithoutGroup").withLastname("ContactWithoutGroup")
                    .withNick("nickPH").withEmail("somePH@mail.ru").withHomePhone("111").withMobilePhone("222"));
            app.goTo().homePage();
            int maxId = app.db().contacts().stream()
                    .mapToInt((value) -> {
                        return value.getId();
                    })
                    .summaryStatistics()
                    .getMax();
            contactForExcluding.withId(maxId);
            groupForExcluding = app.db().groups().stream().findAny().get();
            app.contact().addContactInGroup(contactForExcluding, groupForExcluding);
        }
        app.goTo().homePage();
        app.contact().deleteContactFromGroup(contactForExcluding,groupForExcluding);
        GroupData groupAfterExcluding = app.db().groups().stream().findFirst().get();
        assertFalse(groupAfterExcluding.getContacts().stream().anyMatch(group -> group.getId() == contactForExcluding.getId()));
    }
}
