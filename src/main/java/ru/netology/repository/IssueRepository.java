package ru.netology.repository;

import ru.netology.domain.Issue;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class IssueRepository {
    private List<Issue> issues = new ArrayList<>();
    private Set<String> labels = new HashSet<>();

    public IssueRepository() {
    }

    public void save(Issue issue) {
        issues.add(issue);
        Set<String> toCopy = new HashSet<>();
        toCopy = issue.getLabel();
        labels.addAll(toCopy);
    }

    public List<Issue> findAll() {
        return issues;
    }

    public Set<String> findAllLabels() {
        return labels;
    }

    public Issue findById(int id) {
        for (Issue issue : issues) {
            if (issue.getId() == id) {
                return issue;
            }
        }
        return null;
    }

    public void removeById(int id) {
        issues.removeIf(el -> el.getId() == id);
        labels.clear();
        Set<String> toCopy = new HashSet<>();
        for (Issue issue : issues) {
            toCopy = issue.getLabel();
            labels.addAll(toCopy);
        }
    }

    public void saveAll(List<Issue> newIssues) {
        issues.addAll(newIssues);
        Set<String> toCopy = new HashSet<>();
        for (Issue issue : issues) {
            toCopy = issue.getLabel();
            labels.addAll(toCopy);
        }
    }
}
