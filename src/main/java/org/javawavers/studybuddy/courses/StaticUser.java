package org.javawavers.studybuddy.courses;

/**
 * The {@code StaticUser} class provides a globally accessible static instance of {@code User}. This
 * instance can be used to represent the current user in the application. Note: Modifications to the
 * static instance are shared across the application. Care should be taken to ensure thread safety
 * and proper synchronization if used in a multithreaded environment.
 */
public class StaticUser {
  public static User staticUser = new User();
}
