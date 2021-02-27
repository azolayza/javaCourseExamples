package ru.stqa.pft.addressbook.tests;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;

import java.util.*;

public class GroupModificationTests extends TestBase{
  @BeforeMethod
  public void ensurePreConditions() {
    app.goTo().GroupPage();
    if (app.group().all().size() == 0) {
      app.group().create(new GroupData()
              .withName("test11")
              .withFooter("testgroup"));
    }
  }

  @Test
  public void testGroupModification() {
    Set<GroupData> before = app.group().all();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId())
            .withName("new3")
            .withHeader("new2")
            .withFooter("new3");
    app.group().modify(group);
    Set<GroupData> after = app.group().all();
    Assert.assertEquals(after.size(),before.size());

    before.remove(modifiedGroup);
    before.add(group);
    Assert.assertEquals(before, after);
  }
}
