package ru.netology.manager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class IssueManagerTest {
    private final IssueRepository repository = new IssueRepository();
    private final IssueManager manager = new IssueManager(repository);
    private final List<Issue> issues = new ArrayList<>();

    private final Issue first = new Issue(1, "Тема1", "Артем", Collections.singleton("Тег12"), Collections.singleton("Николай"), 12_55, true);
    private final Issue second = new Issue(2, "Тема2", "Геннадий", Collections.singleton("Тег2"), Collections.singleton("Федор"), 11_45, false);
    private final Issue third = new Issue(3, "Тема3", "Артем", Collections.singleton("Тег3"), Collections.singleton("Федор"), 9_55, true);
    private final Issue fourth = new Issue(4, "Тема4", "Александр", Collections.singleton("Тег12"), Collections.singleton("Василий"), 17_00, false);
    private final Issue fifth = new Issue(5, "Дополнительный", "Максим", Collections.singleton("Тег543"), Collections.singleton("Михаил"), 19_00, false);


    @BeforeEach
    void add() {
        issues.add(first);
        issues.add(second);
        issues.add(third);
        issues.add(fourth);
        manager.addAll(issues);
    }

    @Test
    void shouldFindAll() {
        assertEquals(List.of(first, second, third, fourth), manager.getAll());
    }

    @Test
    void shouldDeleteOneIssue() {
        manager.delete(3);
        assertEquals(List.of(first, second, fourth), manager.getAll());
    }

    @Test
    void shouldAddedIssue() {
        manager.add(fifth);
        assertEquals(List.of(first, second, third, fourth, fifth), manager.getAll());
    }

    @Test
    void shouldFindClosed() {
        assertEquals(List.of(first, third), manager.isClosed());
    }

    @Test
    void shouldFindOpen() {
        assertEquals(List.of(second, fourth), manager.isOpened());
    }

    @Test
    void shouldNotFindAll() {
        repository.removeAll(issues);
        assertEquals(List.of(), manager.getAll());
    }

    @Test
    void shouldFindByAuthor(){
        String author = "Артем";
        Predicate<String> equalAuthor = t -> t.equals(author);
        assertEquals(List.of(first, third), manager.filterByAuthor(equalAuthor));
    }

    @Test
    void shouldFindByLabel(){
        Set<String> label = Collections.singleton("Тег12");
        Predicate<Set> equalLabel = t -> t.equals(label);
        assertEquals(List.of(first, fourth), manager.filterByLabel(equalLabel));
    }

    @Test
    void shouldFindByAssignee(){
        Set<String> assignee = Collections.singleton("Федор");
        Predicate<Set> equalAssignee = t -> t.equals(assignee);
        assertEquals(List.of(second, third), manager.filterByAssignee(equalAssignee));
    }

    @Test
    void shouldCloseIssue(){
        manager.closeIssue(2);
        assertEquals(List.of(first, second, third), manager.isClosed());
    }

    @Test
    void shouldOpenIssue(){
        manager.openIssue(3);
        assertEquals(List.of(second, third, fourth), manager.isOpened());
    }

    @Test
    void shouldFindByOldFinder(){
        assertEquals(List.of(second, third), manager.searchBy("Федор"));
    }

    @Test
    void shouldFindAllAndSortByDate() {
        manager.add(fifth);
        assertEquals(List.of(third, second, first, fourth, fifth), manager.getBySortToDate());
    }

    @Test
    void shouldFindAllAndSortByReversDate() {
        manager.add(fifth);
        assertEquals(List.of(fifth, fourth, first, second, third), manager.getBySortToReversDate());
    }
}