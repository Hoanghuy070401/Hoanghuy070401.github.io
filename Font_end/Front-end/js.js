var a = document.querySelector('.numberone');
var b = document.querySelector('.numbertwo');
const c = document.getElementById('btn')
let x = a + b;

function addFunction() {
    alert("kq la" + x)
}
c.addEventListener('onclick', addFunction)
    // JavaScript có thể "hiển thị" dữ liệu theo những cách khác nhau:
    // Viết vào một phần tử HTML, sử dụng innerHTML.
    // Viết vào đầu ra HTML bằng cách sử dụng document.write().
    // Viết vào một hộp cảnh báo, sử dụng window.alert().
    // Ghi vào bảng điều khiển của trình duyệt bằng cách sử dụng console.log().

// Từ khóa	    Sự mô tả
// var 	        Khai báo một biến
// let 	        Khai báo một biến khối
// const	    Khai báo một hằng số khối
// if	        Đánh dấu một khối câu lệnh sẽ được thực thi theo một điều kiện
// switch	    Đánh dấu một khối câu lệnh sẽ được thực thi trong các trường hợp khác nhau
// for	        Đánh dấu một khối câu lệnh sẽ được thực thi trong một vòng lặp
// function	    Khai báo một hàm
// return	    Thoát khỏi một chức năng
// try	        Triển khai xử lý lỗi cho một khối câu lệnh

// Nếu bạn muốn một quy tắc chung: luôn khai báo các biến với const.
// Nếu bạn nghĩ rằng giá trị của biến có thể thay đổi, hãy sử dụng let.