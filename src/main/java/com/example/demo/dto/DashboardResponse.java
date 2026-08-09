package com.example.demo.dto;

public class DashboardResponse {

    private long totalJobs;
    private long totalApplications;

    public DashboardResponse() {
    }

    public DashboardResponse(
            long totalJobs,
            long totalApplications) {

        this.totalJobs = totalJobs;
        this.totalApplications = totalApplications;
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
}