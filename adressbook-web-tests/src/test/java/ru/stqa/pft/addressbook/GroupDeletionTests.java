package ru.stqa.pft.addressbook;

import org.testng.annotations.Test;

public class GroupDeletionTests extends TestBase {

  @Test
  public void testGroupDelition() throws Exception {
    gotoPage("groups");
    selectGroup();
    deleteSelectedGroups();
    gotoPage("groups");
  }

}
