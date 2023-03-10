package ru.stqa.pft.addressbook.tests.contact;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class ContactDeletionTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData()
                    .withFirstname("VeronikaDel").withMiddlename("IgorevnaDel").withLastname("GolikovaDel").withNick("nickDel").withEmail("someDel@mail.ru"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testDeleteSelectedContact() throws Exception {
        List<ContactData> before = app.contact().list();
        app.contact().selectContact(before.size() - 1);
        app.contact().deleteSelectedContact();
        app.contact().submitContactDeletion();
        app.goTo().homePage();
        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size()-1);
        before.remove(before.size()-1);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
