package ru.stqa.pft.addressbook.tests.contact;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class ContactModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().homePage();
        if (app.contact().list().size() == 0) {
            app.goTo().newContactPage();
            app.contact().create(new ContactData("VeronikaDel", "IgorevnaDel", "GolikovaDel", "nickDel", "someDel@mail.ru"));
            app.goTo().homePage();
        }
    }

    @Test
    public void testModifySelectedContact() {
        List<ContactData> before = app.contact().list();
        app.contact().selectContact(before.size() - 1);
        app.contact().editSelectedContact(before.size() - 1);
        ContactData contact = new ContactData(before.get(before.size() - 1).getId(),"VeronikaEdit", "Igorevna", "Golikova", "Shoco.Arts", "someemail@mail.ru");
        app.contact().fillContactForm(contact);
        app.contact().submitContactModification();
        app.goTo().homePage();

        List<ContactData> after = app.contact().list();
        Assert.assertEquals(after.size(), before.size());

        before.remove(before.size()-1);
        before.add(contact);
        Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }
}
