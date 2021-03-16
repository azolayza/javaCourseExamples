package ru.stqa.pft.addressbook.tests;


import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import ru.stqa.pft.addressbook.model.GroupData;
import ru.stqa.pft.addressbook.model.Groups;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

public class GroupModificationTests extends TestBase{
  @BeforeMethod
  public void ensurePreConditions() {
    app.goTo().GroupPage();
    if (app.db().groups().size() == 0) {
        app.group().create(new GroupData()
              .withName("test11")
              .withFooter("testgroup"));
    }
  }

  @Test
  public void testGroupModification() {
    Groups before = app.db().groups();
    GroupData modifiedGroup = before.iterator().next();
    GroupData group = new GroupData()
            .withId(modifiedGroup.getId())
            .withName("new3")
            .withHeader("new2")
            .withFooter("new3");
    app.group().modify(group);
    assertThat(app.group().count(),equalTo(before.size()));
    Groups after = app.db().groups();
    assertThat(after, equalTo(before.without(modifiedGroup).withAdded(group)));
  }
}
