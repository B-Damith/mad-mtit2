package com.mtit.gitso.model;

/**
 * Created by mtit on 5/18/2017.
 */

public class IssueListModel {
    private String title;
    private String status;
    private String createdDate;
    private String body;

    public IssueListModel(String title, String status, String createdDate, String body) {
        this.title = title;
        this.status = status;
        this.createdDate = createdDate;
        this.body = body;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }
}
