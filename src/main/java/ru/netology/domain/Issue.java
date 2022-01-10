package ru.netology.domain;

import java.util.Objects;

public class Issue {
    private int id;
    private String name;
    private String author;
    private String label;
    private String project;
    private String assignee;
    private boolean isClosed;

    public Issue(int id, String name, String author, String label, String project, String assignee, boolean isClosed) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.label = label;
        this.project = project;
        this.assignee = assignee;
        this.isClosed = isClosed;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getAssignee() {
        return assignee;
    }

    public void setAssignee(String assignee) {
        this.assignee = assignee;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean closed) {
        isClosed = closed;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Issue issue = (Issue) o;
        return getId() == issue.getId() && isClosed() == issue.isClosed() && Objects.equals(getName(), issue.getName()) && Objects.equals(getAuthor(), issue.getAuthor()) && Objects.equals(getLabel(), issue.getLabel()) && Objects.equals(getProject(), issue.getProject()) && Objects.equals(getAssignee(), issue.getAssignee());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getAuthor(), getLabel(), getProject(), getAssignee(), isClosed());
    }

    @Override
    public String toString() {
        return "Issue{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", author='" + author + '\'' +
                ", label='" + label + '\'' +
                ", project='" + project + '\'' +
                ", assignee='" + assignee + '\'' +
                ", isClosed=" + isClosed +
                '}';
    }
}
