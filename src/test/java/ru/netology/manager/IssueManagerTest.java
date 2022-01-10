package ru.netology.manager;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import ru.netology.domain.IsClosedException;
import ru.netology.domain.Issue;
import ru.netology.domain.NotFoundException;
import ru.netology.repository.IssueRepository;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class IssueManagerTest {
    IssueRepository repo = new IssueRepository();
    IssueManager manager = new IssueManager(repo);

    Issue one = new Issue(1, "Issue1", "Ivanov", "status: new", "NewProject", "Petrov", true);
    Issue two = new Issue(2, "Issue2", "Krivonogov", "status: new", "NewProject", "Sidorov", false);
    Issue three = new Issue(3, "Issue3", "Ivanov", "status: in progress", "NewProject", "Krivonogov", true);
    Issue four = new Issue(4, "Issue4", "Ivanov", "status: in progress", "NewProject", "Prohorov", false);
    Issue five = new Issue(5, "Issue5", "Sidorov", "status: in progress", "NewProject", "Ivanov", true);
    Issue six = new Issue(6, "Issue6", "Prohorov", "status: new", "NewProject", "Petrov", false);
    Issue seven = new Issue(7, "Issue7", "Krivonogov", "status: in progress", "NewProject", "Petrov", true);

    @Nested
    public class Empty {

        @Test
        void shouldCloseIssueWithIdNotFound() {
            repo.saveAll(List.of(one, five, seven));

            assertThrows(NotFoundException.class, () -> {
                manager.closeIssueById(4);
            });
        }

        @Test
        void IssueIsClosedTest() {
            repo.saveAll(List.of(one, five, three));

            assertThrows(IsClosedException.class, () -> {
                manager.closeIssueById(5);
            });
        }

        @Test
        void shouldOpenIssueWithIdNotFound() {
            repo.saveAll(List.of(one, five, seven, four));

            assertThrows(NotFoundException.class, () -> {
                manager.openIssueById(2);
            });
        }

        @Test
        void IssueIsOpenedTest() {
            repo.saveAll(List.of(two, six, four));

            assertThrows(IsClosedException.class, () -> {
                manager.openIssueById(6);
            });
        }

        @Test
        void filterByAuthorEmptyTest() {
            Issue[] expected = new Issue[0];

            repo.saveAll(List.of(one, three, four));
            Issue[] actual = manager.filterByAuthor("Krivonogov").toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void filterByLabelEmptyTest() {
            Issue[] expected = new Issue[0];

            repo.saveAll(List.of(four, five, three));
            Issue[] actual = manager.filterByLabel("status: new").toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void filterByAssigneeEmptyTest() {
            Issue[] expected = new Issue[0];

            repo.saveAll(List.of(five, six, seven, one, two, three));
            Issue[] actual = manager.filterByAssignee("Prohorov").toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void showClosedIssuesEmptyTest() {
            Issue[] expected = new Issue[0];

            repo.saveAll(List.of(two, four, six));
            Issue[] actual = manager.showClosedIssues().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void showOpenedIssuesEmptyTest() {
            Issue[] expected = new Issue[0];

            repo.saveAll(List.of(one, three, five, seven));
            Issue[] actual = manager.showOpenedIssues().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void shouldNotRemoveById() {
            Issue[] expected = {one, six, seven};

            manager.add(one);
            manager.add(six);
            manager.add(seven);
            repo.removeById(3);
            Issue[] actual = repo.findAll().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }
    }

    @Nested
    public class WithResult {

        @Test
        void shouldAdd() {
            Issue[] expected = {one, six, seven};

            manager.add(one);
            manager.add(six);
            manager.add(seven);
            Issue[] actual = repo.findAll().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void shouldRemoveById() {
            Issue[] expected = {one, seven};

            manager.add(one);
            manager.add(six);
            manager.add(seven);
            repo.removeById(6);
            Issue[] actual = repo.findAll().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void showClosedIssuesTest() {
            Issue[] expected = {one, three, five, seven};

            repo.saveAll(List.of(one, two, three, four, five, six, seven));
            Issue[] actual = manager.showClosedIssues().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void showOpenedIssuesTest() {
            Issue[] expected = {two, four, six};

            repo.saveAll(List.of(one, two, three, four, five, six, seven));
            Issue[] actual = manager.showOpenedIssues().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void filterByAuthorTest() {
            Issue[] expected = {one, three, four};

            repo.saveAll(List.of(one, two, three, four, five, six, seven));
            Issue[] actual = manager.filterByAuthor("Ivanov").toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void filterByLabelTest() {
            Issue[] expected = {one, two, six};

            repo.saveAll(List.of(one, two, three, four, five, six, seven));
            Issue[] actual = manager.filterByLabel("status: new").toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void filterByAssigneeTest() {
            Issue[] expected = {one, six, seven};

            repo.saveAll(List.of(one, two, three, four, five, six, seven));
            Issue[] actual = manager.filterByAssignee("Petrov").toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void directSortTest() {
            Issue[] expected = {one, three, five, seven};

            repo.saveAll(List.of(seven, one, five, three));
            Issue[] actual = manager.directSort().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void reverseSortTest() {
            Issue[] expected = {seven, five, three, one};

            repo.saveAll(List.of(one, five, seven, three));
            Issue[] actual = manager.reverseSort().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void shouldCloseIssueById() {
            Issue[] expected = {one, five, seven, new Issue(4, "Issue4", "Ivanov", "status: in progress", "NewProject", "Prohorov", true)};

            repo.saveAll(List.of(one, five, seven, four));
            manager.closeIssueById(4);
            Issue[] actual = repo.findAll().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }

        @Test
        void shouldOpenIssueById() {
            Issue[] expected = {one, new Issue(5, "Issue5", "Sidorov", "status: in progress", "NewProject", "Ivanov", false), seven, four};

            repo.saveAll(List.of(one, five, seven, four));
            manager.openIssueById(5);
            Issue[] actual = repo.findAll().toArray(Issue[]::new);

            assertArrayEquals(expected, actual);
        }
    }

}