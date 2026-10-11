-- V5: ใส่รูปให้ชุดตัวอย่างจาก V3 (V3 ไม่ได้ใส่ image_url เลยหน้าเว็บรูปไม่ขึ้น)
-- รูปที่มีใน repo เป็นชุดเดรสเจ้าหญิงทั้งหมด เลยเปลี่ยนชื่อชุดตัวอย่างให้ตรงกับรูป
-- ไฟล์รูปอยู่ที่ src/main/resources/static/uploads/ (เสิร์ฟผ่าน /uploads/...)

UPDATE costumes
SET name = 'ชุดเจ้าหญิงสีชมพูโบว์มาลี',
    description = 'เดรสสั้นสีชมพู ผ้าซาติน แต่งโบว์และแขนผ้าทูลเล่',
    image_url = '/uploads/seed-pink-bow.jpg'
WHERE name = 'ชุดไทยจิตรลดา' AND image_url IS NULL;

UPDATE costumes
SET name = 'ชุดเจ้าหญิงสีม่วงลาเวนเดอร์',
    description = 'เดรสสีม่วงอ่อน ผูกโบว์ แขนผ้าทูลเล่โปร่ง',
    image_url = '/uploads/seed-lavender.jpg'
WHERE name = 'สูทสีดำคลาสสิก' AND image_url IS NULL;

UPDATE costumes
SET name = 'ชุดราตรีสีฟ้าน้ำแข็ง',
    description = 'เดรสสีฟ้าอ่อน กระโปรงพองผ้าเงา เหมาะกับงานราตรี',
    image_url = '/uploads/seed-ice-blue.jpg'
WHERE name = 'ชุดราตรีสีแดงเลือดนก' AND image_url IS NULL;

UPDATE costumes
SET name = 'ชุดเจ้าหญิงทูลเล่สีขาว',
    description = 'ชุดบอลกาวน์ทูลเล่ลายดอกไม้ เหมาะกับงานแฟนซีและถ่ายภาพ',
    image_url = '/uploads/seed-white-tulle.webp'
WHERE name = 'ชุดแฟนซีเจ้าหญิง' AND image_url IS NULL;

-- จัดหมวดให้ตรงกับชุดใหม่ (ชื่อหมวดมาจาก V2)
UPDATE costumes SET category_id = (SELECT id FROM categories WHERE name = 'ชุดแฟนซี')
WHERE name IN ('ชุดเจ้าหญิงสีชมพูโบว์มาลี', 'ชุดเจ้าหญิงสีม่วงลาเวนเดอร์', 'ชุดเจ้าหญิงทูลเล่สีขาว');
