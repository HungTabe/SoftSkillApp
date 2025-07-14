# Soft Skill App - Google Sign-In Implementation

## Overview
This Android app implements Google Sign-In using Firebase Authentication and stores user data in Supabase.

## Features
- ✅ Google Sign-In with Firebase Authentication
- ✅ User data storage in Supabase
- ✅ Automatic user session management
- ✅ Logout functionality
- ✅ Modern Material Design UI

## Setup Instructions

### 1. Firebase Configuration
- The `google-services.json` file is already configured with your Firebase project
- Package name: `fpt.edu.vn.softskillappv2`
- SHA-1 fingerprint: `1d:62:e4:ac:77:3d:c0:20:48:ee:9e:d7:6f:ab:fd:96:ba:63:b0:2b`
- SHA-256 fingerprint: `e1:62:a1:b8:ca:ca:54:77:11:93:e3:b1:4f:cd:f5:96:a6:b5:9f:2f:14:c0:5e:4d:b7:bc:27:24:8e:b8:39:c6`

### 2. Supabase Configuration
- Supabase URL and API key are configured in `SupabaseConfig.kt`
- User data is automatically saved to Supabase when signing in

### 3. Dependencies
All required dependencies are already included in `build.gradle.kts`:
- Firebase Auth
- Google Play Services Auth
- Supabase Kotlin SDK
- Material Design Components

## How to Use

### 1. Launch the App
- The app starts with `LoginActivity` as the launcher activity
- If user is already signed in, they'll be redirected to `MainActivity`

### 2. Sign In Process
1. User clicks "Sign in with Google" button
2. Google Sign-In dialog appears
3. User selects their Google account
4. Firebase authenticates the user
5. User data is saved to Supabase
6. User is redirected to MainActivity

### 3. User Session
- User session is maintained using Firebase Auth
- User information is displayed in MainActivity
- Logout button clears session and returns to LoginActivity

## File Structure

```
app/src/main/java/fpt/edu/vn/softskillappv2/
├── ui/auth/
│   └── LoginActivity.kt          # Google Sign-In implementation
├── data/
│   ├── model/
│   │   └── User.kt               # User data model
│   ├── repository/
│   │   └── UserRepository.kt     # Supabase user operations
│   └── supabase/
│       ├── SupabaseClient.kt     # Supabase client configuration
│       └── SupabaseConfig.kt     # Supabase credentials
├── util/
│   └── SharedPrefsManager.kt     # Local data storage
└── MainActivity.kt               # Main app screen with logout
```

## Key Components

### LoginActivity
- Handles Google Sign-In flow
- Integrates with Firebase Authentication
- Saves user data to Supabase
- Manages user session

### UserRepository
- `createOrUpdateUser()`: Creates new user or returns existing user
- `getUserByEmail()`: Retrieves user by email
- Handles all Supabase user operations

### SharedPrefsManager
- Stores user email and ID locally
- Manages login state
- Provides static methods for easy access

## Troubleshooting

### Common Issues
1. **Google Sign-In fails**: Check SHA-1 fingerprint in Firebase Console
2. **Supabase connection fails**: Verify URL and API key in SupabaseConfig
3. **Build errors**: Ensure all dependencies are properly synced

### Debug Information
- Check Logcat for detailed error messages
- Firebase and Supabase operations are logged with tags:
  - `LoginActivity`
  - `MainActivity`
  - `UserRepository`

## Security Notes
- Firebase handles authentication securely
- User data is stored in Supabase with proper access controls
- SHA fingerprints ensure app authenticity
- No sensitive data is stored locally

## Next Steps
- Add user profile management
- Implement additional authentication methods
- Add user progress tracking
- Integrate with more Supabase features 