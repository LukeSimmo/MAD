package curtin.edu.assignment2;

public class DBSchema
{
    public static class studentTable
    {
        public static final String NAME = "students";
        public static class Cols
        {
            public static final String ID = "id";
            public static final String FIRSTNAME = "firstname";
            public static final String LASTNAME = "lastname";
            public static final String NUMBER = "number";
            public static final String EMAIL = "email";
            public static final String IMAGE = "image";
        }
    }

    public static class testTable
    {
        public static final String NAME = "tests";
        public static class Cols
        {
            public static final String STUDENTID = "studentID";
            public static final String TIMEOFTEST = "timeOfTest";
            public static final String TOTALTIME = "totalTime";
            public static final String MARK = "mark";
        }
    }
}
