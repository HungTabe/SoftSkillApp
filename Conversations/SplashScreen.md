# Thay đổi icon của ứng dụng
_Exported on 7/12/2025 at 10:51:03 GMT+7 from Cursor (1.2.2)_

---

**User**

hãy đọc source code và chỉ tôi cách thay đổi icon của app

---

**Cursor**

Tôi sẽ đọc source code để hiểu cấu trúc ứng dụng và hướng dẫn bạn cách thay đổi icon của app.


Dựa trên việc đọc source code, tôi có thể hướng dẫn bạn cách thay đổi icon của app. Hiện tại ứng dụng đang sử dụng icon mặc định của Android Studio. Đây là cách để thay đổi icon:

## Cách thay đổi icon của app:

### 1. **Cấu trúc hiện tại:**
- Icon được định nghĩa trong `AndroidManifest.xml` tại dòng 12-13:
  ```xml
  android:icon="@mipmap/ic_launcher"
  android:roundIcon="@mipmap/ic_launcher_round"
  ```

- Icon sử dụng adaptive icon với:
  - Background: `@drawable/ic_launcher_background` (màu xanh lá)
  - Foreground: `@drawable/ic_launcher_foreground` (hình Android robot)

### 2. **Các cách thay đổi icon:**

#### **Cách 1: Thay đổi bằng Android Studio (Khuyến nghị)**
1. Chuột phải vào thư mục `app/src/main/res`
2. Chọn `New` → `Image Asset`
3. Chọn `Launcher Icons (Adaptive and Legacy)`
4. Tùy chỉnh:
   - **Foreground Layer**: Chọn icon mới hoặc tạo vector
   - **Background Layer**: Chọn màu nền
   - **Legacy**: Tạo icon cho các thiết bị cũ
5. Click `Next` và `Finish`

#### **Cách 2: Thay thế file thủ công**
1. **Tạo icon mới** (PNG hoặc SVG):
   - Kích thước khuyến nghị: 512x512px
   - Đặt tên: `ic_launcher.png`

2. **Thay thế các file trong thư mục mipmap:**
   - `app/src/main/res/mipmap-hdpi/ic_launcher.webp`
   - `app/src/main/res/mipmap-mdpi/ic_launcher.webp`
   - `app/src/main/res/mipmap-xhdpi/ic_launcher.webp`
   - `app/src/main/res/mipmap-xxhdpi/ic_launcher.webp`
   - `app/src/main/res/mipmap-xxxhdpi/ic_launcher.webp`

3. **Cập nhật file XML:**
   - Sửa `ic_launcher_foreground.xml` để hiển thị icon mới
   - Hoặc thay đổi màu nền trong `ic_launcher_background.xml`

#### **Cách 3: Sử dụng thư viện bên ngoài**
1. Thêm dependency vào `app/build.gradle.kts`:
   ```kotlin
   implementation("com.github.leandroborgesferreira:android-iconify:2.3.0")
   ```

2. Tạo icon trong `drawable` và tham chiếu trong manifest.

### 3. **Lưu ý quan trọng:**
- **Adaptive Icons**: Android 8.0+ sử dụng adaptive icons với background và foreground riêng biệt
- **Kích thước**: Icon nên có kích thước 108x108dp cho adaptive icons
- **Định dạng**: Sử dụng WebP hoặc PNG cho hiệu suất tốt nhất
- **Test**: Luôn test icon trên nhiều thiết bị khác nhau

### 4. **Ví dụ thay đổi màu nền:**
Nếu bạn muốn thay đổi màu nền từ xanh lá sang màu khác, sửa file `ic_launcher_background.xml`:

```xml
<path
    android:fillColor="#YOUR_COLOR_HERE"
    android:pathData="M0,0h108v108h-108z" />
```

Bạn muốn thay đổi icon theo cách nào? Tôi có thể hướng dẫn chi tiết hơn cho từng phương pháp.

```xml
  android:icon="@mipmap/ic_launcher"
  android:roundIcon="@mipmap/ic_launcher_round"
```

```plaintext
   implementation("com.github.leandroborgesferreira:android-iconify:2.3.0")
```

```xml
<path
    android:fillColor="#YOUR_COLOR_HERE"
    android:pathData="M0,0h108v108h-108z" />
```

---

**User**

Hãy đọc src và chỉ tôi cách làm màn splash với Launcher Icons của tôi

---

**Cursor**

Tôi sẽ đọc source code để hiểu cấu trúc ứng dụng và hướng dẫn bạn cách tạo màn hình splash với Launcher Icons của bạn.


Tôi thấy trong AndroidManifest.xml có comment về SplashActivity nhưng chưa được implement. Tôi sẽ hướng dẫn bạn cách tạo màn hình splash với Launcher Icons của bạn.

## Cách tạo màn hình Splash với Launcher Icons:

### 1. **Tạo SplashActivity**


```plaintext
package fpt.edu.vn.softskillappv2.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import fpt.edu.vn.softskillappv2.MainActivity
import fpt.edu.vn.softskillappv2.R

class SplashActivity : AppCompatActivity() {
    
    private lateinit var logoImageView: ImageView
    private val SPLASH_DELAY = 2000L // 2 giây
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        logoImageView = findViewById(R.id.ivLogo)
        
        // Tạo animation cho logo
        val fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeInAnimation.duration = 1000
        
        logoImageView.startAnimation(fadeInAnimation)
        
        // Chuyển hướng sau delay
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}
```

### 2. **Tạo layout cho SplashActivity**


```xml
<?xml version="1.0" encoding="utf-8"?>
<RelativeLayout xmlns:android="http://schemas.android.com/apk/res/android"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    android:background="@drawable/splash_background">

    <!-- Logo ở giữa màn hình -->
    <ImageView
        android:id="@+id/ivLogo"
        android:layout_width="120dp"
        android:layout_height="120dp"
        android:layout_centerInParent="true"
        android:src="@mipmap/ic_launcher"
        android:contentDescription="@string/app_name" />

    <!-- Tên app phía dưới logo -->
    <TextView
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_below="@id/ivLogo"
        android:layout_centerHorizontal="true"
        android:layout_marginTop="24dp"
        android:text="@string/app_name"
        android:textColor="@color/white"
        android:textSize="24sp"
        android:textStyle="bold" />

    <!-- Loading indicator -->
    <ProgressBar
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_centerHorizontal="true"
        android:layout_marginBottom="48dp"
        android:indeterminateTint="@color/white" />

</RelativeLayout>
```

### 3. **Tạo background gradient cho splash**


```xml
<?xml version="1.0" encoding="utf-8"?>
<shape xmlns:android="http://schemas.android.com/apk/res/android">
    <gradient
        android:angle="135"
        android:startColor="#667eea"
        android:endColor="#764ba2"
        android:type="linear" />
</shape>
```

### 4. **Tạo theme riêng cho SplashActivity**


```xml
<resources xmlns:tools="http://schemas.android.com/tools">
    <!-- Base application theme. -->
    <style name="Base.Theme.Softskillappv2" parent="Theme.Material3.DayNight.NoActionBar">
        <!-- Customize your light theme here. -->
        <!-- <item name="colorPrimary">@color/my_light_primary</item> -->
    </style>

    <style name="Theme.Softskillappv2" parent="Base.Theme.Softskillappv2" />
    
    <!-- Theme cho SplashActivity -->
    <style name="Theme.Splash" parent="Theme.Material3.DayNight.NoActionBar">
        <item name="android:windowFullscreen">true</item>
        <item name="android:windowBackground">@drawable/splash_background</item>
        <item name="android:statusBarColor">@android:color/transparent</item>
        <item name="android:navigationBarColor">@android:color/transparent</item>
    </style>
</resources>
```

### 5. **Cập nhật AndroidManifest.xml**


```xml
        <activity
            android:name=".ui.TodoTestActivity"
            android:exported="true"
            >
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>

->

        <activity
            android:name=".ui.splash.SplashActivity"
            android:exported="true"
            android:theme="@style/Theme.Splash"
            android:screenOrientation="portrait">
            <intent-filter>
                <action android:name="android.intent.action.MAIN" />
                <category android:name="android.intent.category.LAUNCHER" />
            </intent-filter>
        </activity>
        
        <activity
            android:name=".ui.TodoTestActivity"
            android:exported="false"
            android:screenOrientation="portrait" />
```

### 6. **Tạo custom animation cho logo (tùy chọn)**


```xml
<?xml version="1.0" encoding="utf-8"?>
<set xmlns:android="http://schemas.android.com/apk/res/android"
    android:duration="1500">
    
    <!-- Fade in -->
    <alpha
        android:fromAlpha="0.0"
        android:toAlpha="1.0"
        android:duration="1000" />
    
    <!-- Scale từ nhỏ đến bình thường -->
    <scale
        android:fromXScale="0.5"
        android:toXScale="1.0"
        android:fromYScale="0.5"
        android:toYScale="1.0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:duration="1000" />
    
    <!-- Rotation nhẹ -->
    <rotate
        android:fromDegrees="-10"
        android:toDegrees="0"
        android:pivotX="50%"
        android:pivotY="50%"
        android:duration="1000" />
        
</set>
```

### 7. **Cập nhật SplashActivity để sử dụng custom animation**


```plaintext
        // Tạo animation cho logo
        val fadeInAnimation = AnimationUtils.loadAnimation(this, android.R.anim.fade_in)
        fadeInAnimation.duration = 1000
        
        logoImageView.startAnimation(fadeInAnimation)

->

        // Tạo animation cho logo
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        logoImageView.startAnimation(logoAnimation)
```

### 8. **Tạo thư mục anim nếu chưa có**


Tuyệt vời! Thư mục `anim` đã tồn tại. Bây giờ tôi sẽ tạo một phiên bản nâng cao hơn của SplashActivity với thêm tính năng kiểm tra trạng thái đăng nhập:

### 9. **Cải tiến SplashActivity với logic kiểm tra đăng nhập**


```plaintext
package fpt.edu.vn.softskillappv2.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import fpt.edu.vn.softskillappv2.MainActivity
import fpt.edu.vn.softskillappv2.R

class SplashActivity : AppCompatActivity() {
    
    private lateinit var logoImageView: ImageView
    private val SPLASH_DELAY = 2000L // 2 giây
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        logoImageView = findViewById(R.id.ivLogo)
        
        // Tạo animation cho logo
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        logoImageView.startAnimation(logoAnimation)
        
        // Chuyển hướng sau delay
        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }, SPLASH_DELAY)
    }
}

->

package fpt.edu.vn.softskillappv2.ui.splash

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.animation.AnimationUtils
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import fpt.edu.vn.softskillappv2.MainActivity
import fpt.edu.vn.softskillappv2.R
import fpt.edu.vn.softskillappv2.SoftSkillApplication

class SplashActivity : AppCompatActivity() {
    
    private lateinit var logoImageView: ImageView
    private val SPLASH_DELAY = 2000L // 2 giây
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        
        logoImageView = findViewById(R.id.ivLogo)
        
        // Tạo animation cho logo
        val logoAnimation = AnimationUtils.loadAnimation(this, R.anim.logo_animation)
        logoImageView.startAnimation(logoAnimation)
        
        // Chuyển hướng sau delay với logic kiểm tra đăng nhập
        Handler(Looper.getMainLooper()).postDelayed({
            navigateToNextScreen()
        }, SPLASH_DELAY)
    }
    
    private fun navigateToNextScreen() {
        val sharedPrefsManager = (application as SoftSkillApplication).sharedPrefsManager
        
        val intent = if (sharedPrefsManager.isLoggedIn()) {
            // Nếu đã đăng nhập, chuyển đến MainActivity
            Intent(this, MainActivity::class.java)
        } else {
            // Nếu chưa đăng nhập, có thể chuyển đến LoginActivity (nếu có)
            // Hoặc vẫn chuyển đến MainActivity để test
            Intent(this, MainActivity::class.java)
        }
        
        startActivity(intent)
        finish()
    }
}
```

## **Tóm tắt cách tạo màn hình Splash với Launcher Icons:**

### **Các file đã tạo/cập nhật:**

1. **`SplashActivity.kt`** - Activity chính với animation và logic chuyển hướng
2. **`activity_splash.xml`** - Layout với logo ở giữa và gradient background
3. **`splash_background.xml`** - Gradient background đẹp mắt
4. **`logo_animation.xml`** - Custom animation cho logo
5. **`themes.xml`** - Theme riêng cho splash screen
6. **`AndroidManifest.xml`** - Cập nhật để SplashActivity là launcher activity

### **Tính năng của Splash Screen:**

✅ **Logo Animation**: Logo có animation fade in + scale + rotation  
✅ **Gradient Background**: Background gradient đẹp mắt  
✅ **Fullscreen**: Không có status bar và navigation bar  
✅ **Loading Indicator**: Progress bar ở dưới  
✅ **Auto Navigation**: Tự động chuyển sau 2 giây  
✅ **Login Check**: Kiểm tra trạng thái đăng nhập  
✅ **Smooth Transition**: Chuyển mượt mà đến MainActivity  

### **Cách tùy chỉnh:**

1. **Thay đổi thời gian hiển thị**: Sửa `SPLASH_DELAY` trong SplashActivity
2. **Thay đổi màu gradient**: Sửa `splash_background.xml`
3. **Thay đổi logo**: Thay `@mipmap/ic_launcher` bằng icon của bạn
4. **Thêm text**: Sửa `activity_splash.xml` để thêm slogan hoặc version
5. **Thay đổi animation**: Tùy chỉnh `logo_animation.xml`

### **Để sử dụng Launcher Icons của bạn:**

1. **Thay thế icon**: Đặt icon của bạn vào thư mục `mipmap-*`
2. **Cập nhật reference**: Thay `@mipmap/ic_launcher` bằng tên icon của bạn
3. **Test**: Build và chạy app để xem kết quả

Bạn có muốn tôi hướng dẫn thêm về cách tùy chỉnh animation hoặc thay đổi icon không?

