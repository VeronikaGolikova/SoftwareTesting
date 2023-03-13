package ru.stqa.pft.addressbook.tests.contact;

import org.testng.Assert;
import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class ContactCreationTests extends TestBase {

  @Test
  public void testCreateNewContact() throws Exception {
    app.goTo().homePage();
    List<ContactData> before = app.contact().list();
    app.goTo().newContactPage();
    ContactData contact = new ContactData("Veronika", "Igorevna", "Golikova", "nick", "some@mail.ru");
    app.contact().create(contact);
    app.goTo().homePage();

    List<ContactData> after = app.contact().list();
    Assert.assertEquals(after.size(), before.size() + 1);

    before.add(contact);
    Comparator<? super ContactData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
    before.sort(byId);
    after.sort(byId);
    Assert.assertEquals(before, after);
  }
}
