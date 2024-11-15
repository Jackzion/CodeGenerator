package com.ziio.cli.pattern;

public class Device {

    private String name;

    public Device(String name) {
        this.name = name;
    }

    public void turnOff() {
        System.out.println("turn off the device!" + name);
    }

    public void turnOn() {
        System.out.println("turn on the device!" + name);
    }
}
