# Hướng dẫn Setup Assessment Feature

## 1. Cấu trúc Database

### Bảng `assessment_questions`
```sql
CREATE TABLE assessment_questions (
    id SERIAL PRIMARY KEY,
    question TEXT NOT NULL,
    options TEXT[] NOT NULL,
    option_scores INTEGER[] NOT NULL,
    explanation TEXT,
    category TEXT NOT NULL -- 'social', 'negotiation', 'office_communication'
);
```

### Bảng `assessment_results`
```sql
CREATE TABLE assessment_results (
    id SERIAL PRIMARY KEY,
    user_id INTEGER NOT NULL,
    question_id INTEGER NOT NULL,
    user_answer INTEGER NOT NULL,
    is_correct BOOLEAN NOT NULL,
    score INTEGER NOT NULL,
    completed_at BIGINT
);
```

## 2. Setup Database

1. Chạy script SQL để tạo bảng (nếu chưa có)
2. Insert dữ liệu mẫu từ file `sample_assessment_questions.sql`

## 3. Tính năng đã implement

### AssessmentActivity
- ✅ Hiển thị câu hỏi từ Supabase
- ✅ Radio buttons cho các lựa chọn
- ✅ Progress tracking
- ✅ Tính điểm và lưu kết quả

### AssessmentResultActivity
- ✅ Hiển thị tổng điểm
- ✅ Phân loại level (Người mới bắt đầu, Sơ cấp, Trung cấp, Chuyên gia)
- ✅ Feedback tùy theo điểm số
- ✅ Khuyến nghị cải thiện
- ✅ Các card category để navigate

### AssessmentRepository
- ✅ CRUD operations cho questions và results
- ✅ Filter theo category
- ✅ Error handling

## 4. Cách sử dụng

1. **Bắt đầu Assessment**: Nhấn nút "🎯 Bắt đầu đánh giá" trên MainActivity
2. **Làm bài**: Trả lời các câu hỏi bằng cách chọn radio button
3. **Xem kết quả**: Sau khi hoàn thành, app sẽ chuyển đến màn hình kết quả
4. **Lưu kết quả**: Kết quả được lưu vào Supabase table `assessment_results`

## 5. Cấu trúc điểm

- **5 điểm**: Lựa chọn tốt nhất
- **4 điểm**: Lựa chọn khá tốt
- **3 điểm**: Lựa chọn trung bình
- **2 điểm**: Lựa chọn kém
- **1 điểm**: Lựa chọn rất kém

## 6. Phân loại level

- **80%+**: Chuyên gia
- **60-79%**: Trung cấp
- **40-59%**: Sơ cấp
- **<40%**: Người mới bắt đầu

## 7. Categories

- **social**: Kỹ năng xã hội
- **negotiation**: Kỹ năng đàm phán
- **office_communication**: Giao tiếp văn phòng

## 8. Files đã tạo

### Activities
- `AssessmentActivity.kt`
- `AssessmentResultActivity.kt`

### Layouts
- `activity_assessment.xml`
- `activity_assessment_result.xml`

### Repository
- `AssessmentRepository.kt`

### Drawables
- `progress_background.xml`
- `level_background.xml`

### Data
- `sample_assessment_questions.sql`

## 9. Cải thiện có thể thêm

1. **Thêm animation** cho chuyển câu hỏi
2. **Thêm timer** cho mỗi câu hỏi
3. **Thêm progress bar** visual
4. **Thêm sound effects**
5. **Thêm dark mode support**
6. **Thêm offline mode**
7. **Thêm export kết quả PDF**
8. **Thêm retry assessment**
9. **Thêm detailed analytics**
10. **Thêm comparison với người khác**

## 10. Testing

1. Chạy app và đăng nhập
2. Nhấn nút "Bắt đầu đánh giá"
3. Trả lời các câu hỏi
4. Kiểm tra kết quả hiển thị
5. Kiểm tra dữ liệu được lưu trong Supabase 