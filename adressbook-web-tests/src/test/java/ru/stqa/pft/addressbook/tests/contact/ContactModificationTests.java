package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactModificationTests extends TestBase {

    @Test
    public void testModifySelectedContact() {
        app.getNavigationHelper().gotoPage("home");
        app.getContactHelper().selectContact();
        app.getContactHelper().editSelectedContact();
        app.getContactHelper().fillContactForm(new ContactData("VeronikaEdit", "Igorevna", "Golikova", "Shoco.Arts", "someemail@mail.ru"));
        app.getContactHelper().submitContactModification();
        app.getNavigationHelper().gotoPage("home");

    }
}
