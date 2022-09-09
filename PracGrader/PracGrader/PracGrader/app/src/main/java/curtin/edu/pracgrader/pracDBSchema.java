package curtin.edu.pracgrader;

public class pracDBSchema
{
    public static class userTable
    {
        public static final String NAME = "users";
        public static class Cols
        {
            public static final String USERNAME = "username";
            public static final String NAME = "name";
            public static final String EMAIL = "email";
            public static final String PIN = "pin";
            public static final String COUNTRY = "country";
            public static final String TYPE = "type";
            public static final String ADDEDBY = "addedBy";
        }
    }

    public static class practicalTable
    {
        public static final String NAME = "practicals";
        public static class Cols
        {
            public static final String PRACTICALNAME = "practicalName";
            public static final String DESC = "description";
            public static final String MARK = "mark";
        }
    }

    public static class studentMarksTable
    {
        public static final String NAME = "studentMarks";
        public static class Cols
        {
            public static final String USERNAME = "username";
            public static final String PRACTICALNAME = "practicalName";
            public static final String MARK = "mark";
        }
    }
}
