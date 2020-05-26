<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <title>Пользователи</title>
</head>

<body>
<h4>Все пользователи</h4>
<table style="width: 400px">
  <thead>
  <tr>
    <td style="width: 50px">Id</td>
    <td style="width: 150px">Имя</td>
    <td style="width: 100px">Адрес</td>
    <td style="width: 100px">Телефон</td>
    <td style="width: 100px">Роль</td>
  </tr>
  </thead>
  <tbody>
  <#list users as user>
    <tr>
      <td>${user.id}</td>
      <td>${user.name}</td>
      <td>${user.address.street}</td>
      <td>${user.phone.number}</td>
      <td>${user.role}</td>
    </tr>
  </#list>
  </tbody>
</table>

<form action="users" method="post">
  <label>
    Логин <br/>
    <input type="text" name="login" value="">
  </label>
  <br/>
  <label>
    Пароль<br/>
    <input type="password" name="password" value="">
  </label>
  <br/>
  <label>
    Имя<br/>
    <input type="text" name="name" value="">
  </label>
  <br/>
  <label>
    Адрес<br/>
    <input type="text" name="address" value="">
  </label>
  <br/>
  <label>
    Телефон<br/>
    <input type="text" name="phone" value="">
  </label>
  <br/><br/>
  <input type="submit" value="Создать пользователя">
</form>

</body>
</html>
