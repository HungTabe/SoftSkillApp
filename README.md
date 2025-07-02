# SoftSkill App

## Introduction
**SoftSkill App** is an Android application designed to help high school and university students improve soft skills (social, negotiation, office communication) through assessments, personalized learning paths, YouTube video lessons, notes, and quizzes. The interface is inspired by TikTok (vertical scrolling, vibrant, intuitive), with gamification, comments, and ads (AdMob). Built with Java in Android Studio, it uses Supabase for data storage, Firebase for Google Authentication and notifications, and YouTube for video content.

## Features
### User
- **Login**: Sign in with Google (Firebase Auth).
- **Soft Skills Assessment**: Complete a 20-30 question quiz to evaluate soft skills.
- **Learning Path**: Receive a personalized learning path based on assessment results, divided into 3 categories:
  - **Social**: Social interaction and relationship building.
  - **Negotiation**: Negotiation and conflict resolution.
  - **Office Communication**: Professional communication (emails, meetings).
- **Video Lessons**: Swipe vertically (TikTok-style) to watch YouTube-embedded videos by category (YouTube Android Player API).
- **Notes**: Take notes while watching videos (Supabase).
- **Quiz**: Answer quizzes after videos, view results (score, answers, explanations).
- **Study Frequency**: Track learning consistency (login frequency, streaks) in Profile.
- **Social Interaction**:
  - Comment on videos (Supabase).
  - Share videos via social media (Deep Link).
  - View uploader’s profile by clicking their icon.
- **Gamification**: Earn points (+10/quiz), badges (“Beginner”, “Expert”), and view top 100 leaderboard.
- **Profile**: Display avatar, name, points, badges, study frequency, and tabs for uploaded/liked videos (TikTok-style).
- **Ads**: Banner, Interstitial, Rewarded ads (AdMob).

### Admin
- Manage video metadata and quizzes (Supabase Dashboard).
- Send notifications for new lessons (FCM).

## Technologies
- **Language**: Java.
- **IDE**: Android Studio.
- **Supabase**: Data storage (users, videos, quizzes, comments, notes).
- **Firebase**:
  - Auth: Google Sign-In.
  - Cloud Messaging: Notifications.
- **YouTube Android Player API**: Embedded video playback.
- **AdMob**: Advertisements.
- **Material Design 3**: UI (colors: red #FF0000, white #FFFFFF, gold #FFD700).
- **ViewPager2**: Video swiping.
- **RecyclerView**: Comments, leaderboard.
- **TabLayout**: Profile tabs.

## System Requirements
- Android 5.0+ (min SDK 24).
- Internet connection (Supabase, Firebase, AdMob, YouTube).
- Google Play Services (login, ads).

## Installation
### For Users
- (Coming soon) Download from Google Play Store: Search “SoftSkill App” and tap “Install”.

### For Developers
1. **Clone Repository**:
   ```bash
   git clone <repository_url>
   ```
2. **Open Project**:
   - Open Android Studio, select `SoftSkillApp` project.
3. **Configure Firebase and Supabase**:
   - Download `google-services.json` from Firebase Console, place in `app/`.
   - Enable Google Sign-In, FCM in Firebase Console.
   - Set up Supabase project, create tables, and note API URL/key.
4. **Sync Gradle**:
   - `build.gradle` (App):
     ```gradle
     plugins {
         id 'com.android.application'
         id 'com.google.gms.google-services'
     }
     android {
         namespace 'com.example.softskillapp'
         compileSdk 35
         defaultConfig {
             applicationId 'com.example.softskillapp'
             minSdk 24
             targetSdk 35
             versionCode 1
             versionName "1.0"
         }
         buildTypes {
             release {
                 minifyEnabled false
                 proguardFiles getDefaultProguardFile('proguard-android-optimize.txt'), 'proguard-rules.pro'
             }
         }
         compileOptions {
             sourceCompatibility JavaVersion.VERSION_11
             targetCompatibility JavaVersion.VERSION_11
         }
     }
     dependencies {
         implementation 'androidx.appcompat:appcompat:1.7.0'
         implementation 'com.google.android.material:material:1.12.0'
         implementation 'androidx.constraintlayout:constraintlayout:2.1.4'
         implementation 'androidx.viewpager2:viewpager2:1.1.0'
         implementation 'androidx.lifecycle:lifecycle-viewmodel:2.8.6'
         implementation 'androidx.lifecycle:lifecycle-livedata:2.8.6'
         implementation 'com.google.firebase:firebase-auth:23.0.0'
         implementation 'com.google.firebase:firebase-messaging:24.0.0'
         implementation 'com.google.android.gms:play-services-ads:23.0.0'
         implementation 'com.google.android.youtube:youtube-android-player-api:1.2.2'
         implementation 'androidx.media3:media3-exoplayer:1.4.0'
         implementation 'com.google.android.gms:play-services-auth:21.2.0'
         implementation 'io.github.jan-tennert.supabase:realtime-kt:2.4.0'
         implementation 'io.github.jan-tennert.supabase:postgrest-kt:2.4.0'
     }
     ```
5. **Run**:
   - Build and run on emulator (API 34) or device.

## Usage
1. **Login**: Sign in with Google.
2. **Assessment**: Complete soft skills quiz.
3. **Learning Path**: View personalized path (Social, Negotiation, Office Communication).
4. **Videos**: Swipe to watch YouTube videos by category, take notes.
5. **Quiz**: Answer quizzes, view results (score, explanations).
6. **Social**: Comment, share videos, view uploader profiles.
7. **Profile**: Check points, badges, study frequency, uploaded/liked videos.
8. **Notifications**: Receive learning reminders and new lesson alerts.

## Project Structure
```
app/
├── data/
│   ├── model/ (User, Video, Quiz, Comment, Note)
│   ├── repository/ (UserRepository, VideoRepository, QuizRepository)
├── ui/
│   ├── splash/ (SplashActivity)
│   ├── auth/ (LoginActivity)
│   ├── assessment/ (AssessmentFragment)
│   ├── roadmap/ (RoadmapFragment)
│   ├── video/ (MainFragment)
│   ├── quiz/ (QuizFragment, QuizResultFragment)
│   ├── profile/ (ProfileFragment)
│   ├── comments/ (CommentsFragment)
├── viewmodel/ (UserViewModel, VideoViewModel, QuizViewModel)
└── util/ (AdManager, NotificationHelper)
```

## Permissions
- `INTERNET`: Supabase, Firebase, AdMob, YouTube.
- Runtime permissions (Android 6.0+).

## 2-Week Coding Roadmap
### Week 1
- **Day 1**: Set up project, Supabase, Firebase, models (`User`, `Video`, `Quiz`, `Comment`, `Note`).
- **Day 2**: Splash Screen (`SplashActivity`), Google Sign-In (`LoginActivity`).
- **Day 3**: Soft skills assessment (`AssessmentFragment`), save results to Supabase.
- **Day 4**: Learning path (`RoadmapFragment`), display 3 categories.
- **Day 5**: Video feed (`MainFragment`), YouTube videos, uploader icon.
- **Day 6-7**: Quiz (`QuizFragment`), results (`QuizResultFragment`).

### Week 2
- **Day 8**: Video notes (`MainFragment`, Supabase `notes`).
- **Day 9**: Comments (`CommentsFragment`), video sharing (Deep Link).
- **Day 10**: Profile (`ProfileFragment`, Videos/Liked tabs), gamification.
- **Day 11**: Study frequency tracking (`UserRepository`, streak display).
- **Day 12**: Notifications (FCM), ads (AdMob).
- **Day 13-14**: Unit tests (JUnit), UI tests (Espresso), create AAB, publish to Google Play.

## Contributing
- Fork the repository and submit pull requests.
- Report bugs or suggest features via Issues.

## License
- MIT License.

## Contact
- Email: <your_email@example.com>
- GitHub: <your_github_profile>
