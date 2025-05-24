# SoftSkill App

## Giới thiệu
SoftSkill App là ứng dụng Android giúp người học cải thiện kỹ năng mềm thông qua video bài học, câu đố trắc nghiệm, và tình huống thực tế (MOOC). Giao diện lấy cảm hứng từ TikTok, kết hợp trải nghiệm học tập tương tác, tích hợp quảng cáo (AdMob) và xuất bản trên Google Play Store.

## Tính năng
- **Video bài học**: Xem video kỹ năng mềm, hỗ trợ phụ đề (Firebase Storage, ExoPlayer).
- **Quiz liên kết video**: Trả lời câu hỏi trắc nghiệm sau mỗi video, nhận phản hồi và điểm (Firestore).
- **MOOC tình huống**: Tương tác với kịch bản thực tế, đánh giá lựa chọn.
- **Đăng nhập/đăng ký**: Hỗ trợ Email, Google, ẩn danh (Firebase Auth).
- **Đăng tải video**: Người dùng tải video bài học (Firebase Storage).
- **Tính năng xã hội**: Bình luận, chia sẻ video (Firestore, Deep Link).
- **Thông báo**: Nhận thông báo bài học mới (Firebase Cloud Messaging).
- **Gamification**: Điểm số, huy hiệu, bảng xếp hạng (Firestore).
- **Quảng cáo**: Banner, Interstitial, Rewarded ads (AdMob).
- **Đa ngôn ngữ**: Tiếng Anh, tiếng Việt.

## Công nghệ
- **Ngôn ngữ**: Java
- **Công cụ**: Android Studio
- **Thư viện**:
  - Firebase (Auth, Firestore, Storage, Messaging)
  - ExoPlayer (androidx.media3)
  - AdMob (play-services-ads)
  - Material Design 3
- **Kiến trúc**: MVVM
- **Cơ sở dữ liệu**: SQLite (local), Firestore (cloud)

## Yêu cầu hệ thống
- Android 5.0 (API 21) trở lên
- Kết nối Internet
- Tài khoản Google Play (cho quảng cáo và đăng nhập Google)

## Cài đặt
1. **Tải từ Google Play Store** (sắp có):
   - Tìm "SoftSkill App" trên Google Play Store.
   - Nhấn "Cài đặt".
2. **Cài đặt thủ công (cho nhà phát triển)**:
   - Clone repository: `git clone <repository_url>`
   - Mở dự án trong Android Studio.
   - Thêm file `google-services.json` từ Firebase Console vào thư mục `app/`.
   - Đồng bộ dự án với Gradle.
   - Build và chạy trên thiết bị/emulator.

## Hướng dẫn sử dụng
1. **Đăng nhập/đăng ký**:
   - Sử dụng email, Google, hoặc ẩn danh để truy cập.
2. **Xem video bài học**:
   - Cuộn dọc (giống TikTok) để xem video.
   - Tương tác: Thích, bình luận, chia sẻ.
3. **Trả lời quiz**:
   - Sau khi xem video, trả lời câu hỏi trắc nghiệm.
   - Nhận điểm (+10 nếu đúng) và phản hồi.
4. **Tham gia MOOC**:
   - Chọn lựa trong kịch bản thực tế, nhận gợi ý cải thiện.
5. **Đăng tải video**:
   - Chọn video từ thiết bị, thêm tiêu đề/mô tả, tải lên.
6. **Gamification**:
   - Kiểm tra điểm số, huy hiệu trong hồ sơ.
   - Xem bảng xếp hạng top 100.
7. **Thông báo**:
   - Nhận thông báo về bài học mới hoặc nhắc nhở học.

## Cấu trúc dự án
```
app/
├── data/
│   ├── local/ (SQLite DAO)
│   ├── remote/ (Firebase API)
│   └── model/ (Video, Quiz, Mooc, User)
├── ui/
│   ├── main/ (MainActivity, VideoFragment)
│   ├── quiz/ (QuizFragment)
│   ├── mooc/ (MoocActivity)
│   ├── auth/ (LoginActivity, RegisterActivity)
│   └── upload/ (UploadVideoActivity)
├── viewmodel/
└── util/ (AdManager, NotificationHelper)
```

## Quyền
- `READ_EXTERNAL_STORAGE`: Chọn video để tải lên.
- `INTERNET`: Kết nối Firebase, AdMob, tải video.

## Đóng góp
- Fork repository và tạo pull request.
- Báo lỗi hoặc đề xuất tính năng qua Issues.

## Giấy phép
- MIT License

## Liên hệ
- Email: trandinhhung717@gmail.com
- GitHub: https://github.com/HungTabe