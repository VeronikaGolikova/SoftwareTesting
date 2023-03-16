package ru.stqa.pft.addressbook.tests.contact;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.ContactData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Arrays;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class ContactPhoneTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().homePage();
    if (app.contact().all().size() == 0) {
      app.goTo().newContactPage();
      app.contact().create(new ContactData()
              .withFirstname("VeronikaPhone").withMiddlename("IgorevnaPhone").withLastname("GolikovaPH").withNick("nickPH").withEmail("somePH@mail.ru")
              .withHomePhone("111").withMobilePhone("222").withWorkPhone("333"));
      app.goTo().homePage();
    }
  }

  @Test
  public void testContactPhones() throws Exception {
    ContactData contactWithPhone = app.contact().all().iterator().next();
    ContactData contactDataFromEditForm = app.contact().infoFromEditForm(contactWithPhone);
    assertThat(contactWithPhone.getAllPhones(), equalTo(mergePhones(contactDataFromEditForm)));
  }

  private String mergePhones(ContactData contact) {
    return Arrays.asList(contact.getHomePhone(), contact.getMobilePhone(), contact.getWorkPhone())
            .stream().filter((s)->!s.equals(""))
            .map(ContactPhoneTests::cleaned)
            .collect(Collectors.joining("\n"));
  }

  public static String cleaned(String phone) {
    return phone.replaceAll("\\s", "").replaceAll("[-()]","");
  }
}
