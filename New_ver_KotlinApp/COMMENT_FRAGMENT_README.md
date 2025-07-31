# Comment Fragment Implementation

## Tổng quan
CommentFragment được tạo để hiển thị danh sách comment cho video và cho phép người dùng thêm comment mới. Fragment này được thiết kế dựa trên hình ảnh mẫu được cung cấp.

## Các file đã tạo/cập nhật

### 1. Layout Files
- `fragment_comment.xml` - Layout chính cho CommentFragment
- `item_comment.xml` - Layout cho từng item comment

### 2. Drawable Resources
- `comment_card_background.xml` - Background cho comment card
- `comment_input_background.xml` - Background cho input comment
- `send_button_background.xml` - Background cho nút send
- `ad_background.xml` - Background cho phần advertisement
- `profile_picture_background.xml` - Background cho profile picture
- `ic_arrow_back.xml` - Icon mũi tên back
- `ic_send.xml` - Icon gửi comment

### 3. Kotlin Files
- `CommentFragment.kt` - Fragment chính
- `CommentAdapter.kt` - Adapter cho RecyclerView
- Cập nhật `VideoAdapter.kt` - Thêm callback cho comment button
- Cập nhật `home.kt` - Thêm logic mở CommentFragment
- Cập nhật `Video.kt` - Thêm Parcelable implementation

### 4. Build Configuration
- Cập nhật `build.gradle.kts` - Thêm plugin kotlin-parcelize

## Tính năng

### 1. Hiển thị danh sách comment
- Hiển thị profile picture, username, timestamp và nội dung comment
- Sử dụng RecyclerView với LinearLayoutManager
- Comment mới nhất hiển thị ở đầu danh sách

### 2. Thêm comment mới
- Input field với placeholder "Add a comment..."
- Nút send với icon paper airplane
- Validation: không cho phép gửi comment rỗng
- Comment mới được thêm vào đầu danh sách

### 3. Navigation
- Nút back để quay lại fragment trước
- Sử dụng FragmentTransaction với addToBackStack

### 4. UI/UX
- Thiết kế giống hình ảnh mẫu
- Màu sắc: light peach/off-white cho cards và input
- Bottom navigation bar với Home tab active
- Advertisement placeholder

## Cách sử dụng

### 1. Từ Home Fragment
```kotlin
// Trong VideoAdapter, khi click vào comment button
btnComment.setOnClickListener { onCommentClick(video) }

// Trong home.kt
onCommentClick = { video ->
    openCommentFragment(video)
}
```

### 2. Mở CommentFragment
```kotlin
private fun openCommentFragment(video: Video) {
    val commentFragment = CommentFragment.newInstance(video)
    requireActivity().supportFragmentManager.beginTransaction()
        .replace(R.id.frame_layout, commentFragment)
        .addToBackStack(null)
        .commit()
}
```

## Dữ liệu mẫu
Fragment hiện tại sử dụng dữ liệu mẫu với 3 comment:
1. Sarah Johnson - "This was really helpful! I learned a lot about active listening."
2. Michael Chen - "Great explanation of the concepts. Looking forward to more content!"
3. Emily Davis - "The examples were very practical. I can use these tips in my daily work."

## TODO
- [ ] Tích hợp với database để lưu và load comment thực tế
- [ ] Thêm chức năng like/reply comment
- [ ] Load profile picture từ URL
- [ ] Thêm pagination cho danh sách comment
- [ ] Thêm chức năng edit/delete comment
- [ ] Thêm emoji picker
- [ ] Thêm chức năng mention user

## Lưu ý
- Fragment sử dụng `frame_layout` ID từ NavBarTestActivity
- Video model cần implement Parcelable để truyền qua Fragment
- Cần thêm plugin kotlin-parcelize trong build.gradle.kts 