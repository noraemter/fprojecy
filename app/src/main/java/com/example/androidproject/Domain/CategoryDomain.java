package com.example.androidproject.Domain;

public class CategoryDomain {
    private int id;
    private String title;
    private String category_image_path;

    public CategoryDomain(int id, String title, String category_image_path) {
        this.id = id;
        this.title = title;
        this.category_image_path = category_image_path;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory_image_path() {
        return category_image_path;
    }

    public void setCategory_image_path(String category_image_path) {
        this.category_image_path = category_image_path;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
