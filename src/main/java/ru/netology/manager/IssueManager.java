package ru.netology.manager;

import ru.netology.domain.*;
import ru.netology.repository.IssueRepository;

import java.util.*;

public class IssueManager {
    private IssueRepository repository;

    public IssueManager(IssueRepository repository) {
        this.repository = repository;
    }

    public void add(Issue issue) {
        repository.save(issue);
    }

    public List<Issue> showClosedIssues() {
        List<Issue> closedIssues = new ArrayList<>();
        closedIssues.addAll(repository.findAll());
        closedIssues.removeIf(el -> el.isClosed() == false);
        return closedIssues;
    }

    public List<Issue> showOpenedIssues() {
        List<Issue> openedIssues = new ArrayList<>();
        openedIssues.addAll(repository.findAll());
        openedIssues.removeIf(el -> el.isClosed() == true);
        return openedIssues;
    }

    public List<Issue> filterByAuthor(String author) {
        List<Issue> authorList = new ArrayList<>();
        authorList.addAll(repository.findAll());
        authorList.removeIf(el -> el.getAuthor() != author);
        return authorList;
    }

    public List<Issue> filterByLabel(String label) {
        List<Issue> labelList = new ArrayList<>();
        labelList.addAll(repository.findAll());
        Set<String> labels = repository.findAllLabels();
        if (labels.contains(label) == false) {
            throw new NotFoundException(
                    "Issues with label: " + label + " not found");
        }
        Set<String> forComparison = new HashSet<>();
        forComparison.add(label);
        labelList.removeIf(el -> el.getLabel().equals(forComparison) == false);
        return labelList;
    }

    public List<Issue> filterByAssignee(String assignee) {
        List<Issue> assigneeList = new ArrayList<>();
        assigneeList.addAll(repository.findAll());
        assigneeList.removeIf(el -> el.getAssignee() != assignee);
        return assigneeList;
    }

    public List<Issue> directSort() {
        List<Issue> tmp = repository.findAll();
        Comparator direct = new DirectSortingById();
        Collections.sort(tmp, direct);
        return tmp;
    }

    public List<Issue> reverseSort() {
        List<Issue> tmp = repository.findAll();
        Comparator reverse = new ReverseSortingById();
        Collections.sort(tmp, reverse);
        return tmp;
    }

    public void closeIssueById(int id) {
        Issue tmp = repository.findById(id);
        if (tmp == null) {
            throw new NotFoundException(
                    "Issue with id: " + id + " not found");
        }
        if (tmp.isClosed() == true) {
            throw new IsClosedException(
                    "Issue status with id: " + id + " closed");
        }
        tmp.setClosed(true);
    }

    public void openIssueById(int id) {
        Issue tmp = repository.findById(id);
        if (tmp == null) {
            throw new NotFoundException(
                    "Issue with id: " + id + " not found");
        }
        if (tmp.isClosed() == false) {
            throw new IsClosedException(
                    "Issue status with id: " + id + " opened");
        }
        tmp.setClosed(false);
    }
}



