package com.alura.demo.service;

public interface IDataConversor {
    <T> T getData(String json, Class<T> clase);
}
