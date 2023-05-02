package com.example.familytree.service;


import com.example.familytree.exceptions.FamilyTreeException;
public interface UserService {
  void logIn(String userName, String password) throws FamilyTreeException;
  void signUp(String userName, String password, String role) throws FamilyTreeException;
}
