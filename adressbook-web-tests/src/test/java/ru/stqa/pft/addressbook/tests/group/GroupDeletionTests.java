package ru.stqa.pft.addressbook.tests.group;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Set;

public class GroupDeletionTests extends TestBase {

  @BeforeMethod
  public void ensurePreconditions() {
    app.goTo().groupPage();
    if (app.group().all().size() ==0) {
      app.group().create( new GroupData().withName("groupForDeletion").withHeader("groupForDeletion").withFooter("groupForDeletion"));
      app.goTo().returnToGroupPage();
    }
  }

  @Test
  public void testGroupDeletion() throws Exception {
    Set<GroupData> before = app.group().all();
    GroupData groupForDeletion = before.iterator().next();
    app.group().delete(groupForDeletion);
    app.goTo().groupPage();
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() - 1);
    before.remove(groupForDeletion);
    Assert.assertEquals(before, after);
  }
}
