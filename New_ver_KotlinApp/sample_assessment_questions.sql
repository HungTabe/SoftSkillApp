-- Sample Assessment Questions for Soft Skills App
-- Insert these into your Supabase assessment_questions table

INSERT INTO assessment_questions (question, options, option_scores, explanation, category) VALUES
(
    'Khi đồng nghiệp không đồng ý với ý kiến của bạn, bạn sẽ làm gì?',
    ARRAY[
        'Tức giận và bỏ đi',
        'Lắng nghe ý kiến của họ và tìm điểm chung',
        'Bỏ qua và không nói chuyện với họ nữa',
        'Cố gắng thuyết phục họ bằng mọi cách'
    ],
    ARRAY[1, 5, 2, 3],
    'Kỹ năng lắng nghe và tìm điểm chung là rất quan trọng trong môi trường làm việc.',
    'social'
),
(
    'Trong cuộc họp, khi có người nói quá nhiều và chiếm hết thời gian, bạn sẽ:',
    ARRAY[
        'Ngắt lời họ ngay lập tức',
        'Đợi họ nói xong rồi nhắc nhở về thời gian',
        'Bỏ cuộc họp',
        'Tìm cách chuyển chủ đề một cách tế nhị'
    ],
    ARRAY[2, 4, 1, 5],
    'Cách xử lý tình huống này cần sự khéo léo và tôn trọng người khác.',
    'office_communication'
),
(
    'Khi đàm phán với khách hàng về giá, khách hàng yêu cầu giảm giá 20%, bạn sẽ:',
    ARRAY[
        'Đồng ý ngay lập tức',
        'Từ chối thẳng thừng',
        'Tìm hiểu lý do và đưa ra giải pháp thay thế',
        'Bỏ cuộc đàm phán'
    ],
    ARRAY[2, 1, 5, 1],
    'Đàm phán hiệu quả cần tìm hiểu nhu cầu thực sự và đưa ra giải pháp win-win.',
    'negotiation'
),
(
    'Khi nhận được feedback tiêu cực từ sếp, bạn sẽ:',
    ARRAY[
        'Phản đối ngay lập tức',
        'Lắng nghe và hỏi thêm để hiểu rõ',
        'Bỏ qua feedback đó',
        'Tìm cách đổ lỗi cho người khác'
    ],
    ARRAY[1, 5, 2, 1],
    'Tiếp nhận feedback một cách tích cực là kỹ năng quan trọng để phát triển.',
    'office_communication'
),
(
    'Trong team có xung đột giữa hai thành viên, bạn sẽ:',
    ARRAY[
        'Bỏ qua và để họ tự giải quyết',
        'Tổ chức cuộc họp để thảo luận vấn đề',
        'Chọn phe ủng hộ',
        'Báo cáo lên cấp trên ngay lập tức'
    ],
    ARRAY[2, 5, 1, 3],
    'Giải quyết xung đột cần sự trung lập và khả năng lắng nghe.',
    'social'
),
(
    'Khi khách hàng phàn nàn về sản phẩm, bạn sẽ:',
    ARRAY[
        'Bảo vệ sản phẩm bằng mọi cách',
        'Lắng nghe, xin lỗi và tìm giải pháp',
        'Chuyển cho bộ phận khác xử lý',
        'Bỏ qua phàn nàn'
    ],
    ARRAY[2, 5, 3, 1],
    'Xử lý khiếu nại khách hàng cần sự kiên nhẫn và tìm giải pháp thực tế.',
    'negotiation'
),
(
    'Khi thuyết trình trước đám đông, bạn cảm thấy lo lắng, bạn sẽ:',
    ARRAY[
        'Hủy buổi thuyết trình',
        'Chuẩn bị kỹ và thực hành nhiều lần',
        'Đọc từng chữ một cách máy móc',
        'Uống thuốc an thần'
    ],
    ARRAY[1, 5, 2, 1],
    'Chuẩn bị kỹ lưỡng là cách tốt nhất để vượt qua lo lắng khi thuyết trình.',
    'office_communication'
),
(
    'Khi đồng nghiệp xin nghỉ phép đột xuất, bạn sẽ:',
    ARRAY[
        'Từ chối ngay lập tức',
        'Tìm hiểu lý do và sắp xếp công việc',
        'Đồng ý nhưng tỏ thái độ không vui',
        'Báo cáo lên cấp trên'
    ],
    ARRAY[1, 5, 2, 3],
    'Sự linh hoạt và thông cảm trong công việc giúp tạo môi trường làm việc tốt.',
    'social'
),
(
    'Trong cuộc đàm phán, đối phương đưa ra điều kiện không hợp lý, bạn sẽ:',
    ARRAY[
        'Đồng ý để ký hợp đồng',
        'Từ chối và bỏ đi',
        'Tìm hiểu lý do và đưa ra đề xuất khác',
        'Đe dọa đối phương'
    ],
    ARRAY[2, 1, 5, 1],
    'Đàm phán cần sự kiên nhẫn và khả năng tìm giải pháp thay thế.',
    'negotiation'
),
(
    'Khi email của bạn bị hiểu nhầm, bạn sẽ:',
    ARRAY[
        'Gửi email trả đũa',
        'Gọi điện hoặc gặp mặt để làm rõ',
        'Bỏ qua vấn đề',
        'Báo cáo lên HR'
    ],
    ARRAY[1, 5, 2, 3],
    'Giao tiếp trực tiếp thường hiệu quả hơn email trong việc giải quyết hiểu nhầm.',
    'office_communication'
); 