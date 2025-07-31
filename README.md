# SoftSkill App

## Introduction
**SoftSkill App** is an Android application for high school and university students to enhance soft skills (social, negotiation, office communication) via assessments, personalized learning paths, YouTube video lessons, notes, and quizzes. The TikTok-inspired interface (vertical scrolling, vibrant, intuitive) includes gamification, comments, and AdMob ads. Built with **Kotlin** in Android Studio, it uses **MVVM** architecture, **Supabase** for data storage, **Firebase** for authentication/notifications, and **YouTube** for video content.

## Demo
<img width="354" height="779" alt="image" src="https://github.com/user-attachments/assets/448af1bb-cce3-4ca6-ad44-7eda7a98dcc2" />
<img width="373" height="786" alt="image" src="https://github.com/user-attachments/assets/beaae3c3-4821-4474-a00d-b1924da767ce" />
<img width="365" height="790" alt="image" src="https://github.com/user-attachments/assets/e5a2dcf8-719f-4274-b3de-b3da24bf3b91" />
<img width="364" height="787" alt="image" src="https://github.com/user-attachments/assets/94ad25ed-f710-4f38-b723-5b9f73acfbf3" />
<img width="365" height="790" alt="image" src="https://github.com/user-attachments/assets/e56e177e-5e4c-41a1-85e7-454b0055adef" />
<img width="360" height="787" alt="image" src="https://github.com/user-attachments/assets/1e5a8f99-b63c-466f-a54a-64d6c18d4a24" />
<img width="364" height="785" alt="image" src="https://github.com/user-attachments/assets/45d1ec0e-de67-459b-a3c9-6cf291e15501" />
<img width="364" height="800" alt="image" src="https://github.com/user-attachments/assets/a9342be5-459e-4f1d-a9ae-004b247a232a" />


## Features
### User
- **Login**: Google Sign-In (Firebase Auth).
- **Soft Skills Assessment**: 20-30 question quiz to evaluate skills.
- **Learning Path**: Personalized path based on assessment, split into:
  - **Social**: Social interaction, relationship building.
  - **Negotiation**: Negotiation, conflict resolution.
  - **Office Communication**: Professional emails, meetings.
- **Video Lessons**: TikTok-style vertical swiping for YouTube videos (YouTube Android Player API).
- **Notes**: Save notes during videos (Supabase).
- **Quiz**: Post-video quizzes with scores, answers, explanations.
- **Study Frequency**: Track login streaks in Profile.
- **Social Interaction**:
  - Comment on videos (Supabase).
  - Share via social media (Deep Link).
  - View uploader profiles.
- **Gamification**: Earn points (+10/quiz), badges (“Beginner”, “Expert”), top 100 leaderboard.
- **Profile**: Avatar, name, points, badges, study frequency, uploaded/liked videos (TikTok-style).
- **Ads**: Banner, Interstitial, Rewarded (AdMob).

### Admin
- Manage video metadata, quizzes (Supabase Dashboard).
- Send notifications for new lessons (FCM).

## Technologies
- **Language**: Kotlin.
- **IDE**: Android Studio.
- **Architecture**: MVVM.
- **Build Tool**: Gradle with Kotlin DSL.
- **Supabase**: Store users, videos, quizzes, comments, notes.
- **Firebase**:
  - Auth: Google Sign-In.
  - Cloud Messaging: Notifications.
- **YouTube Android Player API**: Video playback.
- **AdMob**: Ads.
- **Material Design 3**: UI (colors: red #FF0000, white #FFFFFF, gold #FFD700).
- **ViewPager2**: Video swiping.
- **RecyclerView**: Comments, leaderboard.
- **TabLayout**: Profile tabs.

## System Requirements
- Android 5.0+ (min SDK 24).
- Internet (Supabase, Firebase, AdMob, YouTube).
- Google Play Services (login, ads).

## Installation
### For Users
- (Coming soon) Google Play Store: Search “SoftSkill App” and install.

### For Developers
1. **Clone Repository**:
   ```bash
   git clone <repository_url>
   ```
2. **Open Project**:
   - Open Android Studio, select `SoftSkillApp` project.
3. **Configure Firebase and Supabase**:
   - Add `google-services.json` from Firebase Console to `app/`.
   - Enable Google Sign-In, FCM in Firebase Console.
   - Set up Supabase project, create tables, note API URL/key.
4. **Sync Gradle**:
   - `build.gradle.kts` (App):
     ```kotlin
     plugins {
         id("com.android.application")
         id("org.jetbrains.kotlin.android")
         id("com.google.gms.google-services")
     }

     android {
         namespace = "com.example.softskillapp"
         compileSdk = 35
         defaultConfig {
             applicationId = "com.example.softskillapp"
             minSdk = 24
             targetSdk = 35
             versionCode = 1
             versionName = "1.0"
         }
         buildTypes {
             getByName("release") {
                 isMinifyEnabled = false
                 proguardFiles(getDefaultProguardFile("proguard-android-optimize.txt"), "proguard-rules.pro")
             }
         }
         compileOptions {
             sourceCompatibility = JavaVersion.VERSION_11
             targetCompatibility = JavaVersion.VERSION_11
         }
         kotlinOptions {
             jvmTarget = "11"
         }
     }

     dependencies {
         implementation("androidx.core:core-ktx:1.13.1")
         implementation("androidx.appcompat:appcompat:1.7.0")
         implementation("com.google.android.material:material:1.12.0")
         implementation("androidx.constraintlayout:constraintlayout:2.1.4")
         implementation("androidx.viewpager2:viewpager2:1.1.0")
         implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.8.6")
         implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.8.6")
         implementation("com.google.firebase:firebase-auth-ktx:23.0.0")
         implementation("com.google.firebase:firebase-messaging-ktx:24.0.0")
         implementation("com.google.android.gms:play-services-ads:23.0.0")
         implementation("com.google.android.youtube:youtube-android-player-api:1.2.2")
         implementation("androidx.media3:media3-exoplayer:1.4.0")
         implementation("com.google.android.gms:play-services-auth:21.2.0")
         implementation("io.github.jan-tennert.supabase:realtime-kt:2.4.0")
         implementation("io.github.jan-tennert.supabase:postgrest-kt:2.4.0")
     }
     ```
5. **Run**:
   - Build and run on emulator (API 34) or device.

## Usage

1. **Login**: Google Sign-In.
2. **Assessment**: Complete soft skills quiz.
3. **Learning Path**: View personalized path (Social, Negotiation, Office Communication).
4. **Videos**: Swipe for YouTube videos, take notes.
5. **Quiz**: Answer quizzes, view results.
6. **Social**: Comment, share, view uploader profiles.
7. **Profile**: Check points, badges, study frequency, videos.
8. **Notifications**: Get learning reminders, new lesson alerts.

## Project Structure
```
app/
├── data/
│   ├── model/ (User, Video, Quiz, Comment, Note - Kotlin data classes)
│   ├── repository/ (UserRepository, VideoRepository, QuizRepository - Kotlin)
├── ui/
│   ├── splash/ (SplashActivity)
│   ├── auth/ (LoginActivity)
│   ├── assessment/ (AssessmentFragment)
│   ├── roadmap/ (RoadmapFragment)
│   ├── video/ (MainFragment)
│   ├── quiz/ (QuizFragment, QuizResultFragment)
│   ├── profile/ (ProfileFragment)
├── viewmodel/ (UserViewModel, VideoViewModel, QuizViewModel - Kotlin, MVVM)
└── util/ (AdManager, NotificationHelper - Kotlin)
```

## Permissions
- `INTERNET`: Supabase, Firebase, AdMob, YouTube.
- Runtime permissions (Android 6.0+).

## 2-Week Coding Roadmap
### Week 1
- **Day 1**: Set up project, Supabase, Firebase, Kotlin data classes (`User`, `Video`, `Quiz`, `Comment`, `Note`).
- **Day 2**: Splash Screen (`SplashActivity`), Google Sign-In (`LoginActivity`).
- **Day 3**: Assessment (`AssessmentFragment`), save results to Supabase.
- **Day 4**: Learning path (`RoadmapFragment`), 3 categories.

- **Day 5**: Video feed (`MainFragment`), YouTube videos, uploader icon.
- **Day 6-7**: Quiz (`QuizFragment`), results (`QuizResultFragment`).

### Week 2
- **Day 8**: Video notes (`MainFragment`, Supabase).
- **Day 9**: Comments (`CommentsFragment`), video sharing (Deep Link).
- **Day 10**: Profile (`ProfileFragment`, Videos/Liked tabs), gamification.
- **Day 11**: Study frequency (`UserRepository`, streaks).
- **Day 12**: Notifications (FCM), ads (AdMob).
- **Day 13-14**: Unit tests (Kotlin, JUnit), UI tests (Espresso), create AAB, publish to Google Play.

## Contributing
- Fork, submit pull requests.
- Report bugs, suggest features via Issues.


## License
- MIT License.

## Contact
- Email: <your_email@example.com>
- GitHub: <https://github.com/HungTabe>

