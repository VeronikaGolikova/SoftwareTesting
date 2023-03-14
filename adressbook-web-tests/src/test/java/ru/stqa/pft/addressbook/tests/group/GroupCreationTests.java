package ru.stqa.pft.addressbook.tests.group;

import org.testng.Assert;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Set;

public class GroupCreationTests extends TestBase {

  @Test
  public void testGroopCreation() throws Exception {
    app.goTo().groupPage();
    Set<GroupData> before = app.group().all();
    GroupData group = new GroupData().withName("test5").withHeader("test1").withFooter("test1");
    app.group().create(group);
    app.goTo().returnToGroupPage();
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(), before.size() +1);
    group.withId(after.stream().mapToInt((g)->g.getId()).max().getAsInt());
    before.add(group);
    Assert.assertEquals(before, after);
  }
}
