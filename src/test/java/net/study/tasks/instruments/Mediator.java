package net.study.tasks.instruments;

import net.study.tasks.annotation.Component;

@Component
public class Mediator {

    private String color;
    private String material;

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getMaterial() {
        return material;
    }

    public void setMaterial(String material) {
        this.material = material;
    }
}
