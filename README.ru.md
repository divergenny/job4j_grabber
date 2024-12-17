[![en](https://img.shields.io/badge/lang-English_language-red.svg)](https://github.com/divergenny/job4j_grabber/blob/main/README.md)
[![uz](https://img.shields.io/badge/lang-O'zbek_tili-green.svg)](https://github.com/divergenny/job4j_grabber/blob/main/README.uz.md)
#### job4j_grabber

# Проект - Агрегатор вакансий ✨<br>
**job4j_grabber** — проект для автоматизированного поиска и сбора вакансий с сайтов по заданным критериям. <br>
Приложение работает по расписанию и собирает все актуальные вакансии для **Java-разработчиков** с указанного сайта(career.habr.com, раздел /vacancies/java_developer). <br>
Собранные данные сохраняются в базе данных **PostgreSQL**, а доступ к интерфейсу осуществляется через **REST API**. <br>
Период запуска указывается в настройках - **app.properties**.

<details>
<summary>Основной функционал</summary>

1. Парсинг вакансий:
   * Использование библиотеки jsoup для HTML парсинга страниц и извлечения данных о вакансиях;
   * Приложение автоматически обходит страницы с вакансиями, находит позиции, связанные с Java и сохраняет их в базу данных.
2. Планирование задач:
   * Приложение запускается по расписанию с использованием библиотеки Quartz Scheduler, а периодичность задаётся в файле конфигурации app.properties.
3. Хранение данных:
   * Данные о вакансиях сохраняются в реляционной базе данных PostgreSQL с использованием JDBC.
4. Доступ к данным:
   * Реализован REST API для предоставления доступа к собранным данным через HTTP-запросы.
5. Поддержка CI/CD:
   * Используется GitHub Action для автоматической сборки и тестирования проекта.
</details>

## ⚙️ Сборка и запуск <br>
`mvn install` - для сборки <br>
`java -jar target/job4j_grabber-1.0.jar` - для запуска

Перед запуском, необходима существующая база данных job4j и созданная таблица post.
<details>
<summary>Скрипт для создания таблицы post</summary>

`
create table post (
id serial primary key,
name text,
text text,
link text unique,
created timestamp
)
`
</details>


## ❓ Как использовать
Используется для поиска Java вакансий на сайте https://career.habr.com

## 🌐 Контакты
📧 [divergenny@gmail.com](mailto:divergenny@gmail.com)

## 💻 Stack:
* Java Core
* Jsoup (HTML парсинг)
* Quartz Scheduler (планирование задач)
* JDBC
* PostgreSQL (хранение данных)
* Maven (управление зависимостями и построение проекта)
* GitHub Action (непрерывная интеграция CI/CD) 
* REST API
