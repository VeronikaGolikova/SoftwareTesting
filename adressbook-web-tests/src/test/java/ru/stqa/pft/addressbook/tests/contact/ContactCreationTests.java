package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.*;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.model.Contacts;
import ru.stqa.pft.addressbook.tests.TestBase;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactCreationTests extends TestBase {

  @Test
  public void testCreateNewContact() throws Exception {
    app.goTo().homePage();
    Contacts before = app.contact().all();
    app.goTo().newContactPage();
    ContactData contact = new ContactData()
            .withFirstname("Veronika").withLastname("Golikova").withMiddlename("Igorevna").withNick("nick").withEmail("some@mail.ru");
    app.contact().create(contact);
    app.goTo().homePage();
    assertThat(app.contact().count(), equalTo(before.size() +1));
    Contacts after = app.contact().all();
    assertThat(after, equalTo(
            before.withAdded(contact.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt()))));
  }
}
