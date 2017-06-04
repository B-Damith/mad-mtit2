package com.mtit.gitso.model;

/**
 * Created by mtit on 5/18/2017.
 */

public class RepoListModel {
    private String name;
    private String description;
    private int issueCount;
    private String issuesURL;
    private String language;

    public RepoListModel(String name, String description, int issueCount, String issuesURL, String language) {
        this.name = name;
        this.description = description;
        this.issueCount = issueCount;
        this.issuesURL = issuesURL;
        this.language = language;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getIssueCount() {
        return issueCount;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setIssueCount(int issueCount) {
        this.issueCount = issueCount;
    }

    public String getIssuesURL() {
        return issuesURL;
    }

    public void setIssuesURL(String issuesURL) {
        this.issuesURL = issuesURL;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }
}
