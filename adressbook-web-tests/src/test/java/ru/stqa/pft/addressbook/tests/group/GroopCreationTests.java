package ru.stqa.pft.addressbook.tests.group;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

public class GroopCreationTests extends TestBase {

  @Test
  public void testGroopCreation() throws Exception {
    app.getNavigationHelper().gotoGroupPage();
    app.getGroupHelper().initGroupCreation();
    app.getGroupHelper().fillGroupForm(new GroupData("testNull", null, null));
    app.getGroupHelper().submitGroupCreation();
    app.getNavigationHelper().gotoGroupPage();
  }

}
