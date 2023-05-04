package ru.nsu.manager.dto;

import java.util.List;

public class StatusDTO {
    String status;
    List<String> data;

    public StatusDTO() {
        status = "IN_PROGRESS";
        data = null;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }

    public StatusDTO(String status, List<String> data) {
        this.status = status;
        this.data = data;
    }
}
