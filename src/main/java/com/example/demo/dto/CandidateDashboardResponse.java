package com.example.demo.dto;

public class CandidateDashboardResponse {

    private long appliedJobs;
    private long savedJobs;
    private long scheduledInterviews;
    private boolean resumeUploaded;

    public CandidateDashboardResponse() {
    }

    public long getAppliedJobs() {
        return appliedJobs;
    }

    public void setAppliedJobs(long appliedJobs) {
        this.appliedJobs = appliedJobs;
    }

    public long getSavedJobs() {
        return savedJobs;
    }

    public void setSavedJobs(long savedJobs) {
        this.savedJobs = savedJobs;
    }

    public long getScheduledInterviews() {
        return scheduledInterviews;
    }

    public void setScheduledInterviews(long scheduledInterviews) {
        this.scheduledInterviews = scheduledInterviews;
    }

    public boolean isResumeUploaded() {
        return resumeUploaded;
    }

    public void setResumeUploaded(boolean resumeUploaded) {
        this.resumeUploaded = resumeUploaded;
    }
}