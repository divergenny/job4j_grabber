[![en](https://img.shields.io/badge/lang-English_language-red.svg)](https://github.com/divergenny/job4j_grabber/blob/main/README.md)
[![uz](https://img.shields.io/badge/lang-O'zbek_tili-green.svg)](https://github.com/divergenny/job4j_grabber/blob/main/README.uz.md)
#### job4j_grabber

# Loyixa - Vakansiyalar agregatori ✨<br>
**job4j_grabber** — veb-saytlardan kerakli mezonlar bo‘yicha ish e'lonlarini avtomatik ravishda qidirish va yig‘ish loyihasi. <br>
Dastur jadval asosida ishlaydi va Java dasturchilari uchun barcha dolzarb ish e'lonlarini ko‘rsatilgan saytdan (career.habr.com, /vacancies/java_developer bo‘limi) yig‘ib oladi. <br>
Yig'ilgan ma'lumotlar PostgreSQL bazasida saqlanadi, va ularga REST API orqali murojaat qilish mumkin. <br>
Ishga tushirish jadvali app.properties konfiguratsiya faylida belgilanadi.

<details>
<summary>Asosiy Funktsiyalar</summary>

1. Vakansiyalarni Parslash:
   * HTML sahifalarini tahlil qilish va ma'lumotlarni olish uchun Jsoup kutubxonasidan foydalanadi;
   * Dastur avtomatik ravishda sahifalarni ko‘rib chiqadi, Java bilan bog‘liq bo‘lgan ish e'lonlarini topib, ularni bazaga saqlaydi.
2. Vazifalarni Rejalashtirish:
   * Dastur Quartz Scheduler kutubxonasi yordamida jadval asosida ishga tushiriladi, davriylik app.properties faylida sozlanadi.
3. Ma'lumotlarni Saqlash:
   * Ish e'lonlari JDBC yordamida PostgreSQL ma'lumotlar bazasida saqlanadi.
4. Ma'lumotlarga Murojaat:
   * REST API orqali HTTP so'rovlar yordamida ma'lumotlarga kirish imkoniyati yaratilgan.
5. CI/CD:
   * GitHub Actions avtomatik yig‘ish va testlash jarayonlarini amalga oshiradi.
</details>

## ⚙️ Yig'ish va Ishga Tushirish <br>
`mvn install` - loyihani yig'ish uchun <br>
`java -jar target/job4j_grabber-1.0.jar` - loyihani ishga tushirish uchun

Ishga tushirishdan oldin job4j nomli ma'lumotlar bazasi va post jadvali yaratilgan bo‘lishi kerak.
<details>
<summary>post Jadvalini Yaratish Skripti </summary>

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


## ❓ Qanday Foydalaniladi
Dastur Java bo‘yicha ish e'lonlarini https://career.habr.com veb-saytidan qidirish uchun ishlatiladi.

## 🌐 Kontaktlar
📧 [divergenny@gmail.com](mailto:divergenny@gmail.com)

## 💻 Stack:
* Java Core
* Jsoup (HTML parslash)
* Quartz Scheduler (vazifalarni rejalashtirish)
* JDBC
* PostgreSQL (ma'lumotlarni saqlash)
* Maven (bog‘liqliklarni boshqarish va loyihani yig‘ish)
* GitHub Action (CI/CD integratsiyasi) 
* REST API
