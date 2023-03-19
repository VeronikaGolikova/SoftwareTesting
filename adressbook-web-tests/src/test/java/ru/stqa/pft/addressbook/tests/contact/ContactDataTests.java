package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactDataTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().newContactPage();
      app.contact().create(new ContactData()
              .withFirstname("VeronikaWithData").withMiddlename("IgorevnaWithData").withLastname("GolikovaWithData").withNick("nickPH").withEmail("somePH@mail.ru")
              .withHomePhone("111").withMobilePhone("222").withWorkPhone("333").withAddress("какой-то адрес 23").withEmail3("emtyemail@ya.ru"));
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactWithData() throws Exception {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactDataFromEditForm)));
    assertThat(contact.getAddress(), equalTo(contactDataFromEditForm.getAddress()));
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactDataFromEditForm)));
  }

  @Test (enabled = false)
  public void testContactPhones() throws Exception {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllPhones(), equalTo(mergePhones(contactDataFromEditForm)));
  }

  @Test (enabled = false)
  public void testContactAddress() throws Exception {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAddress(), equalTo(contactDataFromEditForm.getAddress()));
  }

  @Test (enabled = false)
  public void testContactEmails() throws Exception {
    ContactData contact = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contact);
    assertThat(contact.getAllEmails(), equalTo(mergeEmails(contactDataFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s)->!s.equals(""))
            .map(ContactDataTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  private String mergeEmails(ContactData contact) {
    return Arrays.asList(contact.getEmail(), contact.getEmail2(), contact.getEmail3())
            .stream().filter((s)->!s.equals(""))
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]","");
  }
}
