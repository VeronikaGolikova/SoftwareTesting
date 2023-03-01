package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactCreationTests extends TestBase {

  @Test
  public void testCreateNewContact() throws Exception {
    app.getNavigationHelper().gotoAddNewContactPage();
    app.getContactHelper().fillContactForm(new ContactData("Veronika", "Igorevna", "Golikova", "Shoco.Arts", "someemail@mail.ru"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().gotoHomePage();

  }

}
