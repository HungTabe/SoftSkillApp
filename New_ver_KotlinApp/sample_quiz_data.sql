-- Sample Quiz Data for SoftSkill App
-- This file contains sample quiz questions and data for testing

-- Create quizzes table if not exists
CREATE TABLE IF NOT EXISTS quizzes (
    id SERIAL PRIMARY KEY,
    video_id INTEGER NOT NULL,
    question TEXT NOT NULL,
    options TEXT[] NOT NULL,
    correct_answer INTEGER NOT NULL,
    explanation TEXT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Create quiz_results table if not exists
CREATE TABLE IF NOT EXISTS quiz_results (
    id SERIAL PRIMARY KEY,
    quiz_id INTEGER NOT NULL REFERENCES quizzes(id),
    user_id INTEGER NOT NULL,
    user_answer INTEGER NOT NULL,
    is_correct BOOLEAN NOT NULL,
    score INTEGER NOT NULL,
    completed_at BIGINT,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT NOW()
);

-- Sample quiz data for video with ID 1 (Communication Skills)
INSERT INTO quizzes (video_id, question, options, correct_answer, explanation) VALUES
(1, 'What is the most important aspect of effective communication?', 
 ARRAY['Speaking clearly', 'Active listening', 'Using big words', 'Talking quickly'], 
 1, 'Active listening is crucial for understanding others and building meaningful connections.'),

(1, 'Which of the following is NOT a component of non-verbal communication?', 
 ARRAY['Facial expressions', 'Body language', 'Tone of voice', 'Written text'], 
 3, 'Written text is verbal communication, while the others are non-verbal cues.'),

(1, 'What should you do when someone is speaking to you?', 
 ARRAY['Plan your response', 'Interrupt with questions', 'Listen actively and maintain eye contact', 'Check your phone'], 
 2, 'Active listening involves focusing on the speaker and maintaining appropriate eye contact.');

-- Sample quiz data for video with ID 2 (Leadership Skills)
INSERT INTO quizzes (video_id, question, options, correct_answer, explanation) VALUES
(2, 'What is the primary role of a leader?', 
 ARRAY['To give orders', 'To inspire and guide others', 'To control everything', 'To work alone'], 
 1, 'Leaders inspire and guide their team members toward achieving common goals.'),

(2, 'Which leadership style is most effective in most situations?', 
 ARRAY['Autocratic', 'Democratic', 'Laissez-faire', 'It depends on the situation'], 
 3, 'Effective leadership style depends on the situation, team, and organizational context.'),

(2, 'What is emotional intelligence in leadership?', 
 ARRAY['Being emotional', 'Understanding and managing emotions', 'Crying when needed', 'Being sensitive'], 
 1, 'Emotional intelligence involves understanding and managing both your own emotions and those of others.');

-- Sample quiz data for video with ID 3 (Time Management)
INSERT INTO quizzes (video_id, question, options, correct_answer, explanation) VALUES
(3, 'What is the first step in effective time management?', 
 ARRAY['Making a to-do list', 'Prioritizing tasks', 'Setting goals', 'Using a calendar'], 
 2, 'Setting clear goals helps you understand what you want to achieve and guides your time management.'),

(3, 'Which time management technique involves working for 25 minutes followed by a 5-minute break?', 
 ARRAY['Time blocking', 'Pomodoro Technique', 'Eisenhower Matrix', 'Getting Things Done'], 
 1, 'The Pomodoro Technique uses focused work sessions followed by short breaks to maintain productivity.'),

(3, 'What should you do with tasks that are urgent but not important?', 
 ARRAY['Do them immediately', 'Delegate them', 'Schedule them for later', 'Ignore them'], 
 1, 'Urgent but not important tasks should be delegated when possible to focus on important work.');

-- Sample quiz data for video with ID 4 (Problem Solving)
INSERT INTO quizzes (video_id, question, options, correct_answer, explanation) VALUES
(4, 'What is the first step in the problem-solving process?', 
 ARRAY['Implementing a solution', 'Identifying the problem', 'Evaluating alternatives', 'Gathering information'], 
 1, 'You must clearly identify and understand the problem before you can solve it effectively.'),

(4, 'Which problem-solving technique involves breaking down a complex problem into smaller parts?', 
 ARRAY['Brainstorming', 'Root cause analysis', 'Decomposition', 'Trial and error'], 
 2, 'Decomposition involves breaking complex problems into smaller, more manageable components.'),

(4, 'What is the purpose of brainstorming in problem solving?', 
 ARRAY['To find the perfect solution immediately', 'To generate many possible solutions', 'To criticize ideas', 'To implement solutions'], 
 1, 'Brainstorming aims to generate a wide variety of possible solutions without judgment.');

-- Sample quiz data for video with ID 5 (Teamwork)
INSERT INTO quizzes (video_id, question, options, correct_answer, explanation) VALUES
(5, 'What is the key to successful teamwork?', 
 ARRAY['Having the best individual performers', 'Effective communication and collaboration', 'Working independently', 'Following orders'], 
 1, 'Effective communication and collaboration are essential for team success.'),

(5, 'What should you do when there is a conflict in your team?', 
 ARRAY['Ignore it', 'Address it openly and constructively', 'Take sides', 'Complain to management'], 
 1, 'Addressing conflicts openly and constructively helps resolve issues and strengthens the team.'),

(5, 'What is the role of a team member in a collaborative environment?', 
 ARRAY['To work alone', 'To contribute their skills and support others', 'To compete with others', 'To avoid responsibility'], 
 1, 'Team members should contribute their skills while supporting and collaborating with others.'); 