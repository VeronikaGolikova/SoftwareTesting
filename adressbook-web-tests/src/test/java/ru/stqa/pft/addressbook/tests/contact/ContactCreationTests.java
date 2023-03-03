package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class ContactCreationTests extends TestBase {

  @Test
  public void testCreateNewContact() throws Exception {
    app.getNavigationHelper().gotoAddNewContactPage();
    app.getContactHelper().createNewGroup(new ContactData("Veronika", "Igorevna", "Golikova", "nick", "some@mail.ru"));
    app.getNavigationHelper().gotoHomePage();

  }

}
