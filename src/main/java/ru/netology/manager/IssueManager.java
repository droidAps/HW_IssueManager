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
        List<Issue> closedIssues = repository.findAll();
        closedIssues.removeIf(el -> el.isClosed() == false);
        return closedIssues;
    }

    public List<Issue> showOpenedIssues() {
        List<Issue> closedIssues = repository.findAll();
        closedIssues.removeIf(el -> el.isClosed() == true);
        return closedIssues;
    }

    public List<Issue> filterByAuthor(String author) {
        List<Issue> authorList = repository.findAll();
        authorList.removeIf(el -> el.getAuthor() != author);
        return authorList;
    }

    public List<Issue> filterByLabel(String label) {
        List<Issue> labelList = repository.findAll();
        labelList.removeIf(el -> el.getLabel() != label);
        return labelList;
    }

    public List<Issue> filterByAssignee(String assignee) {
        List<Issue> assigneeList = repository.findAll();
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



