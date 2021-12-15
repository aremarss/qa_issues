package ru.netology.manager;

import lombok.AllArgsConstructor;
import ru.netology.domain.Issue;
import ru.netology.repository.IssueRepository;

import java.util.*;
import java.util.function.Predicate;

@AllArgsConstructor
public class IssueManager {
    IssueRepository repository;

    private final List<Issue> issues = new ArrayList<>();

    // Добавление и удаление.

    public void add(Issue item) {
        repository.save(item);
    }

    public void delete(int id) {
        repository.removeById(id);
    }

    public void addAll(List<Issue> issues) {
        repository.saveAll(issues);
    }

    // Получение и фильтрация.

    public List<Issue> getAll(){
        return repository.findAll();
    }

    public List<Issue> filterByAuthor(Predicate<String> predicate) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue item : repository.findAll()) {
            if (predicate.test(item.getAuthor())) {
                tmp.add(item);
            }
        }
        return tmp;
    }

    public List<Issue> filterByLabel(Predicate<Set> predicate) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue item : repository.findAll()) {
            if (predicate.test(item.getLabels())) {
                tmp.add(item);
            }
        }
        return tmp;
    }

    public List<Issue> filterByAssignee(Predicate<Set> predicate) {
        List<Issue> tmp = new ArrayList<>();
        for (Issue item : repository.findAll()) {
            if (predicate.test(item.getAssignees())) {
                tmp.add(item);
            }
        }
        return tmp;
    }

    // Поиск по тексту и открытым/закрытым issue.

    public List<Issue> searchBy(String text) {
        for (Issue issue : repository.findAll()) {
            if (issue.getAuthor().contains(text)) {
                issues.add(issue);
            }
            if (issue.getAssignees().contains(text)) {
                issues.add(issue);
            }
            if (issue.getLabels().contains(text)) {
                issues.add(issue);
            }
        }
        return issues;
    }

    public void openIssue(int id) {
        if (repository.findById(id) != null) {
            for (Issue issue : repository.findAll()) {
                if (issue.getId() == id) {
                    issue.statusIssue(false);
                }
            }
        }
    }

    public void closeIssue(int id) {
        if (repository.findById(id) != null) {
            for (Issue issue : repository.findAll()) {
                if (issue.getId() == id) {
                    issue.statusIssue(true);
                }
            }
        }
    }

    // Открытие и закрытие issue.

    public List<Issue> isOpened() {
        for (Issue issue : repository.findAll()) {
            if (!issue.isClosed()) {
                issues.add(issue);
            }
        }
        return issues;
    }

    public List<Issue> isClosed() {
        for (Issue issue : repository.findAll()) {
            if (issue.isClosed()) {
                issues.add(issue);
            }
        }
        return issues;
    }

    // Сортировка по дате.

    public List<Issue> getBySortToDate() {
        List<Issue> issues = repository.findAll();
        issues.sort(Comparator.comparingInt(Issue::getDate));
        return issues;
    }

    public List<Issue> getBySortToReversDate() {
        List<Issue> issues = repository.findAll();
        issues.sort((o1, o2) -> o2.getDate() - o1.getDate());
        return issues;
    }
}