package com.example.demo.dto;

public class AdminDashboardResponse {

    private long totalUsers;
    private long totalRecruiters;
    private long totalCandidates;
    private long totalJobs;
    private long totalApplications;
    private long totalInterviews;
    private long totalSavedJobs;

    public long getTotalUsers() {
        return totalUsers;
    }

    public void setTotalUsers(long totalUsers) {
        this.totalUsers = totalUsers;
    }

    public long getTotalRecruiters() {
        return totalRecruiters;
    }

    public void setTotalRecruiters(long totalRecruiters) {
        this.totalRecruiters = totalRecruiters;
    }

    public long getTotalCandidates() {
        return totalCandidates;
    }

    public void setTotalCandidates(long totalCandidates) {
        this.totalCandidates = totalCandidates;
    }

    public long getTotalJobs() {
        return totalJobs;
    }

    public void setTotalJobs(long totalJobs) {
        this.totalJobs = totalJobs;
    }

    public long getTotalApplications() {
        return totalApplications;
    }

    public void setTotalApplications(long totalApplications) {
        this.totalApplications = totalApplications;
    }

    public long getTotalInterviews() {
        return totalInterviews;
    }

    public void setTotalInterviews(long totalInterviews) {
        this.totalInterviews = totalInterviews;
    }

    public long getTotalSavedJobs() {
        return totalSavedJobs;
    }

    public void setTotalSavedJobs(long totalSavedJobs) {
        this.totalSavedJobs = totalSavedJobs;
    }
}