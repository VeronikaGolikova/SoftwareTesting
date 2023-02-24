package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;

public class ContactCreationTests extends TestBase {



  @Test
  public void testCreateNewContact() throws Exception {
//    app.getContactHelper().clickCreateNewContact("add new");
    app.getNavigationHelper().gotoPage("add new");
    app.getContactHelper().fillContactForm(new ContactData("Veronika", "Igorevna", "Golikova", "Shoco.Arts", "someemail@mail.ru"));
    app.getContactHelper().submitContactCreation();
    app.getNavigationHelper().gotoPage("home page");

  }

}
