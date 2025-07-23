# Quiz Feature Implementation

## Overview
The Quiz feature allows users to take quizzes related to videos they watch. When a user clicks the "Quizz" button on a video in the HomeFragment, they are taken to a QuizFragment where they can answer questions and have their results saved to Supabase.

## Features
- **Interactive Quiz Interface**: Clean, modern UI with progress tracking
- **Multiple Choice Questions**: Radio button selection for answers
- **Progress Tracking**: Visual progress bar and question counter
- **Navigation**: Previous/Next buttons to navigate between questions
- **Score Calculation**: Automatic scoring based on correct answers
- **Result Saving**: Quiz results are saved to Supabase database
- **User Feedback**: Toast messages for quiz completion and scores

## Implementation Details

### Files Created/Modified

#### New Files:
1. **`QuizFragment.kt`** - Main quiz interface fragment
2. **`fragment_quiz.xml`** - Layout for quiz interface
3. **`sample_quiz_data.sql`** - Sample database data for testing
4. **`QUIZ_FEATURE_README.md`** - This documentation

#### Modified Files:
1. **`home.kt`** - Added navigation to QuizFragment
2. **`Quiz.kt`** - Updated model to match database schema
3. **`QuizRepository.kt`** - Updated to use correct field names

### Database Schema

#### Quizzes Table:
```sql
CREATE TABLE quizzes (
    id SERIAL PRIMARY KEY,
    video_id INTEGER NOT NULL,
    question TEXT NOT NULL,
    options TEXT[] NOT NULL,
    correct_answer INTEGER NOT NULL,
    explanation TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
```

#### Quiz Results Table:
```sql
CREATE TABLE quiz_results (
    id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL REFERENCES quizzes(id),
    user_id INTEGER NOT NULL,
    user_answer INTEGER NOT NULL,
    is_correct BOOLEAN NOT NULL,
    score INTEGER NOT NULL,
    completed_at BIGINT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);
```

### Key Components

#### QuizFragment
- **Navigation**: Handles Previous/Next/Submit button clicks
- **Data Loading**: Fetches quizzes from Supabase based on video ID
- **Answer Tracking**: Stores user answers in memory during quiz
- **Score Calculation**: Calculates percentage score based on correct answers
- **Result Saving**: Saves individual quiz results to Supabase

#### QuizRepository
- **getQuizzesByVideoId()**: Fetches quizzes for a specific video
- **saveQuizResult()**: Saves individual quiz results
- **getQuizById()**: Fetches a specific quiz by ID
- **createQuiz()**: Creates new quiz (for admin use)

## Setup Instructions

### 1. Database Setup
Run the SQL commands in `sample_quiz_data.sql` in your Supabase database:

```sql
-- Execute the SQL file in your Supabase SQL editor
-- This will create the tables and insert sample data
```

### 2. Video Data
Ensure your videos table has entries with IDs that match the quiz data:
- Video ID 1: Communication Skills
- Video ID 2: Leadership Skills
- Video ID 3: Time Management
- Video ID 4: Problem Solving
- Video ID 5: Teamwork

### 3. Testing
1. Launch the app and navigate to the home fragment
2. Click the "Quizz" button on any video
3. Answer the questions using the radio buttons
4. Navigate between questions using Previous/Next buttons
5. Submit the quiz to see your score
6. Check the Supabase database to verify results are saved

## Usage Flow

1. **User clicks "Quizz" button** on a video in HomeFragment
2. **QuizFragment opens** with the video title displayed
3. **Questions load** from Supabase based on video ID
4. **User answers questions** using radio buttons
5. **Navigation** between questions using Previous/Next buttons
6. **Submit quiz** when all questions are answered
7. **Score calculation** and display of results
8. **Results saved** to Supabase database
9. **Return to home** fragment

## Score Calculation
- Each correct answer contributes `100 / total_questions` points
- Final score is displayed as a percentage
- Score feedback:
  - 80%+: "Excellent!"
  - 60%+: "Good job!"
  - 40%+: "Not bad!"
  - <40%: "Keep practicing!"

## Error Handling
- **No quizzes available**: Shows message when no quizzes exist for a video
- **Network errors**: Displays error messages for failed API calls
- **Incomplete answers**: Prevents submission until all questions are answered
- **Save failures**: Shows error if quiz results cannot be saved

## Future Enhancements
- Quiz result history viewing
- Detailed explanations for incorrect answers
- Quiz difficulty levels
- Time limits for questions
- Quiz analytics and statistics
- Admin interface for creating quizzes

## Dependencies
- Firebase Auth (for user identification)
- Supabase (for data storage)
- Material Design components
- Kotlin Coroutines (for async operations)

## Notes
- User ID is currently derived from Firebase user email hash
- Quiz results are saved individually for each question
- The interface supports multiple choice questions only
- Progress is maintained during navigation between questions 