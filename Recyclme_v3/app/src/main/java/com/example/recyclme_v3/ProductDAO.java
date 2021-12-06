package com.example.recyclme_v3;

public class ProductDAO {
    private long id;
    private String productName;
    private float weight;
    private int recycle_instructions;

    public ProductDAO(long id, String productName, float weight, int recycle_instructions) {
        this.id = id;
        this.productName = productName;
        this.weight = weight;
        this.recycle_instructions = recycle_instructions;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public int getRecycle_instructions() {
        return recycle_instructions;
    }

    public void setRecycle_instructions(int recycle_instructions) {
        this.recycle_instructions = recycle_instructions;
    }

    public String toString() {
        return "ProductDAO{" +
                "id=" + id +
                ", productName='" + productName + '\'' +
                '}';
    }
}
