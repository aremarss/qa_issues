package ru.netology.repository;

import ru.netology.domain.Issue;

import java.util.ArrayList;
import java.util.List;

public class IssueRepository {
    private final List<Issue> issues = new ArrayList<>();

    // Сохранение в репозиторий.

    public void save(Issue item) {
        issues.add(item);
    }

    public void saveAll(List<Issue> issues) {
        this.issues.addAll(issues);
    }

    // Удаление из репозитория.

    public void removeById(int id) {
        issues.removeIf(el -> el.getId() == id);
    }

    public void removeAll(List<Issue> issues) {
        this.issues.removeAll(issues);
    }

    // Поиск по репозиторию.

    public Issue findById(int id) {
        for (Issue item : issues) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public List<Issue> findAll() {
        return this.issues;
    }
}