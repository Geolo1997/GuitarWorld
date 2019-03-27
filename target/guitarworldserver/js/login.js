function login() {
    var username = $('#ip_username').val();
    var password = $('#ip_password').val();

    console.log(username + " " + password);
    $.ajax({
        url: "/GuitarWorld/login",
        data: {
            'username': username,
            'password': password
        },
        type: "POST",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                window.location.href = 'main.html';
            } else {
                alert("账号或密码错误");
            }
        }
    });
}