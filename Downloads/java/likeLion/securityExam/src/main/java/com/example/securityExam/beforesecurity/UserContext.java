package com.example.securityExam.beforesecurity;

public class UserContext {
    private static final ThreadLocal<User> userThreadLocal = ThreadLocal.withInitial(() -> null);
//        @Override
//        protected User initialValue() {
//            return super.initialValue();
//        }
//    };

    public static void setUser(User user) {
        userThreadLocal.set(user);
    }

    public static User getUser() {
        return userThreadLocal.get();
    }
    public static void clear() {
        userThreadLocal.remove();
    }
}
