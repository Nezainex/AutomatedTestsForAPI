package org.example.models;

/**
 * Модель для тела запроса создания пользователя.
 */
public class CreateUsersBodyModel {
    private String name;
    private String job;

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }
}

