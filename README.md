# Users

<b>V1.0</b>
Приложение Users предназначено для генерации данных пользователей в таблицу и вывод их в excel- и pdf-файлы.
Приложение рандомно генерирует количество пользователей и их данные (количество строк в таблице от 1 до 30 программа определяет 
случайным образом).
Генерируются случайным образом и выводятся следующие данные: Имя, Отчество, Фамилия, Возраст, Пол, Дата Рождения, ИНН, 
Почтовый индекс, Адрес (Страна, Область, Улица, Дом, Квартира).

<b>V2.0</b>
Имя, Фамилия, Возраст, Пол, Дата Рождения и часть адреса, кроме страны, генерируются с помощью [Веб-приложения](https://randomuser.me/),
недостающие данные генерируются рандомно в соответствии с V1.0.

В случае прекращения подключения приложения к сети, данные пользователей генерируются в соответствии с алгоритмом V1.0.

<h1>Сборка и запуск проекта из командной строки</h1>

Перейдите в директорию с исходными файлами проекта и выполните команду:

mvn clean package

В каталоге target будет создан исполняемый файл.
Вывод в консоль осуществляется в кодировке UTF-8, для правильного отображения текста необходимо 
настроить кодировку.

Для запуска приложения выполните команду:

java -jar -Dfile.encoding=UTF-8 target\user-generator.jar

После выполнения файлы будут сохранены в директорию, из которой осуществлялся запуск приложения.
