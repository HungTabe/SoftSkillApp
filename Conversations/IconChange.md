# Thay đổi icon của ứng dụng
_Exported on 7/12/2025 at 10:43:50 GMT+7 from Cursor (1.2.2)_

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

