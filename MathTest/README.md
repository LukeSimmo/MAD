# Math Test

Mobile based online math test, objective to help primary students of grade 1 to 3 practice their math abilities.
The app shows a set of questions to the students, checks the answeras and stores the marks



Functionality
1. Student information
  - register student
  - import information from contacts to register student
  - retrieving and saving photo of student by:
    + taking live photo (camera app)
    + browsing photos in external storage
    + browse photos online (pixabay.com)
  - edit or delete student information
2. Registered students functionality
  - view registered students
  - view a student's test history
3. The math test
  - start and connect to js server
    + returns random question in JSON upon GET request
  - The test screen contains:
    + The question
    + Clickable Multiple choice options (for multiple choice questions)
    + Fill the answer (for manual answer questions)
    + Timer
    + Test score
    + End test
4. View test results and send information (email)
