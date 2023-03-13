package ru.stqa.pft.addressbook.tests.group;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.tests.TestBase;

import java.util.Comparator;
import java.util.List;

public class GroupModificationTests extends TestBase {

    @BeforeMethod
    public void ensurePreconditions() {
        app.goTo().groupPage();
        if (app.group().list().size() ==0) {
            app.group().create( new GroupData().withName("groupForDeletion").withHeader("groupForDeletion").withFooter("groupForDeletion"));
            app.goTo().returnToGroupPage();
        }
    }

    @Test
    public void testGroupModification() {
        List<GroupData> before = app.group().list();
        int index = before.size() - 1;
        GroupData fillGroup = new GroupData()
                .withId(before.get(index).getId()).withName("testModification").withHeader("testModification").withFooter("testModification");
        app.group().modify(index, fillGroup);
        app.goTo().groupPage();
        List<GroupData> after = app.group().list();
        Assert.assertEquals(after.size(), before.size());
        before.remove(index);
        before.add(fillGroup);
        Comparator<? super GroupData> byId = (g1, g2) -> Integer.compare(g1.getId(), g2.getId());
        before.sort(byId);
        after.sort(byId);
        Assert.assertEquals(before, after);
    }

}
