package com.yfzm.flawsweeper.util;

public class Constant {
    public static final int PAGE_SIZE = 10;

    public static final byte PUBLIC_ITEM_MODE = 0;
    public static final byte PRIVATE_ITEM_MODE = 1;

    public static final int NORMAL_USER = 0;
    public static final int ADMIN_USER = 1;

    public static final String SORT_BY_CREATE_TIME = "time";
    public static final String SORT_BY_VIEW_COUNT = "view";

    public class RegisterErrorCode {
        public static final int DUPLICATE_USERNAME = 1;
        public static final int IP_RESTRICT = 2;
        public static final int INVALID_FORM = 3;
        public static final int UNKOWN_ERROR = 4;
    }
}
