package ru.netology.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class Issue {
    private int id;
    private String theme;
    private String author;
    private Set<String> labels;
    private Set<String> assignees;
    private int date;
    private boolean closed;

    public void statusIssue(boolean status) {
        closed = status;
    }
}