function getMyProfile() {
    $.ajax({
        url: "/GuitarWorld/getMyProfile",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                var user = data.data;
                $('#tv_username').text(user.username);
                $('#img_profile_picture').attr('src', '/GuitarWorld/profilePicture?username=' + user.username);
            } else {
                alert('拉取个人用户信息失败！');
            }
        }
    });
}

function updateProfilePicture() {
    var formData = new FormData();
    formData.append('profilePicture', $('#ip_profile_picture')[0].files[0]);
    $.ajax({
        url: '/GuitarWorld/profilePicture',
        type: 'POST',
        cache: false,
        data: formData,
        processData: false,
        contentType: false,
        success: function (res) {
            alert('上传成功！');
            window.location.reload();
        },
        error: function (message) {
            alert('上传失败：' + message);
        }
    });
}

function logout() {
    $.ajax({
        url: "/GuitarWorld/logout",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                alert('注销成功！');
                window.location.href = 'login.html';
            } else {
            }
        }
    });
}

function getAllWorks() {
    $.ajax({
        url: "/GuitarWorld/getAllWorks",
        type: "GET",
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                var item = $('.works_list_item');
                var worksList = data.data;
                console.log(worksList);
                for (var i = 0; i < worksList.length; i++) {
                    var works = worksList[i];
                    item.children('.author').text(works.author);
                    item.children('.title').text(works.title);
                    item.children('.content').text(works.content);
                    item.children('.detail').click(function () {
                        worksDetail(item.children('.comments_list'), works.id);
                    });
                    if (i !== worksList.length - 1) {
                        item.before(item.prop("outerHTML"));
                    }
                }
            } else {
            }
        }
    });
}

function worksDetail(commentListDiv, worksId) {
    $.ajax({
        url: "/GuitarWorld/listCommentOfWorks",
        type: "POST",
        data: {
            "worksId": worksId
        },
        dataType: "json",
        success: function (data) {
            if (data.code === 0) {
                var commentList = data.data;
                console.log(commentList);
                var item = commentListDiv.children('.comment_item');
                for (var i = 0; i < commentList.length; i++) {
                    var comment = commentList[i];
                    item.children('.id').text(comment.id);
                    item.children('.author').text(comment.author);
                    item.children('.create_time').text(comment.createTime);
                    item.children('.content').text(comment.content);
                    if (i !== commentList.length - 1) {
                        item.before(item.prop("outerHTML"));
                    }
                }
            }
        }
    });
}