# Quiz Feature Test Setup

## Steps to Test the Quiz Feature

### 1. Database Setup
1. Open your Supabase project
2. Go to the SQL Editor
3. Run the SQL commands from `sample_quiz_data.sql`
4. Verify that the tables are created and data is inserted

### 2. Verify Database Tables
Check that these tables exist in your Supabase database:
- `quizzes` table with columns: id, video_id, question, options, correct_answer, explanation, created_at
- `quiz_results` table with columns: id, quiz_id, user_id, user_answer, is_correct, score, completed_at, created_at

### 3. Verify Sample Data
Check that sample quiz data exists for video IDs 1-5:
```sql
SELECT * FROM quizzes WHERE video_id IN (1,2,3,4,5);
```

### 4. Test the App
1. Build and run the app
2. Navigate to the home fragment
3. Click the "Quizz" button on any video
4. Verify that the QuizFragment opens
5. Test answering questions and navigation
6. Submit the quiz and check the results

### 5. Check for Errors
Common issues to check:
- Video ID mismatch between videos table and quizzes table
- Network connectivity to Supabase
- Firebase authentication status
- Database permissions

### 6. Debug Information
If you encounter issues, check:
- Logcat for error messages
- Supabase logs for database errors
- Network connectivity
- Firebase authentication status

## Expected Behavior
1. Clicking "Quizz" button should open QuizFragment
2. Quiz questions should load from Supabase
3. User can navigate between questions
4. Quiz results should be saved to database
5. Score should be calculated and displayed
6. User should return to home fragment after completion 