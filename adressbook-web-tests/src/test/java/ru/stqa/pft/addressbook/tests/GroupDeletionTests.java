package ru.stqa.pft.addressbook.tests;

import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.tests.TestBase;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDelition() throws Exception {
    app.gotoPage("groups");
    app.selectGroup();
    app.deleteSelectedGroups();
    app.gotoPage("groups");
  }

}
