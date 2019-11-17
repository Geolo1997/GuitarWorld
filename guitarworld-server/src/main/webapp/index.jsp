<%@ page contentType="text/html; charset=UTF-8"  %>
<html>
<body>
<h2>Hello World!</h2>
<h2>上传头像</h2>
<form action="uploadProfilePicture" enctype="multipart/form-data" method="post">
    <table>
        <tr>
            <td>请上传头像:</td>
            <td><input type="file" name="profilePicture"></td>
        </tr>
        <tr>
            <td><input type="submit" value="上传"></td>
        </tr>
    </table>
</form>
</body>
</html>
