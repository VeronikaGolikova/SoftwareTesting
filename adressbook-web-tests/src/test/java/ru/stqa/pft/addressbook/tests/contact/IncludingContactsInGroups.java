package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
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
        Contacts contactsFromDb = app.db().contacts();
        Groups groupsFromDb = app.db().groups();
        ContactData contactForIncluding = contactsFromDb.stream().findAny().get();
        GroupData groupForIncluding = groupsFromDb.stream().findAny().get();
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
            app.contact().addContactInGroup(newContactForIncluding, groupForIncluding);
            ContactData contactAfterIncluding = app.db().contacts().stream().filter(c -> c.getId()==maxId).findAny().get();
            assertTrue(contactAfterIncluding.getGroups()
                    .stream().anyMatch(group -> group.getId()==groupForIncluding.getId()));
        } else {
            app.contact().addContactInGroup(contactForIncluding, groupForIncluding);
            ContactData contactAfterIncluding = app.db().contacts().stream().filter(c -> c.getId()==contactForIncluding.getId()).findAny().get();
            assertTrue(contactAfterIncluding.getGroups()
                    .stream().anyMatch(group -> group.getId()==groupForIncluding.getId()));
        }
    }

    @Test
    public void testDeleteContactFromGroup() throws Exception {
        Contacts contactsFromDb = app.db().contacts();
        Groups groupsFromDb = app.db().groups();
        GroupData groupForExcluding = groupsFromDb.stream().findFirst().get();
        Optional<ContactData>  contact = groupForExcluding.getContacts().stream().findAny();

        if(contact.isPresent()){
            ContactData contactForExcluding = contact.get();
            app.goTo().homePage();
            app.contact().deleteContactFromGroup(contactForExcluding,groupForExcluding);
            GroupData groupAfterExcluding = app.db().groups().stream().findFirst().get();
            assertFalse(groupAfterExcluding.getContacts().stream().anyMatch(group -> group.getId() == contactForExcluding.getId()));
        } else {
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
            GroupData groupForIncluding = groupsFromDb.stream().findAny().get();
            app.contact().addContactInGroup(newContactForIncluding, groupForIncluding);
            app.goTo().homePage();
            app.contact().deleteContactFromGroup(newContactForIncluding,groupForIncluding);
            GroupData groupAfterExcluding = groupsFromDb.stream().findFirst().get();
            assertFalse(groupAfterExcluding.getContacts().stream().anyMatch(group -> group.getId() == newContactForIncluding.getId()));
        }
    }
}
