package com.example.bcrypt.model;
/*
     enum (short for enumeration) is a special data type that enables for a variable to be a set of predefined constants.
                     The enum constants are implicitly static and final, and they represent a collection of fixed values.
     This is useful for defining a set of related constants, such as days of the week, months of the year, or specific types of operations.
     MessageType is used to define a set of constants that represent different types of messages in a communication context.
     Using enum in this way provides clarity and type safety, ensuring that only valid message types are used throughout the application.
 */
public enum MessageType {

    CHAT,
    CONNECT,
    DISCONNECT
}

